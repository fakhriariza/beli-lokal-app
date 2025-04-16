package com.example.belilokalapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.belilokalapp.databinding.ActivityPurchaseStatusBinding
import com.example.belilokalapp.network.CartLocalData

class PurchaseStatusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPurchaseStatusBinding
    private lateinit var adapter: ConfirmDetailAdapter
    private lateinit var prefManager: PrefManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchaseStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager(this)
        val productsList = intent.getParcelableArrayListExtra<CartLocalData>("product_local")
        if (productsList != null) {
            setAdapter(productsList)
        }
        val product = intent.getParcelableExtra<CartLocalData>("product_data")
        product?.let {
            setAdapter(listOf(product))
        }
        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() = with(binding) {
        btnBack.setOnClickListener {
            val intent = Intent(this@PurchaseStatusActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun setAdapter(data: List<CartLocalData>) {
        adapter = ConfirmDetailAdapter(data)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvProductDetail.layoutManager = layoutManager
        binding.rvProductDetail.adapter = adapter.apply {
        }
        binding.tvPrice.text =  "${prefManager.getRupiahFormat((data.sumOf { it.price * it.quantity }).toInt())}"


    }
}