package com.lllloookkk.upupp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {
    private const val BASE_URL = "http://xvtxpcjrka.execute-api.us-east-2.amazonaws.com/Prod/"

    private fun creatOKHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(CharsetInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS).build()
    }

    private fun creatRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(creatOKHttpClient())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun <T> creatService(serviceClass: Class<T>): T {
        return creatRetrofit().create(serviceClass)
    }


}