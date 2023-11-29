package com.example.guessthenum

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            Log.d("fr", "test")
            val minRange = viewBinding.editTextNumber.text.toString().toDouble()
            val maxRange = viewBinding.editTextNumber2.text.toString().toDouble()
            val newFragment = NumberGuesser.newInstance(minRange, maxRange)
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
            Log.d("fr", "out")
        }
        return viewBinding.root
    }
}