package com.lllloookkk.upupp.ac

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lllloookkk.upupp.R
import com.lllloookkk.upupp.databinding.ActivityFeedBinding
import com.lllloookkk.upupp.dialog.DialogFour
import com.lllloookkk.upupp.util.PreferencesUtil

class FeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedBinding
    private val handler = Handler(Looper.getMainLooper())
    private val countdownDuration = 20000 // 20秒
    private val buttonCount = 6
    private var currentButtonIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.editName.setText(PreferencesUtil.getString("Name",""))
        binding.confirmButton.setOnClickListener {
            var name = binding.editName.text.toString()
            if (name.isNotEmpty()) {
                PreferencesUtil.putString("Name", name)
            }
            if (binding.editFeedback.text.toString().isNotEmpty()) {
                val dialog = DialogFour.newInstance(name)
                dialog.show(supportFragmentManager, "DialogFour")
            }
        }
        binding.back.setOnClickListener {
            finish()
        }
        setupButtons()
        startCountdownForButton(0)
    }

    private fun setupButtons() {
        for (i in 0 until buttonCount) {
            val button = when (i) {
                0 -> binding.finishOne
                1 -> binding.finishTwo
                2 -> binding.finishThree
                3 -> binding.finishFour
                4 -> binding.finishFive
                5 -> binding.finishSix
                else -> null
            }
            val img = when (i) {
                0 -> binding.selectOne
                1 -> binding.selectTwo
                2 -> binding.selectThree
                3 -> binding.selectFour
                4 -> binding.selectFive
                5 -> binding.selectSix
                else -> null
            }
            button?.setOnClickListener {
                if (button.isEnabled) {
                    button.visibility=View.GONE
                    img?.visibility=View.VISIBLE

                    if (button==binding.finishSix){
                        binding.confirmButton.isEnabled=true
                    }
                    val nextIndex = currentButtonIndex + 1
                    if (nextIndex < buttonCount) {
                        startCountdownForButton(nextIndex)
                    }
                }
            }
        }
    }

    private fun startCountdownForButton(index: Int) {
        currentButtonIndex = index
        val button = when (index) {
            0 -> binding.finishOne
            1 -> binding.finishTwo
            2 -> binding.finishThree
            3 -> binding.finishFour
            4 -> binding.finishFive
            5 -> binding.finishSix
            else -> null
        }
        button?.let {
            it.isEnabled = false
            it.text = (countdownDuration / 1000).toString()+"S"
            startCountdown(it)
        }
    }

    private fun startCountdown(button: TextView) {
        var remainingTime = countdownDuration
        val updateText = object : Runnable {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun run() {
                if (remainingTime > 0) {
                    remainingTime -= 1000
                    button.text = (remainingTime / 1000).toString()+"S"
                    handler.postDelayed(this, 1000)
                } else {
                    button.isEnabled = true
                    button.background = getDrawable(R.drawable.feed_shape)
                    button.text="FINISH"
                }
            }
        }
        handler.post(updateText)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}