package com.example.belilokalapp.di

import android.content.Context
import androidx.room.Room
import com.example.belilokalapp.db.CartLocalDatabase
import com.example.belilokalapp.db.ProductDao
import com.example.belilokalapp.network.RetrofitServicesInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    val baseUrl = "https://fakestoreapi.com/"

    @Singleton
    @Provides
    fun getRetrofitServicesInstance(retrofit: Retrofit): RetrofitServicesInstance {
        return retrofit.create(RetrofitServicesInstance::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): CartLocalDatabase {
        return Room.databaseBuilder(
            context,
            CartLocalDatabase::class.java,
            CartLocalDatabase.NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideProductDao(
        database: CartLocalDatabase
    ): ProductDao {
        return database.getCartLocalDataDao()
    }
}