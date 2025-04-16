package com.example.belilokalapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.belilokalapp.databinding.ItemProductBinding
import com.example.belilokalapp.network.ProductsData

class ProductsAdapter(
    private val productsList: List<ProductsData>
    ) : RecyclerView.Adapter<ProductsAdapter.ListViewHolder>() {
    private lateinit var prefManager: PrefManager
    var mListener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, i: Int) {
        prefManager = PrefManager(holder.itemView.context)

        holder.binding.apply {
            tvProductTitle.text = productsList[i].title
            tvPrice.text = prefManager.getRupiahFormat(productsList[i].price.toInt())
            ivProduct.let {
                Glide.with(it)
                    .load(productsList[i].image)
                    .centerCrop()
                    .fitCenter()
                    .override(400,400)
                    .into(it)
            }
            rlProductCard.setOnClickListener {
                mListener?.onClick(productsList[i])
            }
        }
    }

    override fun getItemCount(): Int = productsList.size

    class ListViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {}

    interface Listener {
        fun onClick(cartLocalData: ProductsData)
    }
}