package com.avicodes.calorietrackerai.presentation.screens.upload


import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.avicodes.calorietrackerai.R
import com.avicodes.calorietrackerai.models.MealName
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import java.time.ZonedDateTime

@OptIn(ExperimentalPagerApi::class)
@Composable
fun UploadScreen(
    uiState: UploadUiState,
    calorieGenState: CalorieGenState,
    pagerState: PagerState,
    mealName: () -> String,
    onDescriptionChanged: (String) -> Unit,
    onDeleteConfirmed: () -> Unit,
    onBackPressed: () -> Unit,
    onSaveClicked: () -> Unit,
    onDateTimeUpdated: (ZonedDateTime) -> Unit,
    onImageSelect: (List<Uri>) -> Unit,
    onImageDeleteClicked: (Bitmap) -> Unit
) {
    var selectedGalleryImage by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(key1 = uiState.mealName) {
        pagerState.scrollToPage(MealName.valueOf(uiState.mealName.name).ordinal)
    }

    Scaffold(
        topBar = {
            UploadTopBar(
                onDeleteConfirmed = onDeleteConfirmed,
                selectedMeal = uiState.selectedMeal,
                mealName = mealName,
                onBackPressed = onBackPressed,
                onDateTimeUpdated = onDateTimeUpdated
            )
        },
        content = { paddingValues ->
            UploadContent(
                uploadUiState = uiState,
                pagerState = pagerState,
                onDescriptionChanged = onDescriptionChanged,
                paddingValues = paddingValues,
                onSaveClicked = onSaveClicked,
                onImageSelect = onImageSelect,
                onImageClicked = { selectedGalleryImage = it }
            )
        }
    )

    when (calorieGenState) {
        is CalorieGenState.Fetching -> {
            val bitmapFlow = calorieGenState.bitmapFlow
            val bitmap by bitmapFlow.collectAsState(initial = null)
            if (bitmap != null) {
                ImageDialog(
                    isGenerating = true,
                    selectedGalleryImage = bitmap,
                    onDismiss = { },
                    onImageDeleteClicked = { }
                )
            }
        }

        else -> {}
    }

    AnimatedVisibility(visible = selectedGalleryImage != null) {
        ImageDialog(
            selectedGalleryImage = selectedGalleryImage,
            onDismiss = { selectedGalleryImage = null },
            onImageDeleteClicked = onImageDeleteClicked
        )
    }
}


@Composable
fun ImageDialog(
    isGenerating: Boolean = false,
    selectedGalleryImage: Bitmap?,
    onDismiss: () -> Unit,
    onImageDeleteClicked: (Bitmap) -> Unit,
) {
    Dialog(onDismissRequest = { onDismiss.invoke() }) {
        if (selectedGalleryImage != null) {
            DisplayImage(
                isGenerating = isGenerating,
                selectedGalleryImage = selectedGalleryImage,
                onCloseClicked = { onDismiss.invoke() },
                onDeleteClicked = {
                    if (selectedGalleryImage != null) {
                        onImageDeleteClicked(selectedGalleryImage)
                        onDismiss.invoke()
                    }
                }
            )
        }
    }
}


@Composable
fun DisplayImage(
    isGenerating: Boolean,
    selectedGalleryImage: Bitmap,
    onCloseClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {

    Column {
        if (!isGenerating) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = onCloseClicked) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close Icon")
                    Text(text = "Close")
                }
                Button(onClick = onDeleteClicked) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Icon")
                    Text(text = "Delete")
                }
            }
        }

        Box(
            modifier = Modifier
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(40.dp)
                    .fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(selectedGalleryImage)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Fit,
                contentDescription = "Gallery Image"
            )
            if (isGenerating) {
                val animationFoodComposition by rememberLottieComposition(
                    spec = LottieCompositionSpec.RawRes(R.raw.animation_scanning)
                )
                LottieAnimation(
                    composition = animationFoodComposition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}