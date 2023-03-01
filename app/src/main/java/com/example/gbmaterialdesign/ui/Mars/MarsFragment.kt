package com.example.gbmaterialdesign.ui.Mars

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.gbmaterialdesign.databinding.FragmentMarsBinding
import com.example.gbmaterialdesign.ui.AppSatates.AppStateMars

class MarsFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[MarsViewModel::class.java]
    }

    private var _binding: FragmentMarsBinding? = null
    private val binding get() = _binding!!
    private var flag : Boolean = false

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

        binding.hideButtons.setOnClickListener {
            if (flag) {
                binding.groups.visibility = View.INVISIBLE
                flag = !flag
            }else{
                binding.groups.visibility = View.VISIBLE
            }
        }

        binding.optionOne.apply {
            alpha = 0f
        }

        binding.optionTwo.apply {
            alpha = 0f
        }



        binding.fab.setOnClickListener {

            flag = !flag
            if (flag){
                ObjectAnimator.ofFloat(binding.plusIcon, View.ROTATION, 0f, 225f).start()
                ObjectAnimator.ofFloat(binding.optionOne, View.TRANSLATION_Y, -130f).start()
                ObjectAnimator.ofFloat(binding.optionTwo, View.TRANSLATION_Y, -250f).start()

                binding.optionOne.animate().alpha(1f).setDuration(2000).setListener(
                    object : AnimatorListenerAdapter(){
                        override fun onAnimationEnd(animation: Animator) {
                            binding.optionOne.isClickable = true

                        }
                    }
                )

                binding.optionTwo.animate().alpha(1f).setDuration(2000).setListener(
                    object : AnimatorListenerAdapter(){
                        override fun onAnimationEnd(animation: Animator) {
                            binding.optionTwo.isClickable = true

                        }
                    }
                )

            }else{
                ObjectAnimator.ofFloat(binding.plusIcon, View.ROTATION, 225f, 0f).start()
                ObjectAnimator.ofFloat(binding.optionOne, View.TRANSLATION_Y, 0f).start()
                ObjectAnimator.ofFloat(binding.optionTwo, View.TRANSLATION_Y, 0f).start()

                binding.optionOne.animate().alpha(0f).setDuration(1000).setListener(
                    object : AnimatorListenerAdapter(){
                        override fun onAnimationEnd(animation: Animator) {
                            binding.optionOne.isClickable = false

                        }
                    }
                )

                binding.optionTwo.animate().alpha(0f).setDuration(1000).setListener(
                    object : AnimatorListenerAdapter(){
                        override fun onAnimationEnd(animation: Animator) {
                            binding.optionTwo.isClickable = false

                        }
                    }
                )
            }
        }
    }

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
                    ){
                        lifecycle(this@MarsFragment)
                    }
                }

                binding.roverButton.setOnClickListener {

                  binding.marsDescription.text =  mars.photos.get(0).rover.name +" "

                          mars.photos.get(0).rover.status+ " "+mars.photos.get(0).rover.launch_date
                }

                binding.solButton.setOnClickListener {
                    binding.marsDescription.text =mars.photos.get(0).rover.name + " "+
                    mars.photos.get(0).rover.status + " "+mars.photos.get(0).rover.landing_date
                }


            }
            is AppStateMars.Error -> {


            }
            is AppStateMars.Loading -> {
                binding.frameLoadingMars.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = MarsFragment()
    }
}