package com.example.belilokalapp.network

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class CartLocalData(
    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating,
    val quantity: Int,
) : Parcelable
