package com.example.gbmaterialdesign

import android.app.Application
import com.google.android.material.color.DynamicColors

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        //DynamicColors.applyToActivitiesIfAvailable(this)
    }
}