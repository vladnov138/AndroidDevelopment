package com.example.guessthenum

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.guessthenum.databinding.FragmentRangeSelectorBinding

/**
 * A simple [Fragment] subclass.
 * Use the [RangeSelector.newInstance] factory method to
 * create an instance of this fragment.
 */
class RangeSelector : Fragment() {
    private lateinit var viewBinding: FragmentRangeSelectorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewBinding = FragmentRangeSelectorBinding.inflate(layoutInflater)
        Log.d("fr", "init")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentRangeSelectorBinding.inflate(inflater, container, false)
        viewBinding.button.setOnClickListener {
            val minRange = viewBinding.editTextNumber.text.toString()
            val maxRange = viewBinding.editTextNumber2.text.toString()
            if (minRange.isEmpty() || maxRange.isEmpty()) {
                Toast.makeText(requireActivity(), "Заполните диапазоны", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val newFragment = NumberGuesser.newInstance(minRange.toInt(), maxRange.toInt())
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, newFragment)
            transaction.addToBackStack(this.toString())
            transaction.commit()
        }
        return viewBinding.root
    }
}