package com.avicodes.calorietrackerai.presentation.screens.home

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