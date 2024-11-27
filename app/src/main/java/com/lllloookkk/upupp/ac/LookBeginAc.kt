package com.lllloookkk.upupp.ac

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lllloookkk.upupp.R
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LookBeginAc : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_look_begin)
        GlobalScope.launch {
            delay(2000L)
            val intent = Intent(this@LookBeginAc, MainActivity::class.java)
            startActivity(intent)
        }

}
}