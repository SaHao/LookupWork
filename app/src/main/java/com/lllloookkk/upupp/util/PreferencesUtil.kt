package com.lllloookkk.upupp.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.lllloookkk.upupp.bean.LookConfig
import com.lllloookkk.upupp.bean.LookInfo

object PreferencesUtil {
    private const val PREF_NAME = "app_look"
    private lateinit var sharedPreferences: SharedPreferences

    // 获取 SharedPreferences 实例
    fun init(context: Context) {
        if (!::sharedPreferences.isInitialized) {
            sharedPreferences =
                context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    fun saveConfig(settingName: String, settingValue: LookConfig) {
        val jsonStr = Gson().toJson(settingValue)
        val editor = sharedPreferences.edit()
        editor.putString(settingName, jsonStr)
        editor.apply()
    }

    fun saveInfo(settingName: String, settingValue: LookInfo) {
        val jsonStr = Gson().toJson(settingValue)
        val editor = sharedPreferences.edit()
        editor.putString(settingName, jsonStr)
        editor.apply()
    }

    fun getConfig(): LookConfig {
        return Gson().fromJson(
            sharedPreferences.getString("lookConfig", ""),
            LookConfig::class.java
        )
    }

    fun getInfo(): LookInfo {
        return Gson().fromJson(sharedPreferences.getString("lookInfo", ""), LookInfo::class.java)
    }

    // 存储字符串数据
    fun putString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()  // 异步保存
    }

    // 获取字符串数据
    fun getString(key: String, defaultValue: String? = null): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    // 存储布尔值
    fun putBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    // 获取布尔值
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    // 存储整数数据
    fun putInt(key: String, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    // 获取整数数据
    fun getInt(key: String, defaultValue: Int = 0): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    // 存储长整型数据
    fun putLong(key: String, value: Long) {
        val editor = sharedPreferences.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    // 获取长整型数据
    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    // 存储浮动数数据
    fun putFloat(key: String, value: Float) {
        val editor = sharedPreferences.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    // 获取浮动数数据
    fun getFloat(key: String, defaultValue: Float = 0f): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }

    // 删除数据
    fun remove(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    // 清空所有数据
    fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}