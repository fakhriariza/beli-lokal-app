package com.example.belilokalapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.belilokalapp.databinding.ItemMenuBinding

class CategoryAdapter(
    private val categoryList: List<String>
    ) : RecyclerView.Adapter<CategoryAdapter.ListViewHolder>() {
    private lateinit var prefManager: PrefManager
    var mListener: Listener? = null
    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        prefManager = PrefManager(holder.itemView.context)

        holder.binding.apply {
            tvTitle.text = categoryList[position]
            if (position == selectedPosition) {
                tvTitle.setBackgroundResource(R.drawable.bg_cart)
                tvTitle.setTextColor(root.context.getColor(R.color.white))
            } else {
                tvTitle.setBackgroundResource(R.drawable.bg_cart_disable)
                tvTitle.setTextColor(root.context.getColor(R.color.white))
            }

            root.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = position

                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)

                mListener?.onClick(categoryList[position])
            }

        }
    }

    override fun getItemCount(): Int = categoryList.size

    class ListViewHolder(val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root) {}

    interface Listener {
        fun onClick(category: String)
    }
}