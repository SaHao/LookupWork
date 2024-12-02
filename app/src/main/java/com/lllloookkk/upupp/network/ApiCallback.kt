package com.lllloookkk.upupp.network

import com.lllloookkk.upupp.bean.LookConfig
import com.lllloookkk.upupp.network.Result

interface ApiCallback<T> {
    fun onSuccess(result: T)
    fun onError(e: Exception)
}