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
import com.lllloookkk.upupp.databinding.DialogOneBinding
import com.lllloookkk.upupp.util.PreferencesUtil


class DialogOne : DialogFragment() {
    interface DialogOneListener {
        fun onInvoke()
    }
    var binding: DialogOneBinding? = null
    private var listener: DialogOneListener? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogOneBinding.inflate(inflater, container, false)
        binding!!.txtOne.text = PreferencesUtil.getConfig().data.contact.contents[0]
        binding!!.txtTwo.text = PreferencesUtil.getConfig().data.contact.contents[1]
        binding!!.txtThree.text = PreferencesUtil.getConfig().data.contact.contents[2]
        binding!!.confirmButton.text = PreferencesUtil.getConfig().data.chatScript.actions.contact
        binding!!.confirmButton.setOnClickListener {
            listener?.onInvoke()
            dismiss()
        }
        binding!!.close.setOnClickListener {
            dismiss()
        }
        return binding!!.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(dpToPx(380f), dpToPx(400f)) // 宽度和高度以像素为单位
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DialogOneListener) {
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