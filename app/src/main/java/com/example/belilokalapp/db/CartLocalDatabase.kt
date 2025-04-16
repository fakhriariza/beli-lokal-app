package com.example.belilokalapp.db

import com.example.belilokalapp.db.Converters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.belilokalapp.network.CartLocalData

@Database(entities = [CartLocalData::class], version = 1)
@TypeConverters(Converters::class)
abstract class CartLocalDatabase : RoomDatabase() {

    companion object {
        const val NAME = "Cart DB"
    }

    abstract fun getCartLocalDataDao(): ProductDao
}