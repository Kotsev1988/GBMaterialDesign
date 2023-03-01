package com.example.gbmaterialdesign.ui.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.gbmaterialdesign.model.Data.Data
import com.example.gbmaterialdesign.model.Data.Data.Companion.EARTH
import com.example.gbmaterialdesign.model.Data.Data.Companion.MARS
import com.example.gbmaterialdesign.model.Data.Data.Companion.SOLAR

class DiffUtilCallback(private val oldDataList: MutableList<Pair<Data, Boolean>>, private val newDataList: MutableList<Pair<Data, Boolean>>): DiffUtil.Callback() {


    override fun getOldListSize(): Int = oldDataList.size

    override fun getNewListSize(): Int = newDataList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        println("COMBINEDareItemsTheSame ")
        return oldDataList[oldItemPosition] == newDataList[newItemPosition]

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        when(oldDataList[oldItemPosition].first.type){
//            EARTH ->{
//                return oldDataList[oldItemPosition].first.earth?.caption == newDataList[newItemPosition].first.earth?.caption
//            }
//            MARS ->{
//                return oldDataList[oldItemPosition].first.mars?.earth_date == newDataList[newItemPosition].first.mars?.earth_date
//            }
//            SOLAR ->{
//                return oldDataList[oldItemPosition].first.solar?.messageBody == newDataList[newItemPosition].first.solar?.messageBody
//            }
//        }
//        return true

        return oldDataList[oldItemPosition] == newDataList[newItemPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val old = oldDataList[oldItemPosition]
        val new = newDataList[newItemPosition]
        return Change(old, new)
    }
}

    data class Change<T>(val oldData: T, val newData: T)

    fun<T> createCombinedPayload(payloads: List<Change<T>>): Change<T>{
        assert(payloads.isEmpty())

        val firstChange = payloads.first()
        val secondChange = payloads.last()

        return Change(firstChange.oldData, secondChange.newData)
    }
