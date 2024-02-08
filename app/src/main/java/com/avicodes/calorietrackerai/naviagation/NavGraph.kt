@file:OptIn(ExperimentalFoundationApi::class)

package com.avicodes.calorietrackerai.naviagation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.avicodes.calorietrackerai.models.MealName
import com.avicodes.calorietrackerai.presentation.screens.history.HistoryScreen
import com.avicodes.calorietrackerai.presentation.screens.history.HistoryViewModel
import com.avicodes.calorietrackerai.presentation.screens.home.HomeScreen
import com.avicodes.calorietrackerai.presentation.screens.upload.UploadScreen
import com.avicodes.calorietrackerai.presentation.screens.upload.UploadViewModel
import com.avicodes.calorietrackerai.utils.Constants.UPLOAD_SCREEN_ARGUMENT_KEY
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState


@Composable
fun SetupNavGraph(startDestination: String, navController: NavHostController) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        homeRoute()
        historyRoute(
            navigateToUploadWithArgs = { navController.navigate(Screen.Upload.passMealId(it)) }
        )
        uploadRoute(
            onBackClicked = { navController.popBackStack() }
        )
    }
}

fun NavGraphBuilder.homeRoute() {
    composable(route = Screen.Home.route) {
        HomeScreen()
    }
}

fun NavGraphBuilder.historyRoute(
    navigateToUploadWithArgs: (String) -> Unit,
) {
    composable(route = Screen.History.route) {
        val viewModel: HistoryViewModel = hiltViewModel()
        val meals by viewModel.meals

        HistoryScreen(
            meals = meals,
            dateIsSelected = viewModel.dateIsSelected,
            onDateSelected = { },
            onDateReset = { },
            onBackPressed = { },
            navigateToMealDetail = navigateToUploadWithArgs
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
fun NavGraphBuilder.uploadRoute(
    onBackClicked: () -> Unit
) {
    composable(
        route = Screen.Upload.route,
        arguments = listOf(navArgument(name = UPLOAD_SCREEN_ARGUMENT_KEY) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {

        val pagerState = rememberPagerState()

        val viewModel: UploadViewModel = hiltViewModel()
        val uiState = viewModel.uiState
        val galleryState = viewModel.galleryState
        val pageNumber by remember { derivedStateOf { pagerState.currentPage } }

        UploadScreen(
            uiState = uiState,
            pagerState = pagerState,
            onBackPressed = onBackClicked,
            onDeleteConfirmed = { },
            onTitleChanged = { },
            onDescriptionChanged = { },
            moodName = { MealName.entries[pageNumber].name },
            onSaveClicked = { },
            onDateTimeUpdated = { },
            galleryState = galleryState,
            onImageSelect = { },
            onImageDeleteClicked = { }
        )
    }
}