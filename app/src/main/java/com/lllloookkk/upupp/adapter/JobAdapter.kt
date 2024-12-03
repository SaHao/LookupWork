package com.lllloookkk.upupp.adapter

import android.content.Context
import android.os.Build
import android.text.method.LinkMovementMethod
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.lllloookkk.upupp.R
import com.lllloookkk.upupp.bean.Description
import com.lllloookkk.upupp.databinding.JobItemBinding

class JobAdapter(private var contexts: Context) : RecyclerView.Adapter<JobAdapter.ViewHolder>() {


    private val items = ArrayList<Description>()
    private lateinit var  title:String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = JobItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(private val binding: JobItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: Description) {
            binding.title.text = item.title
            generateTextViews(binding.contentsLayout, item.contents)
        }
    }

    fun updateData(newItems: List<Description>,title:String) {
        this.title=title
        this.items.clear()
        this.items.addAll(newItems)
        notifyDataSetChanged()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun generateTextViews(layout: LinearLayout, strings: List<String>) {
        for (str in strings) {
            val textView = TextView(contexts).apply {
                text = str
                textSize = 16f // 设置文字大小
                setTextColor(contexts.resources.getColor(R.color.black)) // 设置文字颜色
                textAlignment = View.TEXT_ALIGNMENT_VIEW_START
                setAutoSizeTextTypeUniformWithConfiguration(
                    12,
                    18,
                    1,
                    TypedValue.COMPLEX_UNIT_SP
                ) // 自动换行
                movementMethod = LinkMovementMethod.getInstance() // 支持点击链接
                setTextIsSelectable(true) // 支持文本选择
            }

            // 设置LinearLayout的参数
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // 宽度
                LinearLayout.LayoutParams.WRAP_CONTENT // 高度
            ).apply {
                // 设置边距
                setMargins(0, 0, 0, 10)
            }
            textView.layoutParams = params

            layout.addView(textView)
        }
    }
}
