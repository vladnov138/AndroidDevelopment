package com.example.guessthenum

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    private var minRange = 0
    private var maxRange = 0
    private var guessedNumber = 0
    private lateinit var viewBinding: FragmentNumberGuesserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Чтобы не включительно
        minRange = requireArguments().getInt(ARG_PARAM1) + 1
        maxRange = requireArguments().getInt(ARG_PARAM2)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNumberGuesserBinding.inflate(inflater, container, false)
        guessedNumber = countGuessedNumber()
        showGuessedNumber()
        viewBinding.btnCorrect.setOnClickListener {
            Toast.makeText(requireActivity(), "Yeah! Computer win!", Toast.LENGTH_SHORT).show()
            commitRangeSelector(it)
        }
        viewBinding.btnLess.setOnClickListener {
            if (maxRange == guessedNumber) {
                Toast.makeText(requireActivity(), "Вы что-то путаете", Toast.LENGTH_SHORT).show()
            }
            maxRange = guessedNumber
            guessedNumber = countGuessedNumber()
            showGuessedNumber()
        }
        viewBinding.btnMore.setOnClickListener {
            if (minRange == guessedNumber) {
                Toast.makeText(requireActivity(), "Вы что-то путаете", Toast.LENGTH_LONG).show()
            }
            minRange = guessedNumber
            guessedNumber = countGuessedNumber()
            showGuessedNumber()
        }
        return viewBinding.root
    }

    private fun commitRangeSelector(v: View) {
        val newFragment = RangeSelector()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun countGuessedNumber() = (maxRange - minRange) / 2 + minRange

    private fun showGuessedNumber() {
        viewBinding.textView.text = "Это число $guessedNumber?"
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
        fun newInstance(minRange: Int, maxRange: Int) =
            NumberGuesser().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, minRange)
                    putInt(ARG_PARAM2, maxRange)
                }
            }
    }
}