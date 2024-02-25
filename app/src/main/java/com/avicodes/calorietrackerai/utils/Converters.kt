package com.avicodes.calorietrackerai.utils

import androidx.room.TypeConverter
import java.time.LocalDateTime

class Converters {
    class StringListConverter {
        @TypeConverter
        fun fromString(value: String): List<String> {
            return value.split(",").map { it.trim() }
        }

        @TypeConverter
        fun toString(value: List<String>): String {
            return value.joinToString(",")
        }
    }
}