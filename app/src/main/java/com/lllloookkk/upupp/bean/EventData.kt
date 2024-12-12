package com.lllloookkk.upupp.bean

data class EventData(
    val code: Int,
    val data: EData
)

data class EData(
    val refresh: Boolean
)