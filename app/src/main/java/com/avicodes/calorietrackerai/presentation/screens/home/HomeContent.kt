package com.avicodes.calorietrackerai.presentation.screens.home

import android.content.res.Resources.Theme
import android.net.Uri
import android.provider.CalendarContract.Colors
import android.widget.Space
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.avicodes.calorietrackerai.R
import com.avicodes.calorietrackerai.utils.dashedBorder


@Composable
fun HomeContent(
    modifier: Modifier,
    homeUiState: HomeUiState,
    requestCalorie: (List<Uri>) -> Unit
) {

    var imageUris = rememberSaveable {
        mutableStateListOf<Uri>()
    }

    val multiplePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 5),
    ) { images ->
        images.forEach {
            imageUris.add(it)
        }
        requestCalorie(imageUris)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when(homeUiState) {
            is HomeUiState.Initial -> {
                selectFoodImages(
                    selectImagesClicked = {
                        multiplePhotoPicker.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                )
            }

            is HomeUiState.Loading -> {
                imageUris?.let {
                    showImagesScan(
                        selectedImageList = imageUris
                    )
                }

            }

            is HomeUiState.Success -> {
                Card(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(), shape = MaterialTheme.shapes.large
                ) {
                    Text(text = homeUiState.outputText, modifier = Modifier.padding(16.dp))
                }
            }

            is HomeUiState.Error -> {
                Card(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(), shape = MaterialTheme.shapes.large
                ) {
                    Text(text = homeUiState.errorMessage, modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

@Composable
fun showImagesScan(
    selectedImageList: List<Uri>,
) {

    val animationScanComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.animation_scanning))
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyRow {
                items(selectedImageList) { imagesUri ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = imagesUri,
                            contentDescription = "selected image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .requiredSize(200.dp)
                        )
                    }
                }
            }
        }

        LottieAnimation(
            composition = animationScanComposition,
            iterations = LottieConstants.IterateForever
        )
    }

}

@Composable
fun selectFoodImages(
    modifier: Modifier = Modifier,
    selectImagesClicked: () -> Unit
) {
    val animationFoodComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.food_animation))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .dashedBorder(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(20.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LottieAnimation(
            composition = animationFoodComposition,
            iterations = LottieConstants.IterateForever
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Click to select multiple images of the food for which you want to count the calorie",
            style = TextStyle (
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = selectImagesClicked,
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Select Images")
        }
    }
}