package com.avicodes.calorietrackerai.presentation.screens.upload

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avicodes.calorietrackerai.models.GalleryState
import com.avicodes.calorietrackerai.models.Meal
import com.avicodes.calorietrackerai.models.MealName
import com.avicodes.calorietrackerai.utils.Constants.UPLOAD_SCREEN_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.ZonedDateTime
import javax.inject.Inject


@HiltViewModel
class UploadViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var uiState by mutableStateOf(UiState())
        private set

    val galleryState = GalleryState()
}

data class UiState(
    val mealCalories: Int? = null,
    val selectedMealId: String? = null,
    val selectedMeal: Meal? = null,
    val description: String = "",
    val mealName: MealName = MealName.Breakfast,
    val updatedDateTime: Instant? = null
)