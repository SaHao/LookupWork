package com.lllloookkk.upupp.ac

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.gson.Gson
import com.lllloookkk.upupp.R
import com.lllloookkk.upupp.bean.EventData
import com.lllloookkk.upupp.bean.LookConfig
import com.lllloookkk.upupp.bean.LookInfo
import com.lllloookkk.upupp.network.ApiCallback
import com.lllloookkk.upupp.network.ApiClient
import com.lllloookkk.upupp.util.CommonUtil
import com.lllloookkk.upupp.util.PreferencesUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class LookBeginAc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_look_begin)
        if (!PreferencesUtil.getString("attributes", "").isNullOrEmpty()) {
            getConfig()
        } else {
            initData()
        }
    }

    private fun getConfig() {
        CoroutineScope(Dispatchers.IO).launch {
            val params = mapOf(
                "gaid" to PreferencesUtil.getString("gaid"),
                "attributes" to PreferencesUtil.getString("attributes"),
                "network" to CommonUtil.isVpnConnected(applicationContext)
            )
            val requestBody = Gson().toJson(params).toRequestBody("application/json".toMediaTypeOrNull())
            ApiClient.getConfig(requestBody, object : ApiCallback<LookConfig> {
                override fun onSuccess(result: LookConfig) {
                    if (result.code == 0) {
                        PreferencesUtil.saveConfig("lookConfig", result)
                        getInfo()
                    } else {
                        getConfig()
                    }
                }
                override fun onError(e: Exception) {
                    getConfig()
                }
            })
        }
    }
    private fun getInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            val params = mapOf(
                "gaid" to PreferencesUtil.getString("gaid"),
                "attributes" to PreferencesUtil.getString("attributes"),
                "network" to CommonUtil.isVpnConnected(applicationContext)
            )
            val requestBody = Gson().toJson(params).toRequestBody("application/json".toMediaTypeOrNull())
            ApiClient.getInfo(requestBody, object : ApiCallback<LookInfo> {
                override fun onSuccess(result: LookInfo) {
                    if (result.code == 0) {
                        show()
                        PreferencesUtil.saveInfo("lookInfo", result)
                        startActivity(Intent(this@LookBeginAc,MainActivity::class.java))
                    } else {
                        getInfo()
                    }
                }
                override fun onError(e: Exception) {
                    getInfo()
                }
            })
        }
    }
    private fun show() {
        val adjustEvent =
            AdjustEvent(PreferencesUtil.getConfig().data.report.adjust.app_show_app.code)
        Adjust.trackEvent(adjustEvent)
        CoroutineScope(Dispatchers.IO).launch {
            val params = mapOf(
                "gaid" to PreferencesUtil.getString("gaid"),
                "attributes" to PreferencesUtil.getString("attributes"),
                "network" to CommonUtil.isVpnConnected(applicationContext),
                "action" to "app_show_app"
            )
            val requestBody =
                Gson().toJson(params).toRequestBody("application/json".toMediaTypeOrNull())
            ApiClient.postEvent(requestBody, object : ApiCallback<EventData> {
                override fun onSuccess(result: EventData) {
                    if (result.code == 0) {
                    } else {
                    }
                }

                override fun onError(e: Exception) {
                }
            })
        }
    }
    private suspend fun pollForGAID(): Any? {
        val result = withTimeoutOrNull(30000L) {
            while (true) {
                val gaid = getGaid()
                if (gaid != null) {
                    PreferencesUtil.putString("gaid", gaid)
                    return@withTimeoutOrNull gaid
                }
                delay(1000)
            }
        }
        return result
    }

    private fun initData() {
        CoroutineScope(Dispatchers.Main).launch {
            val gaid = pollForGAID()
            if (gaid != null) {
                val attribution =getAttribution()
                if (attribution != null) {
                    getConfig()
                }
            } else {
                PreferencesUtil.putString("gaid", "00000000-0000-0000-0000-000000000100")
                val attribution = withContext(Dispatchers.Default) {
                    getAttribution()
                }
                if (attribution != null) {
                    getConfig()
                }
            }

        }
    }

    private suspend fun getGaid(): String? = withContext(Dispatchers.IO) {
        try {
            val info = AdvertisingIdClient.getAdvertisingIdInfo(applicationContext)
            Log.e("gaid", info.id.toString())
            return@withContext info.id.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        }
        null
    }

    private suspend fun getAttribution(): String? = withContext(Dispatchers.IO) {
        val result = withTimeoutOrNull(6000L) {
            repeat(10) { index ->
                Adjust.getAttribution { attribution ->
                    if (attribution != null) {
                        Log.e("getAttribution", "has+${attribution.network}")
                        if (!attribution.network.equals(
                                "Organic",
                                ignoreCase = true
                            ) || index == 9
                        ) {
                            val string: String = attribution.toString()
                            PreferencesUtil.putString("attributes", string)
                            return@getAttribution
                        }
                    } else {
                        Log.e("getAttribution", "none")
                        PreferencesUtil.putString("attributes", "")
                        return@getAttribution
                    }
                }
                delay(500L)
            }
            "Done"
        }
        return@withContext result
    }
}

