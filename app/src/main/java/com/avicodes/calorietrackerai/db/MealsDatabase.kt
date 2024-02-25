package com.avicodes.calorietrackerai.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.avicodes.calorietrackerai.models.Meal
import com.avicodes.calorietrackerai.utils.Converters


@Database(entities = [Meal::class], version = 1, exportSchema = false)
@TypeConverters(Converters.StringListConverter::class)
abstract class MealsDatabase: RoomDatabase() {
    abstract fun mealsDao(): MealsDao
}