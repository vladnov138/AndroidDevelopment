package com.example.navigationpractice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.navigationpractice.databinding.FragmentBlankBinding

class BlankFragment : Fragment() {
    private val safeArgs: BlankFragmentArgs by navArgs()
    private val rareName: String? by lazy { safeArgs.rareName }
    private lateinit var viewBinding: FragmentBlankBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentBlankBinding.inflate(inflater, container, false)
        viewBinding.resultTextView.text = rareName
        return viewBinding.root
    }
}