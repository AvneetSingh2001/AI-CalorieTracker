package com.avicodes.calorietrackerai.presentation.screens.home

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val scrollState = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val homeUiState = viewModel.homeUiState.collectAsState()
    val totalCaloriesCount = viewModel.calories.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val imageRequestBuilder = ImageRequest.Builder(LocalContext.current)
    val imageLoader = ImageLoader.Builder(LocalContext.current).build()

    Scaffold(
        modifier = Modifier.scrollable(scrollState, Orientation.Vertical),
        topBar = { HomeTopBar(scrollBehavior = scrollBehavior) },
        content = { paddingValues ->
            HomeContent(
                modifier = Modifier.padding(paddingValues),
                homeUiState = homeUiState.value,
                discardClicked = { viewModel.discardedCalorie() },
                addClicked = { viewModel.addCalorie(currentCalorieCount = it) },
                totalCaloriesCount = totalCaloriesCount.value,
                requestCalorie = { imageList ->
                    coroutineScope.launch {
                        val bitmaps = imageList.mapNotNull {
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

                        viewModel.requestCalories(selectedImages = bitmaps)
                    }
                },
            )
        }
    )

}





