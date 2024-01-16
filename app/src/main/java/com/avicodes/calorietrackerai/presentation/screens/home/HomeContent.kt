package com.avicodes.calorietrackerai.presentation.screens.home

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.avicodes.calorietrackerai.R
import com.avicodes.calorietrackerai.utils.dashedBorder

@Composable
fun HomeContent(
    modifier: Modifier,
    homeUiState: HomeUiState,
    requestCalorie: (List<Uri>) -> Unit,
    discardClicked: () -> Unit,
    addClicked: () -> Unit,
) {

    var imageUri = remember {
        mutableListOf<Uri>()
    }

    val multiplePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { image ->
        if (image != null) {
            imageUri.add(image)
            requestCalorie(imageUri)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (homeUiState) {
            is HomeUiState.Initial -> {
                Column {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Row(
                            modifier = Modifier
                                .background(
                                    color = Color.Red,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .height(50.dp)
                                .padding(10.dp),
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_calorie),
                                contentDescription = "Calorie Icon",
                                colorFilter = ColorFilter.tint(Color.White)
                            )

                            Column(
                                modifier = Modifier.fillMaxHeight(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "4343",
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(
                                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                        fontWeight = FontWeight.Normal,
                                        color = Color.White,
                                    ),
                                )
                            }

                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))

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

            }

            is HomeUiState.Loading -> {
                if (imageUri.size > 0) {
                    showImagesScan(
                        selectedImageList = imageUri
                    )
                }

            }

            is HomeUiState.Success -> {
                showCalorieResult(
                    calorie = homeUiState.outputText,
                    discardClicked = discardClicked,
                    addClicked = addClicked
                )
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
fun showCalorieResult(
    calorie: String,
    discardClicked: () -> Unit,
    addClicked: () -> Unit,
) {
    Column {
        Card(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
        ) {
            Text(
                text = "$calorie Calories",
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = discardClicked,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f),
            ) {
                Text(text = "Discard")
            }

            Spacer(
                modifier = Modifier.width(10.dp)
            )

            Button(
                onClick = addClicked,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f),
            ) {
                Text(text = "Add")
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
            .fillMaxWidth()

    ) {

        Column(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = selectedImageList[0],
                contentDescription = "selected image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .requiredSize(180.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }

        LottieAnimation(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
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
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LottieAnimation(
            composition = animationFoodComposition,
            iterations = LottieConstants.IterateForever
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Click to select the image of the food for which you want to count the calorie",
            style = TextStyle(
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