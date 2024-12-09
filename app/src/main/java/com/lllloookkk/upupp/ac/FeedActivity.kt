package com.lllloookkk.upupp.  ac

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lllloookkk.upupp.databinding.ActivityFeedBinding
import com.lllloookkk.upupp.dialog.DialogFour

class FeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.confirmButton.setOnClickListener {
            val dialog = DialogFour.newInstance((binding.editName.text?:"sajklfew").toString())
            dialog.show(supportFragmentManager, "DialogFour")
        }

    }
}