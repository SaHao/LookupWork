package com.lllloookkk.upupp.ac

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustEvent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lllloookkk.upupp.adapter.JobAdapter
import com.lllloookkk.upupp.bean.EventData
import com.lllloookkk.upupp.bean.InfoData
import com.lllloookkk.upupp.databinding.ActivityJobBinding
import com.lllloookkk.upupp.dialog.DialogFour
import com.lllloookkk.upupp.dialog.DialogOne
import com.lllloookkk.upupp.dialog.DialogThree
import com.lllloookkk.upupp.dialog.DialogThree.DialogThreeListener
import com.lllloookkk.upupp.dialog.DialogTwo
import com.lllloookkk.upupp.network.ApiCallback
import com.lllloookkk.upupp.network.ApiClient
import com.lllloookkk.upupp.util.CommonUtil
import com.lllloookkk.upupp.util.ItemDecoration
import com.lllloookkk.upupp.util.PreferencesUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class JobActivity : AppCompatActivity(), DialogOne.DialogOneListener,DialogThreeListener {
    private lateinit var binding: ActivityJobBinding
    private lateinit var adapter: JobAdapter
    private lateinit var data: InfoData
    private var position: Int = 0
    private lateinit var dialogA: DialogOne
    private lateinit var dialogB: DialogThree
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //        enableEdgeToEdge()
        position= intent.getIntExtra("pos",0)
        data=PreferencesUtil.getInfo().data[position]
        adapter= JobAdapter(this)
        binding.jobRv.layoutManager= LinearLayoutManager(this)
        binding.jobRv.addItemDecoration(
            ItemDecoration(
                20
            )
        )
        binding.jobRv.adapter=adapter
        adapter.updateData(data.descriptions)
        binding.title.text=data.title
        binding.confirmButton.setOnClickListener {
            if(data.type.equals("C",true)){
                dialogA = DialogOne()
                dialogA.show(supportFragmentManager, "DialogOne")
                showPop()
            }else{
                val dialog = DialogTwo()
                dialog.show(supportFragmentManager, "DialogOne")
            }
        }
        binding.back.setOnClickListener {finish()}
        if (PreferencesUtil.getConfig().data.rtl){
            binding.root.layoutDirection= View.LAYOUT_DIRECTION_RTL
        }

    }
    override fun onDownload() {
        popTwoButton()
        downloadApp(PreferencesUtil.getInfo().data[position].contacts[0].store.url)
    }
    override fun onInvoke() {
        if (PreferencesUtil.getInfo().data[position].contacts[0].type.contains("ws")) {
            invokeApp(
                PreferencesUtil.getInfo().data[position].contacts[0].url + "?text" + PreferencesUtil.getInfo().data[position].contacts[0].text,
                PreferencesUtil.getInfo().data[position].contacts[0].type
            )
        } else {
            invokeApp(
                PreferencesUtil.getInfo().data[position].contacts[0].url,
                PreferencesUtil.getInfo().data[position].contacts[0].type
            )
        }
    }

    fun isAppInstalled(packageName: String?): Boolean {
        val packageManager: PackageManager = this@JobActivity.packageManager
        try {
            packageManager.getPackageInfo(packageName!!, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
            return false
        }
    }

    fun invokeApp(url: String, type: String) {
        val gson = Gson()
        var idList: MutableList<String> = ArrayList()
        val idJson = PreferencesUtil.getString("idList", "")
        idList = gson.fromJson(idJson, object : TypeToken<List<String>>() {
        }.type)
        if (PreferencesUtil.getString("idList").isNullOrEmpty()) {
            postInvokeBeginSingle()
            PreferencesUtil.putString("Single", "over")
        }
        postInvokeBegin()
        try {
            if (url.isEmpty()) {
                return
            }
            if (type.contains("ws")) {
                if (isAppInstalled("com.whatsapp")) {
                    val intentws = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    intentws.setPackage("com.whatsapp")
                    intentws.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    this@JobActivity.startActivity(intentws)
                    postInvokeAll()
                    postInvokeWs()
                    if (!idList.contains(PreferencesUtil.getInfo().data[position].contacts[0].id.toString())) {
                        idList.add(PreferencesUtil.getInfo().data[position].contacts[0].id.toString())
                        PreferencesUtil.putList("idList", idList)
                        invokeSuccess(PreferencesUtil.getInfo().data[position].contacts[0].id)
                    }
                } else {
                    dialogA.dismiss()
                    dialogB = DialogThree()
                    dialogB.show(supportFragmentManager, "DialogThree")
                    popTwo()
                }
            } else if (type.contains("tg")) {
                if (isAppInstalled("org.telegram.messenger")) {
                    val intentws = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    intentws.setPackage("org.telegram.messenger")
                    intentws.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    this@JobActivity.startActivity(intentws)
                    postInvokeTg()
                    postInvokeAll()
                    if (!idList.contains(PreferencesUtil.getInfo().data[position].contacts[0].id.toString())) {
                        idList.add(PreferencesUtil.getInfo().data[position].contacts[0].id.toString())
                        PreferencesUtil.putList("idList", idList)
                        invokeSuccess(PreferencesUtil.getInfo().data[position].contacts[0].id)
                    }
                } else if (isAppInstalled("org.telegram.messenger.web")) {
                    val intentws = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    intentws.setPackage("org.telegram.messenger.web")
                    intentws.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    this@JobActivity.startActivity(intentws)
                    postInvokeTg()
                    postInvokeAll()
                    if (!idList.contains(PreferencesUtil.getInfo().data[position].contacts[0].id.toString())) {
                        idList.add(PreferencesUtil.getInfo().data[position].contacts[0].id.toString())
                        PreferencesUtil.putList("idList", idList)
                        invokeSuccess(PreferencesUtil.getInfo().data[position].contacts[0].id)
                    }
                } else {
                    dialogA.dismiss()
                    dialogB = DialogThree()
                    dialogB.show(supportFragmentManager, "DialogThree")
                    popTwo()
                }
            } else {
                if (!idList.contains(PreferencesUtil.getInfo().data[position].contacts[0].id.toString())) {
                    idList.add(PreferencesUtil.getInfo().data[position].contacts[0].id.toString())
                    PreferencesUtil.putList("idList", idList)
                    invokeSuccess(PreferencesUtil.getInfo().data[position].contacts[0].id)
                }
                postInvokeAll()
                val intentws = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                this@JobActivity.startActivity(intentws)
            }
        } catch (ed: Exception) {
            ed.printStackTrace()
        }
    }

    fun downloadApp(url: String?) {
        try {
            if (url == null || TextUtils.isEmpty(url)) {
                return
            }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            this@JobActivity.startActivity(intent)
        } catch (ed: Exception) {
            ed.printStackTrace()
        }
    }

    private fun postInvokeBeginSingle() {
        val adjustEvent =
            AdjustEvent(PreferencesUtil.getConfig().data.report.adjust.addtocartlt.code)
        Adjust.trackEvent(adjustEvent)
        CoroutineScope(Dispatchers.IO).launch {
            val params = mapOf(
                "gaid" to PreferencesUtil.getString("gaid"),
                "attributes" to PreferencesUtil.getString("attributes"),
                "network" to CommonUtil.isVpnConnected(applicationContext),
                "action" to "addtocartlt"
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

    private fun postInvokeBegin() {
        val adjustEvent =
            AdjustEvent(PreferencesUtil.getConfig().data.report.adjust.addtocartpv.code)
        Adjust.trackEvent(adjustEvent)
        CoroutineScope(Dispatchers.IO).launch {
            val params = mapOf(
                "gaid" to PreferencesUtil.getString("gaid"),
                "attributes" to PreferencesUtil.getString("attributes"),
                "network" to CommonUtil.isVpnConnected(applicationContext),
                "action" to "addtocartpv"
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

    private fun postInvokeAll() {
        val adjustEvent =
            AdjustEvent(PreferencesUtil.getConfig().data.report.adjust.addtocart_ok.code)
        Adjust.trackEvent(adjustEvent)
        CoroutineScope(Dispatchers.IO).launch {
            val params = mapOf(
                "gaid" to PreferencesUtil.getString("gaid"),
                "attributes" to PreferencesUtil.getString("attributes"),
                "network" to CommonUtil.isVpnConnected(applicationContext),
                "action" to "addtocart_ok"
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

    private fun postInvokeWs() {
        val adjustEvent = AdjustEvent(PreferencesUtil.getConfig().data.report.adjust.addtocart_ws.code)
        Adjust.trackEvent(adjustEvent)
        CoroutineScope(Dispatchers.IO).launch {
            val params = mapOf(
                "gaid" to PreferencesUtil.getString("gaid"),
                "attributes" to PreferencesUtil.getString("attributes"),
                "network" to CommonUtil.isVpnConnected(applicationContext),
                "action" to "addtocart_ws"
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

    private fun postInvokeTg() {
        val adjustEvent = AdjustEvent(PreferencesUtil.getConfig().data.report.adjust.addtocart_tg.code)
        Adjust.trackEvent(adjustEvent)
        CoroutineScope(Dispatchers.IO).launch {
            val params = mapOf(
                "gaid" to PreferencesUtil.getString("gaid"),
                "attributes" to PreferencesUtil.getString("attributes"),
                "network" to CommonUtil.isVpnConnected(applicationContext),
                "action" to "addtocart_tg"
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

    private fun popTwo() {
        val adjustEvent =
            AdjustEvent(PreferencesUtil.getConfig().data.report.adjust.download_tip_show.code)
        Adjust.trackEvent(adjustEvent)
        CoroutineScope(Dispatchers.IO).launch {
            val params = mapOf(
                "gaid" to PreferencesUtil.getString("gaid"),
                "attributes" to PreferencesUtil.getString("attributes"),
                "network" to CommonUtil.isVpnConnected(applicationContext),
                "action" to "download_tip_show"
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

    private fun popTwoButton() {
        val adjustEvent =
            AdjustEvent(PreferencesUtil.getConfig().data.report.adjust.click_download_btn.code)
        Adjust.trackEvent(adjustEvent)
        CoroutineScope(Dispatchers.IO).launch {
            val params = mapOf(
                "gaid" to PreferencesUtil.getString("gaid"),
                "attributes" to PreferencesUtil.getString("attributes"),
                "network" to CommonUtil.isVpnConnected(applicationContext),
                "action" to "click_download-btn"
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
    private fun showPop() {
        val adjustEvent =
            AdjustEvent(PreferencesUtil.getConfig().data.report.adjust.contact_show_popup.code)
        Adjust.trackEvent(adjustEvent)
        CoroutineScope(Dispatchers.IO).launch {
            val params = mapOf(
                "gaid" to PreferencesUtil.getString("gaid"),
                "attributes" to PreferencesUtil.getString("attributes"),
                "network" to CommonUtil.isVpnConnected(applicationContext),
                "action" to "contact_show_popup"
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

    private fun invokeSuccess(id: Long) {
        val adjustEvent =
            AdjustEvent(PreferencesUtil.getConfig().data.report.adjust.addtocartpv.code)
        Adjust.trackEvent(adjustEvent)
        CoroutineScope(Dispatchers.IO).launch {
            val params = mapOf(
                "gaid" to PreferencesUtil.getString("gaid"),
                "attributes" to PreferencesUtil.getString("attributes"),
                "network" to CommonUtil.isVpnConnected(applicationContext),
                "id" to id,
                "action" to "contact_lva"
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


}