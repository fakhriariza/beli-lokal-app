package com.example.belilokalapp.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.belilokalapp.MainActivity
import com.example.belilokalapp.PrefManager
import com.example.belilokalapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PrefManager
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager(this)
        loginSession()
    }

    private fun loginSession() = with(binding) {
        btnSignIn.setOnClickListener {
            val userIdentifier = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (userIdentifier.isNotEmpty() && password.isNotEmpty()) {
                loginUser(userIdentifier, password)
            } else {
                Toast.makeText(this@LoginActivity, "Maaf, Username dan Password tidak sesuai", Toast.LENGTH_SHORT)
                    .show()}
            }
            tvRegister.setOnClickListener {
                val intent = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
    }
    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Gagal Masuk", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}