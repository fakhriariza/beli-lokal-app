package com.example.belilokalapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.belilokalapp.db.ProductDao
import com.example.belilokalapp.network.CartLocalData
import com.example.belilokalapp.network.ProductsData
import com.example.belilokalapp.network.RetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: RetrofitRepository, private val productDao: ProductDao): ViewModel() {
    var liveDataProducts: MutableLiveData<List<ProductsData>> = MutableLiveData()
    var liveDataCategory: MutableLiveData<List<String>> = MutableLiveData()
    val cartList: LiveData<List<CartLocalData>> = productDao.getCartLocalDataList()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessageCategory: MutableLiveData<String> = MutableLiveData()

    fun getLiveDataProduct(): MutableLiveData<List<ProductsData>>{
        return liveDataProducts
    }

    fun loadListProduct() {
        repository.getProductData { result, error ->
            if (error != null) {
                errorMessage.postValue(error)
            } else {
                errorMessage.postValue(null)
                liveDataProducts.postValue(result)
            }
        }
    }

    fun getCategory(): MutableLiveData<List<String>> {
        return liveDataCategory
    }

    fun loadCategory() {
        repository.getCategory { result ->
            liveDataCategory.postValue(result)
        }
    }

    fun getProductByCategory(): MutableLiveData<List<ProductsData>> {
        return liveDataProducts
    }

    fun loadProductByCategory(category: String) {
        repository.getProductDataByCategory(category) { result, error ->
            if (error != null) {
                errorMessageCategory.postValue(error)
            } else {
                errorMessageCategory.postValue(null)
                liveDataProducts.postValue(result)
            }
        }
    }

    fun addCart(cart: CartLocalData) {
        viewModelScope.launch {
            productDao.addCart(cart)
        }
    }

    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            productDao.deleteCart(id)
        }
    }

    fun updateCartQuantity(id: Int, quantity: Int) {
        viewModelScope.launch {
            productDao.updateQuantity(id, quantity)
        }
    }

    fun deleteAllCart() {
        viewModelScope.launch {
            productDao.deleteAll()
        }
    }
}