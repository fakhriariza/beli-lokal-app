package com.example.belilokalapp.network

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class ProductsData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating,
    val quantity: Int,
) : Parcelable

@Parcelize
data class Rating(
    val rate: Double,
    val count: Int
) : Parcelable