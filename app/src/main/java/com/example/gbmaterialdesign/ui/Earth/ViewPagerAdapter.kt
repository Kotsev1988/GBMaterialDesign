package com.example.gbmaterialdesign.ui.Earth

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gbmaterialdesign.ui.Mars.MarsFragment

class ViewPagerAdapter(fm: Fragment): FragmentStateAdapter(fm) {


    override fun getItemCount(): Int {
        return 3
    }


    override fun createFragment(position: Int): Fragment {
       return when (position) {

            0 -> {
                TodayFragment()
            }

            1 -> {
                YesterdayFragment()
            }

            2 -> {
                DayBeforeYesterdayFragment()
            }
            else -> MarsFragment()
        }
    }
}