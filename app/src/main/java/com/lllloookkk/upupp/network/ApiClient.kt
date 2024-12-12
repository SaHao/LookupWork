package com.lllloookkk.upupp.network

import com.lllloookkk.upupp.bean.EventData
import com.lllloookkk.upupp.bean.LookConfig
import com.lllloookkk.upupp.bean.LookInfo
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ApiClient {
    private val apiService = NetworkModule.creatService(ApiService::class.java)

    fun getConfig(requestBody: RequestBody,  callback: ApiCallback<LookConfig>) {
        apiService.getConfig(requestBody).enqueue(object : Callback<LookConfig> {
            override fun onResponse(call: Call<LookConfig>, response: Response<LookConfig>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback.onSuccess(it)
                    } ?: callback.onError(Exception("Response body is null"))
                } else {
                    callback.onError(Exception("Error: ${response.code()}"))
                }
            }
            override fun onFailure(call: Call<LookConfig>, t: Throwable) {
                callback.onError(Exception("Network error: ${t.message}"))
            }
        })
    }

    fun getInfo(requestBody: RequestBody, callback: ApiCallback<LookInfo>) {
        apiService.getInfo(requestBody).enqueue(object : Callback<LookInfo> {
            override fun onResponse(call: Call<LookInfo>, response: Response<LookInfo>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback.onSuccess(it)
                    } ?: callback.onError(Exception("Response body is null"))
                } else {
                    callback.onError(Exception("Error: ${response.code()}"))
                }
            }
            override fun onFailure(call: Call<LookInfo>, t: Throwable) {
                callback.onError(Exception("Network error: ${t.message}"))
            }
        })
    }
    fun postEvent(requestBody: RequestBody, callback: ApiCallback<EventData>) {
        apiService.postEvent(requestBody).enqueue(object : Callback<EventData> {
            override fun onResponse(call: Call<EventData>, response: Response<EventData>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback.onSuccess(it)
                    } ?: callback.onError(Exception("Response body is null"))
                } else {
                    callback.onError(Exception("Error: ${response.code()}"))
                }
            }
            override fun onFailure(call: Call<EventData>, t: Throwable) {
                callback.onError(Exception("Network error: ${t.message}"))
            }
        })
    }



}