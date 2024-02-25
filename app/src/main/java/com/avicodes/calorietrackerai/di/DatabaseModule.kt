package com.avicodes.calorietrackerai.di

import android.content.Context
import androidx.room.Room
import com.avicodes.calorietrackerai.db.MealsDao
import com.avicodes.calorietrackerai.db.MealsDatabase
import com.avicodes.calorietrackerai.utils.Constants.MEALS_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(
        @ApplicationContext context: Context
    ): MealsDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = MealsDatabase::class.java,
            name = MEALS_DATABASE
        ).build()
    }

    @Singleton
    @Provides
    fun providesMealsDao(database: MealsDatabase): MealsDao = database.mealsDao()
}