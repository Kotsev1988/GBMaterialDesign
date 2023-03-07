package com.example.gbmaterialdesign

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.gbmaterialdesign.databinding.ActivityMainBinding
import com.example.gbmaterialdesign.ui.CURRENT_THEME
import com.example.gbmaterialdesign.ui.KEY_SP

const val ThemeOne = 0
const val ThemeSecond = 1

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        //setTheme(getRealStyleLocal(getCurrentThemeLocal()))
       // setTheme(R.style.Theme_GBMaterialDesign)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

    }

    fun getCurrentThemeLocal(): Int {

        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getInt(CURRENT_THEME, -1)

    }

    fun setCurrentTheme(currentTheme: Int) {

        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(CURRENT_THEME, currentTheme)
        editor.apply()

    }

    fun getRealStyleLocal(currentTheme: Int): Int {

        return when (currentTheme) {
            0 -> R.style.Base_Theme_GBMaterialDesign
            1 -> R.style.AppTheme
            else -> -1
        }
    }
}