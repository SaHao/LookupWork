package com.lllloookkk.upupp.ac

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lllloookkk.upupp.adapter.LookAdapter
import com.lllloookkk.upupp.databinding.ActivityMainBinding
import com.lllloookkk.upupp.util.ItemDecoration
import com.lllloookkk.upupp.util.PreferencesUtil

class MainActivity : AppCompatActivity(),LookAdapter.OnItemClickListener{
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter:LookAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter= LookAdapter()
        binding.mainRv.layoutManager=LinearLayoutManager(this)
        binding.mainRv.addItemDecoration(ItemDecoration(40))
        binding.mainRv.adapter=adapter
        adapter.setListener(this)
        adapter.updateData(PreferencesUtil.getInfo().data)

    }

    override fun showDialogA(position: Int) {
        val intent = Intent(this@MainActivity, JobActivity::class.java)
        intent.putExtra("pos", position)
        startActivity(intent)
    }

    override fun showJobAc(position: Int) {
        val intent = Intent(this@MainActivity, JobActivity::class.java)
        intent.putExtra("pos", position)
        startActivity(intent)
    }

}