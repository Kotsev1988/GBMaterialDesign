package com.example.gbmaterialdesign.ui.utils

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import java.time.format.DecimalStyle

class MyImageView @JvmOverloads constructor (context: Context, attributeSet: AttributeSet, defStyle: Int = 0): AppCompatImageView(context, attributeSet) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}