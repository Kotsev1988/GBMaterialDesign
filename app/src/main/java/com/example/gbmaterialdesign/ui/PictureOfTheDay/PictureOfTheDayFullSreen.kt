package com.example.gbmaterialdesign.ui.PictureOfTheDay


import android.os.Bundle
import android.view.*
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.example.gbmaterialdesign.R
import com.example.gbmaterialdesign.databinding.FragmentPictureFullBinding


class PictureOfTheDayFullSreen : Fragment() {

    private var _binding: FragmentPictureFullBinding? = null
    private val binding get() = _binding!!

    var flag = false
    var desc: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentPictureFullBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(BUNDLE_URL).let {
            if (it != null) {
                Glide.with(requireActivity())
                    .load(it)
                    .into(binding.pictureOfDayFull)
            }
        }

        arguments?.getString(BUNDLE_EXTRA).let {
            if (it != null) {
                desc = it.toString()
                binding.description.text = desc
            }
        }

        binding.pictureOfDayFull.setOnClickListener {
            flag = !flag

            if (flag){

                val constraintSet = ConstraintSet()
                constraintSet.clone(requireContext(), R.layout.fragment_picture_full_motion)

                val transition = ChangeBounds()
                transition.interpolator = AnticipateOvershootInterpolator(1.0f)
                transition.duration = 2000
                TransitionManager.beginDelayedTransition(binding.mainContainer, transition)
                constraintSet.applyTo(binding.mainContainer)
            }else {
                val constraintSet = ConstraintSet()
                constraintSet.clone(requireContext(), R.layout.fragment_picture_full)

                val transition = ChangeBounds()
                transition.interpolator = AnticipateOvershootInterpolator(1.0f)
                transition.duration = 2000
                TransitionManager.beginDelayedTransition(binding.mainContainer, transition)
                constraintSet.applyTo(binding.mainContainer)
            }


        }


    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    companion object {
        const val TAG = "MyBottomSheetDialog"
        const val BUNDLE_EXTRA = "description"
        const val BUNDLE_URL = "URL"
        fun newInstance(bundle: Bundle): PictureOfTheDayFullSreen {
            val fragment = PictureOfTheDayFullSreen()
            fragment.arguments = bundle
            return fragment
        }
    }

}