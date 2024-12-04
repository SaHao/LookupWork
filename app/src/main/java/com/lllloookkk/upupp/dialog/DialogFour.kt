package com.lllloookkk.upupp.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.lllloookkk.upupp.databinding.DialogFourBinding

class DialogFour : DialogFragment() {
    private lateinit var _binding: DialogFourBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= DialogFourBinding.inflate(inflater,container,false)
        _binding.dialogTitle.text = "Dialog Title"
        _binding.dialogMessage.text = "This is a custom dialog using View Binding."

        _binding.dialogButton.setOnClickListener {
            dismiss()
        }
        return _binding.root
    }
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(dpToPx(400f), dpToPx(400f)) // 宽度和高度以像素为单位
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(onCreateView(layoutInflater, null, savedInstanceState))
            .create()
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
    fun dpToPx(dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            requireContext().resources.displayMetrics
        ).toInt()
    }
}