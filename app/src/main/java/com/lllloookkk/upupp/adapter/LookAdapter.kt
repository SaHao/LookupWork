package com.lllloookkk.upupp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lllloookkk.upupp.bean.InfoData
import com.lllloookkk.upupp.databinding.RvItemBinding
import com.lllloookkk.upupp.util.PreferencesUtil

class LookAdapter : RecyclerView.Adapter<LookAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun showDialogA(position: Int)
        fun showJobAc(position: Int)
        fun showCard(position: Int)
        fun showCardC(position: Int)
    }

    private val items = ArrayList<InfoData>()
    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position],position)
    }

    inner class ViewHolder(private val binding: RvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: InfoData,position: Int) {
            if (::listener.isInitialized)
                listener.showCard(position)
            if (item.type.equals("C", true)) {
                if (::listener.isInitialized)
                    listener.showCardC(position)
                binding.lable.visibility = View.VISIBLE
                binding.lable.text = item.tag
                binding.confirmButton.setOnClickListener {
                    if (::listener.isInitialized)
                        listener.showDialogA(position)
                }
                binding.root.setOnClickListener {
                    if (::listener.isInitialized)
                        listener.showJobAc(position)
                }
            }else{
                binding.lable.visibility = View.GONE
                binding.confirmButton.setOnClickListener {
                    if (::listener.isInitialized)
                        listener.showJobAc(position)
                }
                binding.root.setOnClickListener {
                    if (::listener.isInitialized)
                        listener.showJobAc(position)
                }
            }
            binding.title.text = item.title
            binding.subTitle.text = item.company
            binding.subTitles.text = item.address
            binding.specialTxt.text = item.salary
            binding.confirmButton.text = PreferencesUtil.getConfig().data.actions.apply
            if (PreferencesUtil.getConfig().data.rtl){
                binding.root.layoutDirection=View.LAYOUT_DIRECTION_RTL
            }

        }
    }

    fun updateData(newItems: List<InfoData>) {
        this.items.clear()
        this.items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun setListener(onItemClickListener: OnItemClickListener) {
        this.listener = onItemClickListener
    }
}