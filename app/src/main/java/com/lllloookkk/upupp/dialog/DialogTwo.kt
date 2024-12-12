package com.lllloookkk.upupp.dialog

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.DialogFragment
import com.lllloookkk.upupp.databinding.DialogOneBinding
import com.lllloookkk.upupp.databinding.DialogTwoBinding
import com.lllloookkk.upupp.util.PreferencesUtil

class DialogTwo : DialogFragment() {
    var binding: DialogTwoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogTwoBinding.inflate(inflater, container, false)
        binding!!.txtOne.text = PreferencesUtil.getConfig().data.tips.contents[0]
        binding!!.confirmButton.text=PreferencesUtil.getConfig().data.tips.confirm.install
        binding!!.confirmButton.setOnClickListener {
            dismiss()
        }
        binding!!.close.setOnClickListener {
            dismiss()
        }
        if (PreferencesUtil.getConfig().data.rtl){
            binding!!.root.layoutDirection=View.LAYOUT_DIRECTION_RTL
        }
        return binding!!.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(dpToPx(380f), dpToPx(400f)) // 宽度和高度以像素为单位
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(onCreateView(layoutInflater, null, savedInstanceState))
            .create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun dpToPx(dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            requireContext().resources.displayMetrics
        ).toInt()
    }
}