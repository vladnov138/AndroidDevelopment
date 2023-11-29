package com.example.guessthenum

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.guessthenum.databinding.FragmentNumberGuesserBinding
import com.example.guessthenum.databinding.FragmentRangeSelectorBinding

private const val ARG_PARAM1 = "minRange"
private const val ARG_PARAM2 = "maxRange"

/**
 * A simple [Fragment] subclass.
 * Use the [NumberGuesser.newInstance] factory method to
 * create an instance of this fragment.
 */
class NumberGuesser private constructor() : Fragment() {
    private var minRange: Double = 0.0
    private var maxRange: Double = 0.0
    private var guessedNumber: Double = 0.0
    private lateinit var viewBinding: FragmentNumberGuesserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        minRange = requireArguments().getDouble(ARG_PARAM1)
        maxRange = requireArguments().getDouble(ARG_PARAM2)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNumberGuesserBinding.inflate(inflater, container, false)
        guessedNumber = countGuessedNumber()
        showGuessedNumber()
        viewBinding.btnCorrect.setOnClickListener(this::commitRangeSelector)
        viewBinding.btnLess.setOnClickListener {
            maxRange = guessedNumber
            guessedNumber = countGuessedNumber()
            showGuessedNumber()
        }
        viewBinding.btnMore.setOnClickListener {
            minRange = guessedNumber
            guessedNumber = countGuessedNumber()
            showGuessedNumber()
        }
        return viewBinding.root
    }

    private fun commitRangeSelector(v: View) {

    }

    private fun countGuessedNumber() = (maxRange - minRange) / 2 + minRange

    private fun showGuessedNumber() {
        viewBinding.textView.text = guessedNumber.toString()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NumberGuesser.
         */
        @JvmStatic
        fun newInstance(minRange: Double, maxRange: Double) =
            NumberGuesser().apply {
                arguments = Bundle().apply {
                    putDouble(ARG_PARAM1, minRange)
                    putDouble(ARG_PARAM2, maxRange)
                }
            }
    }
}