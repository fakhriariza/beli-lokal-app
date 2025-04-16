package com.example.belilokalapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.belilokalapp.databinding.FragmentProfileBinding
import com.example.belilokalapp.onboarding.LoginActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth

class ProfileBottomsheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.rlProfilePic.bringToFront()
        binding.tvName.text = auth.currentUser?.displayName
        binding.tvEmail.text = auth.currentUser?.email
        binding.tvSignOut.setOnClickListener {
            auth.signOut()
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).setButtonNav()
        _binding = null
    }
}