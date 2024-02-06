package com.example.fragments_practice

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragments_practice.databinding.FragmentTestBinding

class TestFragment : Fragment() {

    private lateinit var viewBinding: FragmentTestBinding

    companion object {
        private val TAG = TestFragment::class.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentTestBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = viewBinding.button
        button.setOnClickListener {
            MyDialogFragment().apply {
                onPositiveClickListener =
                    DialogInterface.OnClickListener { dialog, which ->
                        viewBinding.textView.text = "Angarsk has been bombed"
                    }
                onNegativeClickListener =
                    DialogInterface.OnClickListener { dialog, which ->
                        viewBinding.textView.text = "Angarsk has not been bombed"
                    }
            }.show(requireActivity().supportFragmentManager, "MyDialogFragment")
//            AlertDialog.Builder(this).show()
        }
        val buttonNext = viewBinding.button2
        buttonNext.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainerView, Test2Fragment())
                .commitAllowingStateLoss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "OnCreate")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "OnResume")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "OnStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "OnPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "OnDestroy")
    }
}