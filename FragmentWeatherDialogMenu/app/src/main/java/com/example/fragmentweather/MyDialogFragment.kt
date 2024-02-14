package com.example.fragmentweather

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class MyDialogFragment : DialogFragment() {
    var onPositiveClickListener: DialogInterface.OnClickListener? = null
    var onNegativeClickListener: DialogInterface.OnClickListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).setTitle("Выбор отображения!")
            .setPositiveButton("Краткий") { dialogInterface, which ->
                onPositiveClickListener?.onClick(dialog, which)
            }
            .setNegativeButton("Подробный") { dialogInterface, which ->
                onNegativeClickListener?.onClick(dialog, which)
            }
            .create()
            .apply {
                setCancelable(false)
                setCanceledOnTouchOutside(false)
            }
    }
}