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

    private var generativeModel: GenerativeModel

    init {
        val config = generationConfig {
            temperature = 0.7f
        }

        generativeModel = GenerativeModel(
            modelName = "gemini-pro-vision",
            apiKey = BuildConfig.gemini_api_key,
            generationConfig = config
        )
    }

    fun requestCalories(selectedImages: List<Bitmap>) {
        _homeUiState.value = HomeUiState.Loading
        val prompt = "what is this?"

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val content = content {
                    for(bitmap in selectedImages) {
                        image(bitmap)
                    }
                    text(prompt)
                }

                var output = ""
                generativeModel.generateContentStream(content).collect { res ->
                    output += res.text
                    _homeUiState.value = HomeUiState.Success(output)
                }
            } catch (e: Exception) {
                _homeUiState.value = HomeUiState.Error(e.message ?: "Something went wrong :(")
            }
        }
    }


}