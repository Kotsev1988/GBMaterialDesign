package com.example.gbmaterialdesign.ui.Nasa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gbmaterialdesign.R
import com.example.gbmaterialdesign.databinding.EarthItemBinding
import com.example.gbmaterialdesign.model.Data.Data
import com.example.gbmaterialdesign.model.Data.Data.Companion.EARTH
import com.example.gbmaterialdesign.model.Data.Data.Companion.MARS
import com.example.gbmaterialdesign.model.Data.Data.Companion.SOLAR

class NasaRecyclerAdapter(var dataList: MutableList<Pair<Data, Boolean>>, val addItem: AddItems, val removeItem: RemoveItem): RecyclerView.Adapter<NasaRecyclerAdapter.BaseViewHolder>() {

    fun addItemList(dataNew: MutableList<Pair<Data, Boolean>>, position: Int){
        dataList = dataNew
        notifyItemInserted(position)
    }

    fun removeItemList(dataNew: MutableList<Pair<Data, Boolean>>, position: Int){
        dataList = dataNew
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        return when(viewType){

            EARTH ->{

                val view = EarthItemBinding.inflate(LayoutInflater.from(parent.context))
                 EarthViewHolder(view)
            }
            MARS ->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.mars_item, parent, false)
                MarsViewHolder(view)

            }
            SOLAR ->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.solar_item, parent, false)
                SolarViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.header_item, parent, false)
                HeaderViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    override fun getItemViewType(position: Int): Int {
        return dataList[position].first.type
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(dataList[position].first)
    }

    inner class EarthViewHolder(val binding : EarthItemBinding): BaseViewHolder(binding.root){

        private val name: TextView = itemView.findViewById(R.id.earth_text)
        private val img: ImageView = itemView.findViewById(R.id.earth_picture)
        override fun bind(data: Data){
          name.text = data.earth?.caption+" "+data.earth?.identifier

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
                        dataList.add(currentPosition-1, this)
                    }
                    notifyItemMoved(currentPosition, currentPosition-1)
                }
            }

            binding.moveDownItemEarth.setOnClickListener {
                layoutPosition.takeIf {
                    it < dataList.size -1
                }?.also { currentPosition ->
                    dataList.removeAt(currentPosition).apply {
                        dataList.add(currentPosition+1, this)
                    }
                    notifyItemMoved(currentPosition, currentPosition+1)
                }
            }
        }
    }

    inner class MarsViewHolder(itemView: View): BaseViewHolder(itemView){

        private val name: TextView = itemView.findViewById(R.id.mars_text)
        private val img: ImageView = itemView.findViewById(R.id.mars_picture)
        override fun bind(data: Data){

            name.text = data.mars?.earth_date
            Glide.with(itemView.context)
                .load(data.mars?.img_src)
                .fitCenter()
                .thumbnail()
                .into(img)
        }
    }

    inner class SolarViewHolder(itemView: View): BaseViewHolder(itemView){

        private val name: TextView = itemView.findViewById(R.id.solar_text)
        private val name_full: TextView = itemView.findViewById(R.id.solar_full_text)
        override fun bind(data: Data){
            name.text = data.solar?.messageBody

            name.setOnClickListener {
                dataList[layoutPosition] = dataList[layoutPosition].let {
                    it.first to !it.second
                }
                name_full.visibility = if (dataList[layoutPosition] .second) View.VISIBLE else View.GONE
                name_full.text = data.solar?.messageBody
            }

        }
    }

    inner class HeaderViewHolder(itemView: View): BaseViewHolder(itemView){

        private val name: TextView = itemView.findViewById(R.id.header_text)
        override fun bind(data: Data){
            name.text = "Заголовок"

        }
    }

    abstract inner class BaseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
      abstract fun bind(data: Data)
    }
}