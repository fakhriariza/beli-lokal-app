package com.example.belilokalapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.belilokalapp.databinding.ActivityProductDetailBinding
import com.example.belilokalapp.network.CartLocalData
import com.example.belilokalapp.network.ProductsData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var prefManager: PrefManager
    private val viewModel: MainViewModel by viewModels()
    private lateinit var productData: ProductsData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productData = intent.getParcelableExtra("product_data") ?: return
        prefManager = PrefManager(this)

        initVM()
        initView()
    }

    @SuppressLint("SetTextI18n", "SuspiciousIndentation")
    private fun initView() = with(binding) {
        tvTitle.text = productData.title
        tvRating.text = productData.rating.rate.toString()
        tvDesc.text = productData.description
        tvPrice.text = prefManager.getRupiahFormat(productData.price.toInt())
        ivDetails.let {
                Glide.with(it)
                    .load(productData.image)
                    .into(it)
        }
        ivBack.setOnClickListener {
            val intent = Intent(this@ProductDetailActivity, MainActivity::class.java)
            startActivity(intent)
        }
        btnAdd.setOnClickListener {
            viewModel.addCart(productData.toCartLocalData())
            btnDisable()
        }
        rlCart.setOnClickListener {
                val intent = Intent(this@ProductDetailActivity, CartActivity::class.java)
                startActivity(intent)
        }
        btnDirectPayment.setOnClickListener {
            val product = CartLocalData(
                id = productData.id,
                title = productData.title,
                price = productData.price,
                description = productData.description,
                category = productData.category,
                image = productData.image,
                rating = productData.rating,
                quantity = 1
            )

            val bottomSheet = DirectBuyBottomsheetFragment.newInstance(product)
            bottomSheet.show(supportFragmentManager, "DirectBottomSheet")
        }
    }

    private fun initVM() {
        viewModel.cartList.observe(this@ProductDetailActivity, Observer {
            if (it != null && it.isNotEmpty()) {
                binding.tvCount.text = it.size.toString()
                val isProductInCart = it.any { it.id == productData.id }
                if (isProductInCart) {
                    btnDisable()
                }
            } else {
                binding.tvCount.visibility = View.GONE
            }
        })
    }

    private fun btnDisable() {
        binding.btnAdd.isEnabled = false
        binding.btnAdd.text = "Produk Sudah Di Keranjang"
        binding.btnAdd.setBackgroundResource(R.drawable.bg_cart_disable)
    }


    fun ProductsData.toCartLocalData(): CartLocalData {
        return CartLocalData(
            id = this.id,
            title = this.title,
            price = this.price,
            description = this.description,
            category = this.category,
            image = this.image,
            rating = this.rating,
            quantity = this.quantity + 1
        )
    }
}