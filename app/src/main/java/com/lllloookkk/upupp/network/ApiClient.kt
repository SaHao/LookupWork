package com.lllloookkk.upupp.network

import com.lllloookkk.upupp.bean.LookConfig
import com.lllloookkk.upupp.bean.LookInfo
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ApiClient {
    private val apiService = NetworkModule.creatService(ApiService::class.java)

    fun getConfig(requestBody: RequestBody,  callback: ApiCallback) {
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

    fun getInfo(requestBody: RequestBody, callback: (Result<LookInfo>) -> Unit) {
        apiService.getInfo(requestBody).enqueue(object : Callback<LookInfo> {
            override fun onResponse(call: Call<LookInfo>, response: Response<LookInfo>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback(Result.Success(it))
                    } ?: callback(Result.Error(Exception("Response body is null")))
                } else {
                    callback(Result.Error(Exception("Error: ${response.code()}")))
                }
            }
            override fun onFailure(call: Call<LookInfo>, t: Throwable) {
                callback(Result.Error(Exception("Network error: ${t.message}")))
            }
        })
    }

    fun postEvent(requestBody: RequestBody, callback: (Result<okhttp3.Response>) -> Unit) {
        apiService.postEvent(requestBody).enqueue(object : Callback<okhttp3.Response> {
            override fun onResponse(call: Call<okhttp3.Response>, response: Response<okhttp3.Response>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback(Result.Success(it))
                    } ?: callback(Result.Error(Exception("Response body is null")))
                } else {
                    callback(Result.Error(Exception("Error: ${response.code()}")))
                }
            }
            override fun onFailure(call: Call<okhttp3.Response>, t: Throwable) {
                callback(Result.Error(Exception("Network error: ${t.message}")))
            }
        })
    }


}