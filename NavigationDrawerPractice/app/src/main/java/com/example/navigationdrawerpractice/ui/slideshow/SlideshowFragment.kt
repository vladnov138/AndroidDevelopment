package com.example.navigationdrawerpractice.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.navigationdrawerpractice.databinding.FragmentSlideshowBinding
import com.example.navigationdrawerpractice.ui.slide.SlideFragment
import com.google.android.material.tabs.TabLayoutMediator

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel = ViewModelProvider(this)[SlideshowViewModel::class.java]

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val slideAdapter = SlideAdapter(this)
        binding.pager.adapter = SlideAdapter(this)

        slideshowViewModel.tabs.observe(viewLifecycleOwner) {
            slideAdapter.items = it
        }

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = slideshowViewModel.tabs.value?.get(position)?.title
        }.attach()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class SlideAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        var items: List<Tab> = emptyList()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun createFragment(position: Int): Fragment {
            return SlideFragment.newInstance(items[position].contentId)
        }

    }
}