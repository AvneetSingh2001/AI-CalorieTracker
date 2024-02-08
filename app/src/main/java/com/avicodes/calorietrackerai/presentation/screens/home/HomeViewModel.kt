package com.avicodes.calorietrackerai.presentation.screens.home

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avicodes.calorietrackerai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _homeUiState: MutableStateFlow<HomeUiState> =
        MutableStateFlow(HomeUiState.Initial)

    val homeUiState =
        _homeUiState.asStateFlow()

    private val _calories: MutableStateFlow<Int>
        = MutableStateFlow(0)

    val calories =
        _calories.asStateFlow()

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

    fun requestCalories(selectedImages: List<Bitmap>) {
        _homeUiState.value = HomeUiState.Loading
        val prompt = "Exactly how much calories does this food is? if its a packet, then search the calorie of food according to packet size, if its some utensil container, take that in account to calculate the precise calorie. give the exact calorie count, in numbers, only give me number, no other text"

        var output = ""
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val content = content {
                    for(bitmap in selectedImages) {
                        image(bitmap)
                    }
                    text(prompt)
                }

                generativeModel.generateContentStream(content).collect { res ->
                    output += res.text
                    _homeUiState.value = HomeUiState.Success(output)
                }
            } catch (e: Exception) {
                _homeUiState.value = HomeUiState.Error(e.message ?: "Something went wrong :(")
            }
        }
    }

    fun discardedCalorie() {
        _homeUiState.value = HomeUiState.Initial
    }

    fun addCalorie(currentCalorieCount: Int) {
        _homeUiState.value = HomeUiState.Initial
        _calories.value = _calories.value.plus(currentCalorieCount)
    }
}

sealed interface HomeUiState {
    object Initial : HomeUiState
    object Loading : HomeUiState

    data class Error(
        val errorMessage: String
    ) : HomeUiState

    data class Success(
        val outputText: String
    ) : HomeUiState
}