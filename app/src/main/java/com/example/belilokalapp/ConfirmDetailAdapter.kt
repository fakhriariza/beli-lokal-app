package com.example.belilokalapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.belilokalapp.databinding.ItemResultBinding
import com.example.belilokalapp.network.CartLocalData

class ConfirmDetailAdapter(
    private val productList: List<CartLocalData>
    ) : RecyclerView.Adapter<ConfirmDetailAdapter.ListViewHolder>() {
    private lateinit var prefManager: PrefManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, i: Int) {
        prefManager = PrefManager(holder.itemView.context)

        holder.binding.apply {
            val productName = productList[i].title
            val price = productList[i].price
            val quantity = productList[i].quantity
            val totalPrice = productList[i].price * productList[i].quantity

            tvProductTitle.text = productName
            tvQuantity.text = quantity.toString()
            tvPrice.text = prefManager.getRupiahFormat(price.toInt())
            tvTotalPrice.text = prefManager.getRupiahFormat(totalPrice.toInt())
        }
    }

    override fun getItemCount(): Int = productList.size

    class ListViewHolder(val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {}
}