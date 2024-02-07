package com.avicodes.calorietrackerai.models

import androidx.compose.ui.graphics.Color
import com.avicodes.calorietrackerai.R
import com.avicodes.calorietrackerai.ui.theme.BreakfastColor

enum class Meal(
    val icon: Int,
    val contentColor: Color,
    val containerColor: Color
) {
    Breakfast(
        icon = R.drawable.breakfast,
        contentColor = Color.Black,
        containerColor = BreakfastColor
    ),
    Brunch(
        icon = R.drawable.breakfast,
        contentColor = Color.Black,
        containerColor = BreakfastColor
    ),
    Lunch(
        icon = R.drawable.breakfast,
        contentColor = Color.Black,
        containerColor = BreakfastColor
    ),
    Snacks(
        icon = R.drawable.breakfast,
        contentColor = Color.Black,
        containerColor = BreakfastColor
    ),
    Dinner(
        icon = R.drawable.breakfast,
        contentColor = Color.Black,
        containerColor = BreakfastColor
    )
}