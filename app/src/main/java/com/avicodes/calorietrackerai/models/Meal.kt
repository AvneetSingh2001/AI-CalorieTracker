package com.avicodes.calorietrackerai.models

import android.net.Uri
import java.time.Instant
import java.time.LocalDate
import java.util.UUID

data class Meal(
    var id: String = UUID.randomUUID().toString(),
    var description: String = "",
    var date: Instant = Instant.now(),
    var images: List<Uri> = listOf(),
    var mealName: String = MealName.Snacks.name,
    var calories: Int = 0
)

typealias Meals = Map<LocalDate, List<Meal>>
