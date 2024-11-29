package com.lllloookkk.upupp.util

import android.app.Application
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.OnAttributionChangedListener

class MainAPP : Application() {

    override fun onCreate() {
        super.onCreate()
        PreferencesUtil.init(this)
        initAj()
    }
    private fun initAj(){
        val environment = AdjustConfig.ENVIRONMENT_PRODUCTION
        val config = AdjustConfig(this, "tnvruyff3bwg", environment)
        config.enableSendingInBackground()
        config.onAttributionChangedListener =
            OnAttributionChangedListener { attribution -> val string = attribution.toString() }
        Adjust.initSdk(config)
    }
}