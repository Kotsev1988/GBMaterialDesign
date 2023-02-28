package com.example.gbmaterialdesign.ui.TouchHelper

interface ItemTouchHelperAdapater {

    fun onItemMove(fromPosition: Int, toPosition: Int)

    fun onItemDismiss(position: Int)
}