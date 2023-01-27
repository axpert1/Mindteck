package com.app.mindteck.remote

import com.app.mindteck.remote.interceptor.CacheInterceptor
import com.app.mindteck.remote.interceptor.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://restcountries.com/v2/"

object ServiceGenerator {
    inline operator fun <reified T> invoke(
        connectivityInterceptor: NetworkConnectionInterceptor
    ): T {
        val okHttpClient = OkHttpClient.Builder().addNetworkInterceptor(CacheInterceptor()).addInterceptor(connectivityInterceptor).build()
                return Retrofit.Builder().baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(T::class.java)
    }
}