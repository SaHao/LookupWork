package com.lllloookkk.upupp.ac

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lllloookkk.upupp.adapter.JobAdapter
import com.lllloookkk.upupp.bean.InfoData
import com.lllloookkk.upupp.databinding.ActivityJobBinding
import com.lllloookkk.upupp.dialog.DialogFour
import com.lllloookkk.upupp.dialog.DialogOne
import com.lllloookkk.upupp.dialog.DialogTwo
import com.lllloookkk.upupp.util.ItemDecoration
import com.lllloookkk.upupp.util.PreferencesUtil

class JobActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJobBinding
    private lateinit var adapter: JobAdapter
    private lateinit var data: InfoData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //        enableEdgeToEdge()
        val value = intent.getIntExtra("pos",0)
        data=PreferencesUtil.getInfo().data[value]
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
                val dialog = DialogOne()
                dialog.show(supportFragmentManager, "DialogOne")
            }else{
                val dialog = DialogTwo()
                dialog.show(supportFragmentManager, "DialogOne")
            }
        }
        binding.back.setOnClickListener {finish()}

    }
}