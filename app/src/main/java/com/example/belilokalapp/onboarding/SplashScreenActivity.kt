package com.example.belilokalapp.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.belilokalapp.MainActivity
import com.example.belilokalapp.PrefManager
import com.example.belilokalapp.databinding.ActivitySplashScreenBinding
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var bindng: ActivitySplashScreenBinding
    private lateinit var prefManager: PrefManager
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindng = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(bindng.root)

        prefManager = PrefManager(this)

        if (auth.currentUser != null) {
            val splashTime: Long = 3000
            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, splashTime)
        } else {
            val splashTime: Long = 3000
            Handler().postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, splashTime)
        }
    }
}