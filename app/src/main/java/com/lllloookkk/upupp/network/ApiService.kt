package com.lllloookkk.upupp.network

import com.lllloookkk.upupp.bean.EventData
import com.lllloookkk.upupp.bean.LookConfig
import com.lllloookkk.upupp.bean.LookInfo
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @POST("lookup/p3z")
    @Headers("Content-Type:application/json")
    fun getConfig(@Body requestBody: RequestBody): Call<LookConfig>

    @POST("lookup/w3k")
    @Headers("Content-Type:application/json")
    fun getInfo(@Body requestBody: RequestBody): Call<LookInfo>

    @POST("lookup/s3j")
    @Headers("Content-Type:application/json")
    fun postEvent(@Body requestBody: RequestBody): Call<EventData>
}