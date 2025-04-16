package com.example.belilokalapp

import android.app.Application
import androidx.room.Room
import com.example.belilokalapp.db.CartLocalDatabase
import com.example.belilokalapp.network.CartLocalData
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BeliLokapApp: Application() {
}