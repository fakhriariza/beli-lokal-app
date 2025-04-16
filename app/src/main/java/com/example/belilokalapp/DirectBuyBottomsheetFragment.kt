package com.example.belilokalapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.belilokalapp.databinding.FragmentDirectBuyBinding
import com.example.belilokalapp.network.CartLocalData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DirectBuyBottomsheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDirectBuyBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefManager: PrefManager
    private var product: CartLocalData? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDirectBuyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(context)


        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        product = arguments?.getParcelable(ARG_PRODUCT)
        binding.tvProductTitle.text = product?.title
        binding.tvPrice.text = prefManager.getRupiahFormat(product?.price?.toInt())
        binding.tvTotalQuantity.text = product?.quantity.toString()
        binding.ivProduct.let {
            Glide.with(it)
                .load(product?.image)
                .into(it)
        }
        binding.tvTotal.text = "Total ${prefManager.getRupiahFormat((1 * product?.price!!).toInt())}"
        binding.ivPlus.setOnClickListener {
            product?.let {
                val newQuantity = it.quantity + 1
                product = it.copy(quantity = newQuantity)
                binding.tvTotalQuantity.text = newQuantity.toString()
                binding.tvTotal.text = "Total ${prefManager.getRupiahFormat((newQuantity * it.price).toInt())}"
            }
        }
        binding.ivMinus.setOnClickListener{
            product?.let {
                val newQuantity = if (it.quantity > 1) it.quantity - 1 else 1
                product = it.copy(quantity = newQuantity)
                binding.tvTotalQuantity.text = newQuantity.toString()
                binding.tvTotal.text = "Total ${prefManager.getRupiahFormat((newQuantity * it.price).toInt())}"
            }
        }
        binding.btnAdd.setOnClickListener {
            product?.let {
                val intent = Intent(requireContext(), PurchaseStatusActivity::class.java)
                intent.putExtra("product_data", it)
                startActivity(intent)
                activity?.finish()
                dismiss()
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_PRODUCT = "product_local"

        fun newInstance(product: CartLocalData): DirectBuyBottomsheetFragment {
            val fragment = DirectBuyBottomsheetFragment()
            val args = Bundle()
            args.putParcelable(ARG_PRODUCT, product)
            fragment.arguments = args
            return fragment
        }
    }
}