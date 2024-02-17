package com.example.navigationdrawerpractice.ui.slide

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.example.navigationdrawerpractice.R
import com.example.navigationdrawerpractice.databinding.FragmentSlideBinding

class SlideFragment : Fragment() {

    private lateinit var binding: FragmentSlideBinding

    companion object {
        private const val ARG_ID = "id"

        fun newInstance(id: Int) = SlideFragment().apply {
            arguments = bundleOf(
                ARG_ID to id
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setId(arguments?.getInt(ARG_ID) ?: 0)

        viewModel.description.observe(viewLifecycleOwner) {
            binding.slideDescription.text = it
        }
    }

    private val viewModel: SlideViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSlideBinding.inflate(inflater, container, false)
        return binding.root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(SlideViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}