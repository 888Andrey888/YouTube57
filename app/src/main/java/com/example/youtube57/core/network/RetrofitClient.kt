package com.example.youtube57.core.network


import com.example.youtube57.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun provideInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
}

fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder().addInterceptor(interceptor).readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS).connectTimeout(20, TimeUnit.SECONDS).build()
}

fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient).build()
}

fun provideApiService(retrofitClient: Retrofit): ApiService {
    return retrofitClient.create(ApiService::class.java)
}