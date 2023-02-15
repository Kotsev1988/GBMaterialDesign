package com.example.gbmaterialdesign.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.gbmaterialdesign.*
import com.example.gbmaterialdesign.databinding.FragmentSettingsBinding

const val KEY_SP = "KEY_SP"
const val CURRENT_THEME = "KEY_CURRENT"

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var parentActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = (context as MainActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (parentActivity.getCurrentThemeLocal()) {
            0 -> binding.groupOne.check(R.id.main_theme)
            1 -> binding.groupOne.check(R.id.app_theme)
        }

        binding.mainTheme.setOnClickListener {
            setCurrentThemeLocal(R.style.Base_Theme_GBMaterialDesign)

            parentActivity.setCurrentTheme(ThemeOne)
            parentActivity.recreate()
        }

        binding.appTheme.setOnClickListener {
            setCurrentThemeLocal(R.style.AppTheme)
            parentActivity.setCurrentTheme(ThemeSecond)
            parentActivity.recreate()
        }

    }

    private fun setCurrentThemeLocal(appTheme: Int) {

        val sharedPreferences = requireActivity()
            .getSharedPreferences(KEY_SP, AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(CURRENT_THEME, appTheme)
        editor.apply()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() =
            SettingsFragment()
    }

}