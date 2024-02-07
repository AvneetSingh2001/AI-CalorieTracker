package com.avicodes.calorietrackerai.models

import java.util.UUID

data class Diet(
    var id: String = UUID.randomUUID().toString(),
    var description: String = "an apple a day keeps doctor away",
    var date: String = "",
    var images: List<String> = listOf(),
    var meal: String = Meal.Snacks.name
)