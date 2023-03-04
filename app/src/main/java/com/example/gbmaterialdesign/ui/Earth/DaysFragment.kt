package com.example.gbmaterialdesign.ui.Earth

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.TypefaceSpan
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Space
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.ResourcesCompat.getFont
import androidx.core.text.bold
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.load
import com.example.gbmaterialdesign.R
import com.example.gbmaterialdesign.databinding.FragmentDaysBinding
import com.example.gbmaterialdesign.ui.AppSatates.AppStateEarth


class DaysFragment : Fragment() {

    private var _binding: FragmentDaysBinding? = null
    private val binding get() = _binding!!
    private var argument: String = ""
    var flag = false

    private val viewModel by lazy {
        ViewModelProvider(this)[EarthViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(BUNDLE_DAY).let {
            if (it != null) {
                argument = it
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getEarthLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })

        viewModel.getEarthData(argument)
    }

    @SuppressLint("NewApi")
    private fun renderData(it: AppStateEarth) {

        when (it) {
            is AppStateEarth.Success -> {
                binding.frameLoadingDay.visibility = View.GONE

                val earth_date: String
                val earth = it.earthPicture



                    val picture = earth[0].image

                    earth_date = earth[0].date.let {
                        it.substring(0, it.indexOf(" "))
                    }.replace("-", "/")

                    val url =
                        "https://epic.gsfc.nasa.gov/archive/natural/" + earth_date + "/png/" + picture + ".png"


                    val transitionSet = TransitionSet()
                    val fade = Fade()
                    fade.duration = 2000
                    val bounds = ChangeBounds()
                    bounds.duration = 2000
                    transitionSet.addTransition(fade)
                    transitionSet.addTransition(bounds)
                    val slide = Slide(Gravity.END)
                    transitionSet.addTransition(slide)

                    TransitionManager.beginDelayedTransition(binding.daysContainer, transitionSet)
                    binding.todayPicture.load(
                        url
                    ) {

                        lifecycle(this@DaysFragment)
                        crossfade(true)
                    }
                    val params: ViewGroup.LayoutParams = binding.todayPicture.layoutParams
                    var scaleType: ImageView.ScaleType



                    binding.todayPicture.setOnClickListener {


                        flag = !flag
                        if (flag) {
                            it.animate().alpha(1f).setDuration(3000).start()

                            params.height = ViewGroup.LayoutParams.MATCH_PARENT
                            scaleType = ImageView.ScaleType.CENTER_CROP

                        } else {

                            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                            scaleType = ImageView.ScaleType.FIT_CENTER
                        }
                        binding.todayPicture.layoutParams = params
                        binding.todayPicture.scaleType = scaleType
                    }

                val string = it.earthPicture.get(0).caption
                val spannable = SpannableStringBuilder(string)

                val typeface = ResourcesCompat.getFont(requireContext(), R.font.aladin)
                spannable.setSpan(typeface?.let { it1 -> TypefaceSpan(it1) }, 0, string.length, SpannableStringBuilder.SPAN_EXCLUSIVE_INCLUSIVE)


                spannable.insert(string.length, earth_date)
                    binding.dateOfPicture.text = spannable

            }
            is AppStateEarth.Error -> {


            }
            is AppStateEarth.Loading -> {
                binding.frameLoadingDay.visibility = View.VISIBLE
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val BUNDLE_DAY = "Day"
        fun newInstance(bundle: Bundle): DaysFragment {

            val fragment = DaysFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
