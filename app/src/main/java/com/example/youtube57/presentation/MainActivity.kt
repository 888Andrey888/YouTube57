package com.example.youtube57.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.youtube57.R
import com.example.youtube57.core.network.RetrofitClient
import com.example.youtube57.domain.repository.Repository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object{
        internal val retrofitClient = RetrofitClient().createApiService()
        internal val repository = Repository(retrofitClient)
    }
}