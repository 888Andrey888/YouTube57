package com.example.youtube57.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.youtube57.R
import com.example.youtube57.core.network.RemoteDataSource
import com.example.youtube57.core.network.RetrofitClient
import com.example.youtube57.domain.repository.Repository


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object {
        private val retrofitClient = RetrofitClient().createApiService()
        private val remoteDataSource = RemoteDataSource(retrofitClient)
        internal val repository = Repository(remoteDataSource)
    }
}