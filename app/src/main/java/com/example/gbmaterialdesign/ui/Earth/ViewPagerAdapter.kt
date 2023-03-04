package com.example.gbmaterialdesign.ui.Earth

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gbmaterialdesign.ui.Earth.DaysFragment.Companion.BUNDLE_DAY
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ViewPagerAdapter(fm: Fragment): FragmentStateAdapter(fm) {


    private val days = arrayOf("dby", "yesterday", "today")
    val dateFormat: DateFormat = SimpleDateFormat("YYYY-MM-dd")
    override fun getItemCount(): Int {
        return days.size
    }


    override fun createFragment(position: Int): Fragment {
       return when (position) {

            0 -> {
                val bundle = Bundle()

                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DATE, -10)
                val dayBeforeYesterday = dateFormat.format(calendar.time)
                bundle.putString(BUNDLE_DAY, dayBeforeYesterday)
                DaysFragment.newInstance(bundle)
            }

            1 -> {
                val bundle = Bundle()
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DATE, -15)
                val yesterday = dateFormat.format(calendar.time)
                bundle.putString(BUNDLE_DAY, yesterday)
                DaysFragment.newInstance(bundle)
            }

            2 -> {
                val bundle = Bundle()
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DATE, -20)
                val yesterday1 = dateFormat.format(calendar.time)
                bundle.putString(BUNDLE_DAY, yesterday1)
                DaysFragment.newInstance(bundle)
            }

            else ->{
                val bundle = Bundle()
                val currentDate = dateFormat.format(Date())
                bundle.putString(BUNDLE_DAY, currentDate)
                DaysFragment.newInstance(bundle)
            }
        }
    }


}