@file:OptIn(ExperimentalFoundationApi::class)

package com.avicodes.calorietrackerai.naviagation

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.avicodes.calorietrackerai.models.MealName
import com.avicodes.calorietrackerai.presentation.screens.history.HistoryScreen
import com.avicodes.calorietrackerai.presentation.screens.history.HistoryViewModel
import com.avicodes.calorietrackerai.presentation.screens.home.HomeScreen
import com.avicodes.calorietrackerai.presentation.screens.upload.UploadScreen
import com.avicodes.calorietrackerai.presentation.screens.upload.UploadViewModel
import com.avicodes.calorietrackerai.utils.Constants.UPLOAD_SCREEN_ARGUMENT_KEY
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


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
        HomeScreen(
            navigateToUpload = { }
        )
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
        val viewModel: UploadViewModel = hiltViewModel()
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()
        val imageRequestBuilder = ImageRequest.Builder(LocalContext.current)
        val imageLoader = ImageLoader.Builder(LocalContext.current).build()
        val uiState = viewModel.uploadUiState
        val calorieGenState = viewModel.calorieGenState
        val pageNumber by remember { derivedStateOf { pagerState.currentPage } }

        UploadScreen(
            uiState = uiState,
            calorieGenState = calorieGenState,
            mealName = { MealName.entries[pageNumber].name },
            pagerState = pagerState,
            onBackPressed = onBackClicked,
            onDeleteConfirmed = { },
            onDescriptionChanged = { viewModel.changeDescription(it) },
            onSaveClicked = { },
            onDateTimeUpdated = { },
            onImageDeleteClicked = { },
            onImageSelect = { imageUriList ->
                coroutineScope.launch {
                    val bitmaps = async {
                        imageUriList.mapNotNull {
                            val imageRequest = imageRequestBuilder
                                .data(it)
                                .size(size = 768)
                                .build()

                            val imageResult = imageLoader.execute(imageRequest)
                            if (imageResult is SuccessResult) {
                                return@mapNotNull (imageResult.drawable as BitmapDrawable).bitmap
                            } else {
                                return@mapNotNull null
                            }
                        }
                    }.await()
                    viewModel.addPhoto(bitmaps)
                    viewModel.requestCalories(selectedImages = bitmaps)
                }
            },
        )
    }
}