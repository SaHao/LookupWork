package com.lllloookkk.upupp.network


interface ApiCallback<T> {
    fun onSuccess(result: T)
    fun onError(e: Exception)
}