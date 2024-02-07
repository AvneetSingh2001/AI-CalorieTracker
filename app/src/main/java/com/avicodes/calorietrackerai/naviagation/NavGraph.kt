package com.avicodes.calorietrackerai.naviagation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.avicodes.calorietrackerai.presentation.screens.history.HistoryScreen
import com.avicodes.calorietrackerai.presentation.screens.home.HomeScreen
import com.avicodes.calorietrackerai.presentation.screens.upload.UploadScreen
import com.avicodes.calorietrackerai.utils.Constants.UPLOAD_SCREEN_ARGUMENT_KEY


@Composable
fun SetupNavGraph(startDestination: String, navController: NavHostController) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        homeRoute()
        historyRoute(
            onBackClicked = {
                navController.popBackStack()
            }
        )
        uploadRoute()
    }
}

fun NavGraphBuilder.homeRoute() {
    composable(route = Screen.Home.route) {
        HomeScreen()
    }
}

fun NavGraphBuilder.historyRoute(
    onBackClicked: () -> Unit
) {
    composable(route = Screen.Home.route) {
        HistoryScreen(
            onBackClicked = onBackClicked
        )
    }
}

fun NavGraphBuilder.uploadRoute() {
    composable(
        route = Screen.Home.route,
        arguments = listOf(navArgument(name = UPLOAD_SCREEN_ARGUMENT_KEY) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        UploadScreen()
    }
}