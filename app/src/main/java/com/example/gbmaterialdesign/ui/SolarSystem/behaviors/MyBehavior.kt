package com.example.gbmaterialdesign.ui.SolarSystem.behaviors

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import com.example.gbmaterialdesign.R
import com.google.android.material.appbar.AppBarLayout

class MyBehavior(context: Context, attributeSet: AttributeSet): CoordinatorLayout.Behavior<NestedScrollView>(context, attributeSet) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: NestedScrollView,
        dependency: View
    ): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: NestedScrollView ,
        dependency: View
    ): Boolean {

        if (dependency is AppBarLayout){

            println("@@@ "+child.y)
            println("@@@@D "+dependency.y)

            child.y = (dependency.y + dependency.height).toFloat()
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}