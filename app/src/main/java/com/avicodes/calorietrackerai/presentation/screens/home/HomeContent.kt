package com.avicodes.calorietrackerai.presentation.screens.home

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.airbnb.lottie.compose.rememberLottieComposition
import com.avicodes.calorietrackerai.R
import com.avicodes.calorietrackerai.presentation.components.CalorieProgressInfo
import com.avicodes.calorietrackerai.utils.dashedBorder

@Composable
fun HomeContent(
    modifier: Modifier,
    homeUiState: HomeUiState,
    totalCaloriesCount: Int,
    navigateToUpload: () -> Unit,
    discardClicked: () -> Unit,
    addClicked: (Int) -> Unit,
) {
    var imageUri = remember { mutableListOf<Uri>() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (homeUiState) {
            is HomeUiState.Initial -> {
                selectFoodImages(selectImagesClicked = navigateToUpload)
            }

           else -> {}
        }


        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f))
                .padding(vertical = 20.dp)
        ) {
            Text(
                text = "Today's Progress",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(20.dp))

            CalorieProgressInfo()
        }

        Spacer(modifier = Modifier.height(40.dp))


        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f))
                    .padding(vertical = 20.dp, horizontal = 20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "History Icon"
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Edit Goal",
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f))
                    .padding(vertical = 20.dp, horizontal = 20.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "History Icon"
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Show Diet History",
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall
                )
            }

        }


    }
}

@Composable
fun showCalorieResult(
    currentCalorieCount: String,
    discardClicked: () -> Unit,
    addClicked: (Int) -> Unit,
) {
    Column {
        Card(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
        ) {
            Text(
                text = "$currentCalorieCount Calories",
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
                onClick = {
                    addClicked(
                        currentCalorieCount
                            .replace("[^0-9]".toRegex(), "")
                            .toIntOrNull() ?: 0
                    )
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f),
            ) {
                Text(text = "Add")
            }
        }
    }
}

@Composable
fun selectFoodImages(
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

@Preview
@Composable
fun showHomeContent() {
    Scaffold { paddingValues ->
        HomeContent(
            modifier = Modifier.padding(paddingValues),
            homeUiState = HomeUiState.Initial,
            addClicked = {},
            discardClicked = {},
            navigateToUpload = {},
            totalCaloriesCount = 0
        )
    }
}
