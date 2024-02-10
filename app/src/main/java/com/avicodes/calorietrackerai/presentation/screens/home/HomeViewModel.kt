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