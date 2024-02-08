package com.avicodes.calorietrackerai.naviagation

import com.avicodes.calorietrackerai.utils.Constants.UPLOAD_SCREEN_ARGUMENT_KEY

sealed class Screen(val route: String) {
    data object Home: Screen(route = "home_screen")
    data object History: Screen(route = "history_screen")
    data object Upload: Screen(route = "upload_screen?$UPLOAD_SCREEN_ARGUMENT_KEY={$UPLOAD_SCREEN_ARGUMENT_KEY}") {
        fun passMealId(mealId: String) =
            "upload_screen_?$UPLOAD_SCREEN_ARGUMENT_KEY=$mealId"
    }
}