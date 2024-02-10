package com.avicodes.calorietrackerai.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.avicodes.calorietrackerai.models.MealName
import java.time.LocalTime

fun Modifier.dashedBorder(
    color: Color,
    shape: Shape,
    strokeWidth: Dp = 1.dp,
    dashWidth: Dp = 4.dp,
    gapWidth: Dp = 4.dp,
    cap: StrokeCap = StrokeCap.Round
) = this.drawWithContent {
    val outline = shape.createOutline(size, layoutDirection, this)

    val path = Path()
    path.addOutline(outline)

    val stroke = Stroke(
        cap = cap,
        width = strokeWidth.toPx(),
        pathEffect = PathEffect.dashPathEffect(
            intervals = floatArrayOf(dashWidth.toPx(), gapWidth.toPx()),
            phase = 0f
        )
    )

    this.drawContent()

    drawPath(
        path = path,
        style = stroke,
        color = color
    )
}


fun getCurrentMeal(): MealName {
    val currentTime = LocalTime.now()
    return when {
        currentTime.isBefore(LocalTime.of(4, 0)) -> MealName.Dinner
        currentTime.isBefore(LocalTime.of(10, 0)) -> MealName.Breakfast
        currentTime.isBefore(LocalTime.of(12, 0)) -> MealName.Brunch
        currentTime.isBefore(LocalTime.of(15, 0)) -> MealName.Lunch
        currentTime.isBefore(LocalTime.of(18, 0)) -> MealName.Snacks
        else -> MealName.Dinner
    }
}