package com.example.belilokalapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.belilokalapp.network.CartLocalData

@Dao
interface ProductDao {

    @Query("SELECT * FROM CartLocalData")
    fun getCartLocalDataList() : LiveData<List<CartLocalData>>

    @Insert
    suspend fun addCart(cart : CartLocalData)

    @Query("DELETE FROM CartLocalData where id = :id")
    suspend fun deleteCart(id: Int)

    @Query("UPDATE CartLocalData SET quantity = :quantity WHERE id = :id")
    suspend fun updateQuantity(id: Int, quantity: Int)

    @Query("DELETE FROM CartLocalData")
    suspend fun deleteAll()

}