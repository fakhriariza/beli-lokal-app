package com.example.belilokalapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.belanjayuk.main.CartAdapter
import com.example.belilokalapp.databinding.ActivityCartBinding
import com.example.belilokalapp.network.CartLocalData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var prefManager: PrefManager
    private val viewModel: MainViewModel by viewModels()
    private lateinit var productsList: List<CartLocalData>

    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager(this)
        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() = with(binding) {
        viewModel.cartList.observe(this@CartActivity, Observer {
            if (it != null) {
                if (it.isNotEmpty()) {
                    rvProductList.visibility = View.VISIBLE
                    productsList = it
                    tvTotalPrice.text =
                        "Total ${prefManager.getRupiahFormat((it.sumOf { it.price * it.quantity }).toInt())}"
                    cartAdapter = CartAdapter(it)
                    val layoutManager =
                        LinearLayoutManager(this@CartActivity, LinearLayoutManager.VERTICAL, false)
                    binding.rvProductList.layoutManager = layoutManager
                    binding.rvProductList.adapter = cartAdapter.apply {
                        mListener = object : CartAdapter.Listener {
                            override fun onClick(id: Int) {
                                TODO("Not yet implemented")
                            }

                            override fun add(id: Int, qty: Int) {
                                val newQuantity = qty + 1
                                viewModel.updateCartQuantity(id, newQuantity)
                            }

                            override fun min(id: Int, qty: Int) {
                                if (qty > 1) {
                                    val newQuantity = qty - 1
                                    viewModel.updateCartQuantity(id, newQuantity)
                                }
                            }

                            override fun deleteItems(id: Int, ) {
                                viewModel.deleteProduct(id)
                            }

                            override fun totalPrice(totalPrice: Int) {
                                tvTotalPrice.visibility = View.VISIBLE
                                tvTotalPrice.text =
                                    "Total ${prefManager.getRupiahFormat(totalPrice)}"
                            }
                        }
                    }
                } else {
                    tvTotalPrice.visibility = View.GONE
                    btnAdd.visibility = View.GONE
                    rlError.visibility = View.VISIBLE
                    rvProductList.visibility = View.GONE
                }
            } else {
                tvTotalPrice.visibility = View.GONE
                btnAdd.visibility = View.GONE
                rlError.visibility = View.VISIBLE
                rvProductList.visibility = View.GONE
            }
        })

        btnAdd.setOnClickListener {
            val intent = Intent(this@CartActivity, PurchaseStatusActivity::class.java)
            intent.putParcelableArrayListExtra("product_local", ArrayList(productsList))
            startActivity(intent)
            viewModel.deleteAllCart()
            finish()
        }
    }
}
