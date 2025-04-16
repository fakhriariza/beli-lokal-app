package com.example.belilokalapp.db

import androidx.room.TypeConverter
import com.example.belilokalapp.network.Rating
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromRating(rating: Rating?): String? {
        return gson.toJson(rating)
    }

    @TypeConverter
    fun toRating(ratingJson: String?): Rating? {
        return gson.fromJson(ratingJson, object : TypeToken<Rating>() {}.type)
    }
}