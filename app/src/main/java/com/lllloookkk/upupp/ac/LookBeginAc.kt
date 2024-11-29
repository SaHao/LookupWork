package com.lllloookkk.upupp.ac

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lllloookkk.upupp.R
import com.lllloookkk.upupp.bean.LookConfig
import com.lllloookkk.upupp.bean.LookInfo
import com.lllloookkk.upupp.network.ApiClient
import com.lllloookkk.upupp.network.Result
import com.lllloookkk.upupp.util.PreferencesUtil
import kotlinx.coroutines.DelicateCoroutinesApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class LookBeginAc : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_look_begin)
//        GlobalScope.launch {
//            delay(2000L)
//            val intent = Intent(this@LookBeginAc, MainActivity::class.java)
//            startActivity(intent)
//        }
        getConfig()
        getInfo()
    }

    private fun getConfig() {
        val requestBody = """
    {
        "gaid": "34531535ffa-532523j-5235n2k35-5325n",
        "attributes": "tt:1176xgf3 tn:test net:test cam: adg: cre: cl: adid:29724ee9795a3c87920b05ba5a763155 ct: ca:NaN cc: fir:"
    }
""".trimIndent()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        ApiClient.getConfig(requestBody) { result: Result<LookConfig> ->
            when (result) {
                is Result.Success -> {
                    val data = result.data
                    PreferencesUtil.saveConfig("lookConfig",result.data)
                    println(data.data.toString())
                }

                is Result.Error -> {
                    println("Error: ${result.exception.message}")
                }
            }
        }

    }

    private fun getInfo() {
        val requestBody = """
    {
        "gaid": "34531535ffa-532523j-5235n2k35-5325n",
        "attributes": "tt:1176xgf3 tn:test net:test cam: adg: cre: cl: adid:29724ee9795a3c87920b05ba5a763155 ct: ca:NaN cc: fir:"
    }
""".trimIndent().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        ApiClient.getInfo(requestBody) { result: Result<LookInfo> ->
            when (result) {
                is Result.Success -> {
                    val data1 = result.data
                    PreferencesUtil.saveInfo("lookInfo",result.data)
                    println(data1.data[data1.data.size-1].toString())
                }

                is Result.Error -> {
                    println("Error: ${result.exception.message}")
                }

            }
        }
    }


}

