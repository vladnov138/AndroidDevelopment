package com.example.fragments_practice

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar

class MyDialogFragment : DialogFragment() {
    var onPositiveClickListener: OnClickListener? = null
    var onNegativeClickListener: OnClickListener? = null

    val langs: Array<String> by lazy {
        requireActivity().resources.getStringArray(R.array.langs)
    }

    private var choice: Int = 0


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
            Toast.makeText(requireContext(), "hour: $hourOfDay, minute: $minute", Toast.LENGTH_LONG).show()
        }, hour, minute,true)
    //        return AlertDialog.Builder(requireContext()).setTitle("Внимание!")
//            .setSingleChoiceItems(langs, 0) { _, which ->
//                choice = which
//            }
//            .setPositiveButton("Бомбить Ангарск") { dialogInterface, which ->
//                Toast.makeText(requireContext(), "Nice choice: $choice", Toast.LENGTH_LONG)
//                    .show()
//                onPositiveClickListener?.onClick(dialog, which)
//            }
//            .setNegativeButton("Отмена") { dialogInterface, which ->
//                onNegativeClickListener?.onClick(dialog, which)
//            }
//            .create()
//            .apply {
//                setCancelable(false)
//                setCanceledOnTouchOutside(false)
//            }
    }
}