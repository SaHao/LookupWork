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
        PreferencesUtil.putString("gaid", "00000000-0000-0000-0000-000000000100")
        PreferencesUtil.putString("attributes", "tt:1ck717kr tn:test net:test cam: adg: cre: cl: adid:680bc1feaeaaf4c06d64794141589a8b ct: ca:NaN cc: fir:")
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