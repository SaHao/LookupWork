package com.lllloookkk.upupp.ac

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lllloookkk.upupp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.txt1.text="测试机1"
        binding.txt2.text="测试机2"
        binding.txt3.text="测试机3"
        binding.but1.setOnClickListener {
            binding.txt1.text="改变1"
            binding.txt3.text="改变3"
            binding.txt2.text="改变2"
        }

    }
}