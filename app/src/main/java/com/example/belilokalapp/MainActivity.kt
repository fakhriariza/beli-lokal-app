package com.example.belilokalapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.belilokalapp.databinding.ActivityMainBinding
import com.example.belilokalapp.network.ProductsData
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private val viewModel: MainViewModel by viewModels()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.bnvMainActivity.selectedItemId = R.id.navigation_home
        binding.tvName.text = "Halo ${auth.currentUser?.displayName}"
        initVM()
        initOnClick()
    }

    private fun initVM() {
        visibleState()
        viewModel.loadCategory()
        viewModel.getCategory().observe(this, Observer {
            if (it != null) {
                val modifiedCategories = mutableListOf("All")
                modifiedCategories.addAll(it)

                categoryAdapter = CategoryAdapter(modifiedCategories)
                val layoutManagerProduct =
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                binding.rvMenu.isNestedScrollingEnabled = false
                binding.rvMenu.layoutManager = layoutManagerProduct
                binding.rvMenu.adapter = categoryAdapter.apply {
                    mListener = object : CategoryAdapter.Listener {
                        @SuppressLint("SuspiciousIndentation")
                        override fun onClick(category: String) {
                            if (category == "All") {
                                viewModel.loadListProduct()
                            } else
                                viewModel.loadProductByCategory(category)
                        }
                    }
                }
            }
        })

        viewModel.loadListProduct()
        viewModel.getProductByCategory().observe(this, Observer {
            if (it != null) {
                viewModel.getLiveDataProduct().observe(this, Observer {
                    if (it != null) {
                        productsAdapter = ProductsAdapter(it)
                        val layoutManagerProduct = GridLayoutManager(this, 2)
                        binding.rvProductList.isNestedScrollingEnabled = false
                        binding.rvProductList.layoutManager = layoutManagerProduct
                        binding.rvProductList.adapter = productsAdapter.apply {
                            mListener = object : ProductsAdapter.Listener {
                                override fun onClick(cartLocalData: ProductsData) {
                                    val intent = Intent(this@MainActivity, ProductDetailActivity::class.java)
                                    intent.putExtra("product_data", cartLocalData)
                                    startActivity(intent)
                                }
                            }
                        }
                    }
                })
            }
        })
        viewModel.cartList.observe(this@MainActivity, Observer {
            if (it != null && it.isNotEmpty()) {
                binding.tvCount.text = it.size.toString()
            } else {
                binding.tvCount.visibility = View.GONE
            }
        })

        viewModel.errorMessage.observe(this@MainActivity, Observer {
            if (it != null) {
                errorState()
            }
        })

        viewModel.errorMessageCategory.observe(this@MainActivity, Observer {
            if (it != null) {
                errorState()
            }
        })
    }

    private fun initOnClick() {
        binding.bnvMainActivity.setOnItemSelectedListener { item: MenuItem ->
            val itemId = item.itemId
            when (itemId) {
                R.id.navigation_home -> {
                    return@setOnItemSelectedListener true
                }

                R.id.navigation_profile -> {
                    val bottomSheet = ProfileBottomsheetFragment()
                    bottomSheet.show(supportFragmentManager, "ProfileBottomsheet")
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
        binding.rlCart.setOnClickListener {
            val intent = Intent(this@MainActivity, CartActivity::class.java)
            startActivity(intent)
        }
        binding.btnRefresh.setOnClickListener {
            visibleState()
            initVM()
        }
    }

    fun setButtonNav() {
        binding.bnvMainActivity.selectedItemId = R.id.navigation_home
    }

    private fun errorState() {
        binding.rlError.visibility = View.VISIBLE
        binding.rvProductList.visibility = View.GONE
        binding.rvMenu.visibility = View.GONE
    }

    private fun visibleState() {
        binding.rlError.visibility = View.GONE
        binding.rvProductList.visibility = View.VISIBLE
        binding.rvMenu.visibility = View.VISIBLE
    }

}