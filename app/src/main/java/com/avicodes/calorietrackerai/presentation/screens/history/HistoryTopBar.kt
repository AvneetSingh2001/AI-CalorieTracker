package com.avicodes.calorietrackerai.presentation.screens.history

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryTopBar(onBackClicked: () -> Unit) {
    TopAppBar(
        navigationIcon = { BackButton(onClick = onBackClicked) },
        title = { Text(text = "History") },
        actions = { DateRangeButton(onClick = onBackClicked) }
    )
}

@Composable
fun BackButton(
    onClick: () -> Unit
) {
    IconButton(onClick = { onClick.invoke() }) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun DateRangeButton(
    onClick: () -> Unit
) {
    IconButton(onClick = { onClick.invoke() }) {
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}