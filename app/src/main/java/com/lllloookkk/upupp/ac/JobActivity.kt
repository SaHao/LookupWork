package com.lllloookkk.upupp.ac

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lllloookkk.upupp.adapter.JobAdapter
import com.lllloookkk.upupp.databinding.ActivityJobBinding
import com.lllloookkk.upupp.util.ItemDecoration
import com.lllloookkk.upupp.util.PreferencesUtil

class JobActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJobBinding
    private lateinit var adapter: JobAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //        enableEdgeToEdge()
        val value = intent.getIntExtra("pos",0)
        adapter= JobAdapter(this)
        binding.jobRv.layoutManager= LinearLayoutManager(this)
        binding.jobRv.addItemDecoration(
            ItemDecoration(
                20
            )
        )
        binding.jobRv.adapter=adapter
        adapter.updateData(PreferencesUtil.getInfo().data[value].descriptions,PreferencesUtil.getInfo().data[value].title)
        binding.confirmButton.setOnClickListener {

        }
    }
}