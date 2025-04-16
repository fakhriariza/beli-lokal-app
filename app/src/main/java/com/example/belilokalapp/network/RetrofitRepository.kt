package com.example.belilokalapp.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RetrofitRepository @Inject constructor(private val retroInstance: RetrofitServicesInstance) {

    fun getProductData(callback: (List<ProductsData>?, String?) -> Unit) {
        val call: Call<List<ProductsData>> = retroInstance.getDataProducts()
        call.enqueue(object : Callback<List<ProductsData>> {
            override fun onResponse(call: Call<List<ProductsData>>, response: Response<List<ProductsData>>) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, "Error: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ProductsData>>, t: Throwable) {
                callback(null, t.message ?: "Unknown error")
            }
        })
    }

        fun getCategory(callback: (List<String>?) -> Unit) {
            val call: Call<List<String>> = retroInstance.getCategory()
            call.enqueue(object : Callback<List<String>> {
                override fun onResponse(
                    call: Call<List<String>>,
                    response: Response<List<String>>
                ) {
                    callback(response.body())
                }

                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    callback(null)
                }
            })
        }

    fun getProductDataByCategory(category: String, callback: (List<ProductsData>?, String?) -> Unit) {
        val call: Call<List<ProductsData>> = retroInstance.getProductByCategory(category)
        call.enqueue(object : Callback<List<ProductsData>> {
            override fun onResponse(call: Call<List<ProductsData>>, response: Response<List<ProductsData>>) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, "Error: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ProductsData>>, t: Throwable) {
                callback(null, t.message ?: "Unknown error")
            }
        })
    }

}