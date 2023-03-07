package com.example.gbmaterialdesign.ui.SolarSystem

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.BulletSpan
import android.text.style.ForegroundColorSpan
import android.text.style.URLSpan
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.gbmaterialdesign.R
import com.example.gbmaterialdesign.databinding.FragmentSystemBinding
import com.example.gbmaterialdesign.ui.AppSatates.AppStateSolarSystem
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class SystemFragment : Fragment() {

    private var _binding: FragmentSystemBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this)[SolarSystemViewModel::class.java]
    }

    private val dateFormat: DateFormat = SimpleDateFormat("YYYY-MM-dd")

    lateinit var spannableRainBow: SpannableString

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSystemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        viewModel.getSolarSystemLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -2)
        val dayBeforeYesterday = dateFormat.format(calendar.time)

        calendar.add(Calendar.DATE, -1)
        val yesterday = dateFormat.format(calendar.time)

        viewModel.getEarthData(dayBeforeYesterday, yesterday)

    }

    @SuppressLint("NewApi")
    private fun renderData(it: AppStateSolarSystem) {

        when (it) {
            is AppStateSolarSystem.Success -> {
                binding.frameLoadingSS.visibility = View.GONE

                val string = it.solarSystemWeather.get(it.solarSystemWeather.size - 1).messageBody
                val spannable = SpannableString(string)

                spannable.setSpan(BulletSpan(100, ContextCompat.getColor(requireContext(),
                    R.color.textDarkSecondary), 10), 0,
                    string.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)


                var url = ""
                var startURL: Int = -1
                var endURL: Int = -1
                val matcher = Patterns.WEB_URL.matcher(string)
                while (matcher.find()) {

                    url = matcher.group()

                    startURL = matcher.start()
                    endURL = matcher.end()
                    spannable.setSpan(URLSpan(url), startURL, endURL,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }

                binding.systemText.movementMethod = LinkMovementMethod.getInstance()
                binding.systemText.text = spannable

                Glide.with(requireActivity())
                    .load(url)
                    .into(binding.solSystemWeather)

                //spannableRainBow =SpannableString(string) //- окрашивание символов в цвета радуги
                //rainbow(1)

            }
            is AppStateSolarSystem.Error -> {


            }
            is AppStateSolarSystem.Loading -> {
                binding.frameLoadingSS.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = SystemFragment()
    }

    fun rainbow(i: Int = 1) {
        var currentCount = i

        val x = object : CountDownTimer(2000, 200) {
            override fun onTick(p0: Long) {
                colorText(currentCount)
                currentCount = if (++currentCount > 5) 1 else currentCount
            }

            override fun onFinish() {
                rainbow(currentCount)
            }
        }
        x.start()
    }

    private fun colorText(colorFirstNumber: Int) {
        binding.systemText.setText(spannableRainBow, TextView.BufferType.SPANNABLE)
        spannableRainBow = binding.systemText.text as SpannableString
        val map = mapOf(
            0 to ContextCompat.getColor(requireContext(), R.color.red),
            1 to ContextCompat.getColor(requireContext(), R.color.orange),
            2 to ContextCompat.getColor(requireContext(), R.color.yellow),
            3 to ContextCompat.getColor(requireContext(), R.color.green),
            4 to ContextCompat.getColor(requireContext(), R.color.blue),
            5 to ContextCompat.getColor(requireContext(), R.color.purple_700),
            6 to ContextCompat.getColor(requireContext(), R.color.purple_500)
        )

        val spans = spannableRainBow.getSpans(
            0, spannableRainBow.length,
            ForegroundColorSpan::class.java
        )

        for (span in spans) {
            spannableRainBow.removeSpan(span)
        }

        var colorNumber = colorFirstNumber

        for (i in 0 until binding.systemText.text.length) {
            if (colorNumber == 5) {

                colorNumber = 0
            } else {
                colorNumber += 1
            }
            spannableRainBow.setSpan(
                ForegroundColorSpan(map.getValue(colorNumber)),
                i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
}