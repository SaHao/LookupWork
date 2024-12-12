package com.lllloookkk.upupp.dialog

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.text.style.TtsSpan.ARG_TEXT
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.lllloookkk.upupp.databinding.DialogFourBinding
import com.lllloookkk.upupp.util.PreferencesUtil
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DialogFour : DialogFragment() {
    var binding: DialogFourBinding? = null

    companion object {
        private const val ARG_NAME = "name"

        fun newInstance(name: String): DialogFour {
            val args = Bundle()
            args.putString(ARG_NAME, name)
            val fragment = DialogFour()
            fragment.arguments = args
            return fragment
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFourBinding.inflate(inflater, container, false)
        binding!!.name.text = arguments?.getString(ARG_NAME)
        binding!!.gaid.text = PreferencesUtil.getString("gaid")
        binding!!.time.text = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))
        binding!!.dialogButton.setOnClickListener {
            activity?.finish()
        }
        return binding!!.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(dpToPx(380f), dpToPx(540f)) // 宽度和高度以像素为单位
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