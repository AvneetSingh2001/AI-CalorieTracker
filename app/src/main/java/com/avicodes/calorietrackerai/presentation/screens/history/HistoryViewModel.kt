package com.avicodes.calorietrackerai.presentation.screens.history

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.avicodes.calorietrackerai.models.Meals
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor() : ViewModel() {
    var meals: MutableState<Meals> = mutableStateOf(mapOf())

    var dateIsSelected by mutableStateOf(false)
        private set
}