package com.example.gbmaterialdesign.ui.SolarSystem.behaviors

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import com.example.gbmaterialdesign.R
import com.google.android.material.appbar.AppBarLayout
import java.lang.Math.abs

class BehaviorButton(context: Context, attributeSet: AttributeSet): CoordinatorLayout.Behavior<View>(context, attributeSet) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return  (dependency.id == R.id.mainAppBar)
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View ,
        dependency: View
    ): Boolean {

        if (dependency.id == R.id.mainAppBar){

            child.y = dependency.y+(dependency.height-child.height/2)
            child.x = (dependency.width - child.width ).toFloat()

            println("DEP.Y "+dependency.y + "DEP.Y "+abs(dependency.y) + " DEP.H "+dependency.height)

            child.alpha = (1 - (abs(dependency.y)/(dependency.height/2))).toFloat()

        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}