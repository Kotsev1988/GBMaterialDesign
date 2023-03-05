package com.example.gbmaterialdesign.ui.Mars

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import android.text.style.ForegroundColorSpan
import android.text.style.IconMarginSpan
import android.text.style.ImageSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.gbmaterialdesign.R
import com.example.gbmaterialdesign.databinding.FragmentMarsBinding
import com.example.gbmaterialdesign.ui.AppSatates.AppStateMars
import java.util.Arrays

class MarsFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[MarsViewModel::class.java]
    }

    private var _binding: FragmentMarsBinding? = null
    private val binding get() = _binding!!
    private var flag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMarsLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })

        viewModel.getMarsPicture()

        binding.optionOne.apply {
            alpha = 0f
        }

        binding.optionTwo.apply {
            alpha = 0f
        }

        binding.fab.setOnClickListener {

            flag = !flag
            if (flag) {
                ObjectAnimator.ofFloat(binding.plusIcon, View.ROTATION, 0f, 225f).start()
                ObjectAnimator.ofFloat(binding.optionOne, View.TRANSLATION_Y, -130f).start()
                ObjectAnimator.ofFloat(binding.optionTwo, View.TRANSLATION_Y, -250f).start()

                binding.optionOne.animate().alpha(1f).setDuration(2000).setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            binding.optionOne.isClickable = true

                        }
                    }
                )

                binding.optionTwo.animate().alpha(1f).setDuration(2000).setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            binding.optionTwo.isClickable = true

                        }
                    }
                )

            } else {
                ObjectAnimator.ofFloat(binding.plusIcon, View.ROTATION, 225f, 0f).start()
                ObjectAnimator.ofFloat(binding.optionOne, View.TRANSLATION_Y, 0f).start()
                ObjectAnimator.ofFloat(binding.optionTwo, View.TRANSLATION_Y, 0f).start()

                binding.optionOne.animate().alpha(0f).setDuration(1000).setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            binding.optionOne.isClickable = false

                        }
                    }
                )

                binding.optionTwo.animate().alpha(0f).setDuration(1000).setListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            binding.optionTwo.isClickable = false

                        }
                    }
                )
            }
        }
    }

    @SuppressLint("NewApi")
    private fun renderData(it: AppStateMars) {

        when (it) {
            is AppStateMars.Success -> {

                val mars = it.marsPicture

                binding.frameLoadingMars.visibility = View.GONE

                binding.imageButton.setOnClickListener { view ->
                    val picture = mars.photos[0].img_src
                    binding.marsRover.text = mars.photos[0].rover.name.toString()


                    binding.marsPicture.load(
                        picture
                    ) {
                        lifecycle(this@MarsFragment)
                    }
                }

                val string = mars.photos.get(0).rover.name
                val spannable = SpannableStringBuilder(string)

                spannable.insert(string.length, "\n" + mars.photos.get(0).rover.status
                        + "\n" + mars.photos.get(0).rover.launch_date
                )

                val text = spannable.toString().indexOf("\n")
                var current = text.first()
                text.forEach {

                    if (current != it) {
                        spannable.setSpan(BulletSpan(100,
                            ContextCompat.getColor(requireContext(), R.color.myColor), 10),
                            current + 1,
                            it,
                            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }

                    current = it
                }

                spannable.setSpan(BulletSpan(100,
                    ContextCompat.getColor(requireContext(), R.color.myColor), 10),
                    current + 1,
                    spannable.toString().length,
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)



                binding.roverButton.setOnClickListener {
                    binding.marsDescription.text = spannable
                }

                val cameraInfo = mars.photos.get(0).camera.full_name + "\n" + mars.photos.get(0).camera.rover_id

                val spannableCamera = SpannableString(cameraInfo)

                spannableCamera.setSpan(ContextCompat.getDrawable(requireContext(), R.drawable.ic_photo_camera)!!.toBitmap()
                    .let { it1 -> IconMarginSpan(it1) }, 0 , cameraInfo.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                binding.cameraButton.setOnClickListener {
                    binding.marsDescription.text = spannableCamera
                }


            }
            is AppStateMars.Error -> {


            }
            is AppStateMars.Loading -> {
                binding.frameLoadingMars.visibility = View.VISIBLE
            }
        }
    }

    fun String.indexOf(substr: String, ignoreCase: Boolean = true): List<Int> =
        (if (ignoreCase) Regex(substr, RegexOption.IGNORE_CASE) else Regex(substr))
            .findAll(this).map {
                it.range.first
            }.toList()


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = MarsFragment()
    }
}