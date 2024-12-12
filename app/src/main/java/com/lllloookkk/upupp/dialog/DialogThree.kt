package com.lllloookkk.upupp.dialog

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.lllloookkk.upupp.databinding.DialogThreeBinding
import com.lllloookkk.upupp.dialog.DialogOne.DialogOneListener
import com.lllloookkk.upupp.util.PreferencesUtil

class DialogThree : DialogFragment() {
    interface DialogThreeListener {
        fun onDownload()
    }
    var binding: DialogThreeBinding? = null
    private var listener: DialogThreeListener? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogThreeBinding.inflate(inflater, container, false)
        binding!!.txtOne.text = PreferencesUtil.getConfig().data.download.contents[0]
        binding!!.txtTwo.text = PreferencesUtil.getConfig().data.download.contents[1]
        binding!!.confirmButton.text= PreferencesUtil.getConfig().data.download.confirm.install
        binding!!.confirmButton.setOnClickListener {
            dismiss()
            listener?.onDownload()
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
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DialogThreeListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement CloseDialogListener")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(onCreateView(layoutInflater, null, savedInstanceState))
            .create()
    }

    private fun dpToPx(dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            requireContext().resources.displayMetrics
        ).toInt()
    }
}