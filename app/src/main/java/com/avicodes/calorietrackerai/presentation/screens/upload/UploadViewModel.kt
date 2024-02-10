package com.avicodes.calorietrackerai.presentation.screens.upload

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avicodes.calorietrackerai.BuildConfig
import com.avicodes.calorietrackerai.models.Meal
import com.avicodes.calorietrackerai.models.MealName
import com.avicodes.calorietrackerai.utils.getCurrentMeal
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject


@HiltViewModel
class UploadViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var _uploadUiState: UploadUiState by mutableStateOf(UploadUiState())
    val uploadUiState get() = _uploadUiState

    private var _calorieGenState: CalorieGenState by mutableStateOf(CalorieGenState.Idle)
    val calorieGenState get() = _calorieGenState

    private var generativeModel: GenerativeModel

    init {
        val config = generationConfig {
            temperature = 0.1f
        }
        generativeModel = GenerativeModel(
            modelName = "gemini-pro-vision",
            apiKey = BuildConfig.gemini_api_key,
            generationConfig = config
        )
    }

    fun changeDescription(desc: String) {
        _uploadUiState = _uploadUiState.copy(description = desc)
    }

    fun addPhoto(images: List<Bitmap>) {
        val calorie = if (images.isEmpty()) null else _uploadUiState.calories
        _uploadUiState = _uploadUiState.copy(images = images, calories = calorie)
    }

    suspend fun requestCalories(selectedImages: List<Bitmap>) {
        _calorieGenState = CalorieGenState.Fetching(startImageLoop(selectedImages))
        val prompt =
            "Exactly how much calories does this food is? if its a packet, then search the calorie of food according to packet size, if its some utensil container, take that in account to calculate the precise calorie. give the exact calorie count, in numbers, only give me number, no other text"

        var output = ""
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val content = content {
                    for (bitmap in selectedImages) {
                        image(bitmap)
                    }
                    text(prompt)
                }
                generativeModel.generateContentStream(content).collect { res ->
                    output += res.text
                    _uploadUiState = _uploadUiState.copy(calories = output)
                    _calorieGenState = CalorieGenState.Idle
                }
            } catch (e: Exception) {
                _calorieGenState = CalorieGenState.Error(e.message.toString())
            }
        }
    }

    private fun startImageLoop(images: List<Bitmap>): Flow<Bitmap> = flow {
        while (_calorieGenState is CalorieGenState.Fetching) {
            for (bitmap in images) {
                emit(bitmap)
                delay(2000)
            }
        }
    }
}

sealed class CalorieGenState {
    data class Fetching(var bitmapFlow: Flow<Bitmap>) : CalorieGenState()

    data object Idle : CalorieGenState()

    data class Error(val message: String) : CalorieGenState()
}

data class UploadUiState(
    var calories: String? = null,
    var selectedMeal: Meal? = null,
    var description: String = "",
    val mealName: MealName = getCurrentMeal(),
    var images: List<Bitmap> = listOf(),
    val updatedDateTime: Instant? = null
)

