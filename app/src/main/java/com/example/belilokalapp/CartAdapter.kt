package com.example.belanjayuk.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.belilokalapp.PrefManager
import com.example.belilokalapp.databinding.ItemCartBinding
import com.example.belilokalapp.network.CartLocalData

class CartAdapter(
    private val productsList: List<CartLocalData>
    ) : RecyclerView.Adapter<CartAdapter.ListViewHolder>() {
    private lateinit var prefManager: PrefManager
    var mListener: Listener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, i: Int) {
        prefManager = PrefManager(holder.itemView.context)

        holder.binding.apply {
            val item = productsList[i]
            if (item != null) {
                tvProductTitle.text = item.title
                tvPrice.text = prefManager.getRupiahFormat(item.price.toInt())
                tvTotalQuantity.text = productsList[i].quantity.toString()
                ivProduct.let {
                    Glide.with(it)
                        .load(item.image)
                        .into(it)
                }
                ivPlus.setOnClickListener {
                    mListener?.add(productsList[i].id, productsList[i].quantity)
                    mListener?.totalPrice((productsList.sumOf { it.price * it.quantity }).toInt())
                }
                ivMinus.setOnClickListener {
                    mListener?.min(productsList[i].id, productsList[i].quantity)
                    mListener?.totalPrice((productsList.sumOf { it.price * it.quantity }).toInt())
                }
                ivDelete.setOnClickListener {
                    mListener?.deleteItems(productsList[i].id)
                    mListener?.totalPrice((productsList.sumOf { it.price * it.quantity }).toInt())
                }

                tvTotal.text = "Total ${prefManager.getRupiahFormat((productsList[i].price * productsList[i].quantity).toInt())}"
            } else {
                rlProductCard.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = productsList.size

    class ListViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {}

    interface Listener {
        fun onClick(id: Int)
        fun add(id: Int, qty: Int)
        fun min(id: Int, qty: Int)
        fun deleteItems(id: Int)
        fun totalPrice(totalPrice: Int)
    }
}