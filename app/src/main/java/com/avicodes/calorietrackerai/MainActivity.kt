package com.avicodes.calorietrackerai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.avicodes.calorietrackerai.naviagation.Screen
import com.avicodes.calorietrackerai.naviagation.SetupNavGraph
import com.avicodes.calorietrackerai.ui.theme.CalorieTrackerAITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalorieTrackerAITheme {
                val navController = rememberNavController()
                SetupNavGraph(startDestination = Screen.DietHistory.route, navController = navController)
            }
        }
    }
}
