package com.avicodes.calorietrackerai.models

import java.time.Instant
import java.util.UUID

data class Diet(
    var id: String = UUID.randomUUID().toString(),
    var description: String = "",
    var date: Instant = Instant.now(),
    var images: List<String> = listOf(),
    var meal: String = Meal.Snacks.name
)