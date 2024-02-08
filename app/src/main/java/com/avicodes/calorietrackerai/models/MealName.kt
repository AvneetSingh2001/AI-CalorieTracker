package com.avicodes.calorietrackerai.models

import androidx.compose.ui.graphics.Color
import com.avicodes.calorietrackerai.R
import com.avicodes.calorietrackerai.ui.theme.BreakfastColor
import com.avicodes.calorietrackerai.ui.theme.BrunchColor
import com.avicodes.calorietrackerai.ui.theme.DinnerColor
import com.avicodes.calorietrackerai.ui.theme.LunchColor
import com.avicodes.calorietrackerai.ui.theme.SnacksColor

enum class MealName(
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
        icon = R.drawable.brunch,
        contentColor = Color.Black,
        containerColor = BrunchColor
    ),
    Lunch(
        icon = R.drawable.lunch,
        contentColor = Color.Black,
        containerColor = LunchColor
    ),
    Snacks(
        icon = R.drawable.snacks,
        contentColor = Color.Black,
        containerColor = SnacksColor
    ),
    Dinner(
        icon = R.drawable.dinner,
        contentColor = Color.Black,
        containerColor = DinnerColor
    )
}