package com.avicodes.calorietrackerai.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.avicodes.calorietrackerai.models.Meal

@Dao
interface MealsDao {
    @Insert
    suspend fun insertMeal(meal: Meal)

    @Update
    suspend fun updateMeal(meal: Meal)

    @Query("SELECT * FROM meals_table")
    suspend fun getAllMeals(): List<Meal>

    @Query("DELETE FROM meals_table WHERE id=:id")
    suspend fun deleteMeal(id: String)
}