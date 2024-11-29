package com.lllloookkk.upupp.bean

data class LookInfo(
    val code: Int,
    val data: List<InfoData>
)

data class InfoData(
    val address: String,
    val city: String,
    val company: String,
    val companyStar: String,
    val contacts: List<InfoContact>,
    val descriptions: List<Description>,
    val host: Int,
    val id: String,
    val jobType: String,
    val salary: String,
    val tag: String,
    val tags: List<String>,
    val tid: Int,
    val title: String,
    val top: Int,
    val type: String
)

data class InfoContact(
    val action: String,
    val id: String,
    val name: String,
    val packages: List<String>,
    val rate: Int,
    val store: Store,
    val text: String,
    val type: String,
    val url: String
)

data class Description(
    val contents: List<String>,
    val title: String
)

data class Store(
    val action: String,
    val packages: List<String>,
    val url: String
)