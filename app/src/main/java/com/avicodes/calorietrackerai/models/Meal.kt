package com.avicodes.calorietrackerai.models

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.avicodes.calorietrackerai.utils.Constants.MEAL_TABLE
import dagger.Provides
import java.time.Instant
import java.time.LocalDate
import java.util.UUID


@Entity(tableName = MEAL_TABLE)
data class Meal(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var description: String = "",
    var date: String = "",
    var images: List<String> = listOf(),
    var mealName: String = MealName.Snacks.name,
    var calories: Int = 0
)

typealias Meals = Map<LocalDate, List<Meal>>
