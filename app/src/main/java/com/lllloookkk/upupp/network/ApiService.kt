package com.lllloookkk.upupp.network

import com.lllloookkk.upupp.bean.LookConfig
import com.lllloookkk.upupp.bean.LookInfo
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("lookup/p3z")
    fun getConfig(@Body requestBody: RequestBody): Call<LookConfig>

    @POST("lookup/w3k")
    fun getInfo(@Body requestBody: RequestBody): Call<LookInfo>

    @POST("lookup/s3j")
    fun postEvent(@Body requestBody: RequestBody): Call<Response>
}