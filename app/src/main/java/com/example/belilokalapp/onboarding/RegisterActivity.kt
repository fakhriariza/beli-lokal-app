package com.example.belilokalapp.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.belilokalapp.MainActivity
import com.example.belilokalapp.PrefManager
import com.example.belilokalapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var prefManager: PrefManager
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager(this)
        register()
    }

    private fun register() = with(binding) {
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val name = etName.text.toString()
            val password = etPassword.text.toString()

            if (email.isNotEmpty() && name.isNotEmpty() && password.isNotEmpty()) {
                registerUser(email, password, name)
            } else {
                Toast.makeText(this@RegisterActivity, "Maaf, Belum Semua Diisi", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        tvRegister.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun registerUser(email: String, password: String, name: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()

                    auth.currentUser?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val intent = Intent(applicationContext, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@RegisterActivity, "Pendaftaran Gagal", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                } else {
                    Toast.makeText(this@RegisterActivity, "Pendaftaran Gagal", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}