package com.example.gbmaterialdesign.ui.Nasa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gbmaterialdesign.R
import com.example.gbmaterialdesign.databinding.EarthItemBinding
import com.example.gbmaterialdesign.model.Data.Data
import com.example.gbmaterialdesign.model.Data.Data.Companion.EARTH
import com.example.gbmaterialdesign.model.Data.Data.Companion.MARS
import com.example.gbmaterialdesign.model.Data.Data.Companion.SOLAR
import com.example.gbmaterialdesign.ui.TouchHelper.ItemTouchViewHolder
import com.example.gbmaterialdesign.ui.TouchHelper.ItemTouchHelperAdapater
import com.example.gbmaterialdesign.ui.diffutil.Change
import com.example.gbmaterialdesign.ui.diffutil.DiffUtilCallback
import com.example.gbmaterialdesign.ui.diffutil.createCombinedPayload

class NasaRecyclerAdapter(
    var dataList: MutableList<Pair<Data, Boolean>>,
    val addItem: AddItems, val removeItem: RemoveItem,
) :
    RecyclerView.Adapter<NasaRecyclerAdapter.BaseViewHolder>(), ItemTouchHelperAdapater {

    fun addItemList(dataNew: MutableList<Pair<Data, Boolean>>, position: Int) {
        dataList = dataNew
        notifyItemInserted(position)
    }

    fun removeItemList(dataNew: MutableList<Pair<Data, Boolean>>, position: Int) {
        dataList = dataNew
        notifyItemRemoved(position)
    }

    fun setDataLIstDiffUtil(newList: MutableList<Pair<Data, Boolean>>) {
        val diff = DiffUtil.calculateDiff(DiffUtilCallback(dataList, newList))

        diff.dispatchUpdatesTo(this)
        dataList = newList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        return when (viewType) {

            EARTH -> {

                val view = EarthItemBinding.inflate(LayoutInflater.from(parent.context))
                EarthViewHolder(view)
            }
            MARS -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.mars_item, parent, false)
                MarsViewHolder(view)

            }
            SOLAR -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.solar_item, parent, false)
                SolarViewHolder(view)
            }
            else -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.header_item, parent, false)
                HeaderViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return dataList[position].first.type
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(dataList[position].first)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {

        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {


            val combinedPayload =
                createCombinedPayload(payloads as List<Change<Pair<Data, Boolean>>>)

            if (combinedPayload.newData.first.earth?.id == combinedPayload.oldData.first.earth?.id) {
                holder.itemView.findViewById<TextView>(R.id.earthText).text =
                    combinedPayload.newData.first.earth?.id
            }

            when (combinedPayload.newData.first.type) {
                EARTH -> {
                    if (combinedPayload.newData.first.earth?.id == combinedPayload.oldData.first.earth?.id) {
                        holder.itemView.findViewById<TextView>(R.id.earthText).text =
                            combinedPayload.newData.first.earth?.id
                    }
                }
                MARS -> {
                    if (combinedPayload.newData.first.mars?.id == combinedPayload.oldData.first.mars?.id) {
                        holder.itemView.findViewById<TextView>(R.id.marsText).text =
                            combinedPayload.newData.first.mars?.earth_date
                    }
                }
                SOLAR -> {
                    if (combinedPayload.newData.first.solar?.id == combinedPayload.oldData.first.solar?.id) {
                        holder.itemView.findViewById<TextView>(R.id.solarItemText).text =
                            combinedPayload.newData.first.solar?.messageBody
                    }
                }
            }
        }
    }

    inner class EarthViewHolder(val binding: EarthItemBinding) : BaseViewHolder(binding.root) {

        private val name: TextView = itemView.findViewById(R.id.earthText)
        private val img: ImageView = itemView.findViewById(R.id.earthItemPicture)
        override fun bind(data: Data) {
            name.text = data.earth?.caption + " " + data.earth?.id

            val earth_date: String
            val picture = data.earth?.image
            earth_date = data.earth?.date?.let {
                it.substring(0, it.indexOf(" "))
            }?.replace("-", "/") ?: ""

            val url =
                "https://epic.gsfc.nasa.gov/archive/natural/" + earth_date + "/png/" + picture + ".png"
            Glide.with(itemView.context)
                .load(url)
                .fitCenter()
                .thumbnail()
                .into(img)

            binding.addItemEarth.setOnClickListener {
                addItem.add(layoutPosition)
            }

            binding.removeItemEarth.setOnClickListener {
                removeItem.remove(layoutPosition)
            }

            binding.moveUPItemEarth.setOnClickListener {
                layoutPosition.takeIf {
                    it > 1
                }?.also { currentPosition ->
                    dataList.removeAt(currentPosition).apply {
                        dataList.add(currentPosition - 1, this)
                    }
                    notifyItemMoved(currentPosition, currentPosition - 1)
                }
            }

            binding.moveDownItemEarth.setOnClickListener {
                layoutPosition.takeIf {
                    it < dataList.size - 1
                }?.also { currentPosition ->
                    dataList.removeAt(currentPosition).apply {
                        dataList.add(currentPosition + 1, this)
                    }
                    notifyItemMoved(currentPosition, currentPosition + 1)
                }
            }
        }

        override fun onItemSelected() {
            binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context,
                R.color.myColor))
        }

        override fun onItemClear() {
            binding.root.setBackgroundColor(0)
        }


    }

    inner class MarsViewHolder(itemView: View) : BaseViewHolder(itemView) {

        private val name: TextView = itemView.findViewById(R.id.marsText)
        private val img: ImageView = itemView.findViewById(R.id.marsItemPicture)

        private val removeMars: ImageView = itemView.findViewById(R.id.removeItemMars)
        private val moveUpMars: ImageView = itemView.findViewById(R.id.moveUPItemMars)
        private val moveDownMars: ImageView = itemView.findViewById(R.id.moveDownItemMars)

        override fun bind(data: Data) {

            name.text = data.mars?.earth_date
            Glide.with(itemView.context)
                .load(data.mars?.img_src)
                .fitCenter()
                .thumbnail()
                .into(img)

            removeMars.setOnClickListener {
                removeItem.remove(layoutPosition)
            }

            moveUpMars.setOnClickListener {
                layoutPosition.takeIf {
                    it > 1
                }?.also { currentPosition ->
                    dataList.removeAt(currentPosition).apply {
                        dataList.add(currentPosition - 1, this)
                    }
                    notifyItemMoved(currentPosition, currentPosition - 1)
                }
            }

            moveDownMars.setOnClickListener {
                layoutPosition.takeIf {
                    it < dataList.size - 1
                }?.also { currentPosition ->
                    dataList.removeAt(currentPosition).apply {
                        dataList.add(currentPosition + 1, this)
                    }
                    notifyItemMoved(currentPosition, currentPosition + 1)
                }
            }
        }



        override fun onItemSelected() {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.myColor))
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }


    }

    inner class SolarViewHolder(itemView: View) : BaseViewHolder(itemView) {

        private val name: TextView = itemView.findViewById(R.id.solarItemText)
        private val name_full: TextView = itemView.findViewById(R.id.solarFullText)

        private val removeSolar: ImageView = itemView.findViewById(R.id.removeItemSolar)
        private val moveUpSolar: ImageView = itemView.findViewById(R.id.moveUPItemSolar)
        private val moveDownSolar: ImageView = itemView.findViewById(R.id.moveDownItemSolar)
        override fun bind(data: Data) {
            name.text = data.solar?.messageBody

            name.setOnClickListener {
                dataList[layoutPosition] = dataList[layoutPosition].let {
                    it.first to !it.second
                }
                name_full.visibility =
                    if (dataList[layoutPosition].second) View.VISIBLE else View.GONE
                name_full.text = data.solar?.messageBody
            }

            removeSolar.setOnClickListener {
                removeItem.remove(layoutPosition)
            }

            moveUpSolar.setOnClickListener {
                layoutPosition.takeIf {
                    it > 1
                }?.also { currentPosition ->
                    dataList.removeAt(currentPosition).apply {
                        dataList.add(currentPosition - 1, this)
                    }
                    notifyItemMoved(currentPosition, currentPosition - 1)
                }
            }

            moveDownSolar.setOnClickListener {
                layoutPosition.takeIf {
                    it < dataList.size - 1
                }?.also { currentPosition ->
                    dataList.removeAt(currentPosition).apply {
                        dataList.add(currentPosition + 1, this)
                    }
                    notifyItemMoved(currentPosition, currentPosition + 1)
                }
            }

        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.myColor))
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }


    }

    inner class HeaderViewHolder(itemView: View) : BaseViewHolder(itemView) {

        private val name: TextView = itemView.findViewById(R.id.header_text)
        override fun bind(data: Data) {
            name.text = "Заголовок"

        }

        override fun onItemSelected() {
            TODO("Not yet implemented")
        }

        override fun onItemClear() {
            TODO("Not yet implemented")
        }
    }

    abstract inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        ItemTouchViewHolder {
        abstract fun bind(data: Data)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        dataList.removeAt(fromPosition).apply {
            dataList.add(toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        removeItem.remove(position)
    }


}