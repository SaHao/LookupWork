package com.lllloookkk.upupp.network

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject

class CharsetInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val mediaType = response.body?.contentType()
        val content = response.body?.string()
        val body = if (content != null && mediaType?.type == "application" && mediaType.subtype == "json") {
            val prettyJson = try {
                val jsonObj = JSONObject(content)
                jsonObj.toString(4) // 4 是缩进级别
            } catch (e: JSONException) {
                content
            }
            ResponseBody.create(mediaType, prettyJson)
        } else {
            ResponseBody.create(mediaType, content ?: "")
        }

        return response.newBuilder()
            .body(body)
            .build()
    }
    }
