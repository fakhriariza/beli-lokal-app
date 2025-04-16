package com.example.belilokalapp.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitServicesInstance {

    @GET("products")
    fun getDataProducts(): Call<List<ProductsData>>

    @GET("products/categories")
    fun getCategory(): Call<List<String>>

    @GET("products/category/{category}")
    fun getProductByCategory(@Path("category") category: String): Call<List<ProductsData>>
}