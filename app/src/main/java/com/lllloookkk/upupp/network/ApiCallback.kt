package com.lllloookkk.upupp.network

import com.lllloookkk.upupp.bean.LookConfig

interface ApiCallback {
    fun onSuccess(config: LookConfig)
    fun onError(e: Exception)
}