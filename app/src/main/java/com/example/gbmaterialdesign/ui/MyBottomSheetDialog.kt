package com.example.gbmaterialdesign.ui

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.gbmaterialdesign.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: BottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = BottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            requireActivity()?.let {
                binding.bottomSheetDescription.typeface = Typeface.createFromAsset(it.assets, "alladin/KryptapersonaluseExtrabold-L3oVD.otf")
            }


        arguments?.getString(BUNDLE_EXTRA).let {
            if (it != null) {


                binding.bottomSheetDescription.text = it
            }
        }

        binding.dialogButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("SheetDialog")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "MyBottomSheetDialog"
        const val BUNDLE_EXTRA = "description"
        fun newInstance(bundle: Bundle): MyBottomSheetDialog {
            val fragment = MyBottomSheetDialog()
            fragment.arguments = bundle
            return fragment
        }
    }
}