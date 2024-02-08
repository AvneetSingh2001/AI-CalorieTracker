package com.avicodes.calorietrackerai.presentation.screens.history

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.LayoutDirection
import com.avicodes.calorietrackerai.models.Meals
import java.time.ZonedDateTime

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HistoryScreen(
    meals: Meals,
    onBackPressed: () -> Unit,
    navigateToMealDetail: (String) -> Unit,
    dateIsSelected: Boolean,
    onDateSelected: (ZonedDateTime) -> Unit,
    onDateReset: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HistoryTopBar(
                scrollBehavior = scrollBehavior,
                onBackPressed = onBackPressed,
                dateIsSelected = dateIsSelected,
                onDateSelected = onDateSelected,
                onDateReset = onDateReset
            )
        },
        content = { paddingValues ->
            HistoryContent(
                paddingValues = paddingValues,
                meals = meals,
                onClick = navigateToMealDetail
            )
        }
    )
}
