package com.avicodes.calorietrackerai.presentation.screens.history

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HistoryScreen(onBackClicked: () -> Unit) {
    Scaffold(
        topBar = { HistoryTopBar(onBackClicked = onBackClicked) },
        content = { HistoryContent() }
    )
}

@Preview
@Composable
fun HistoryScreenPreview() {
    HistoryScreen(
        onBackClicked = { }
    )
}