package com.example.foodblogs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodblogs.utils.Extensions.setImage
import com.example.foodcart.R

class SliderAdapter(private val imageList:MutableList<String> = mutableListOf()): RecyclerView.Adapter<SliderAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val sliderImage = itemView.findViewById<ImageView>(R.id.iv_slider_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.slider_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = imageList[position]
        holder.sliderImage.setImage(element)
    }

    override fun getItemCount(): Int = imageList.size

    fun updateList(list:List<String>){
        imageList.clear()
        imageList.addAll(list)
        notifyItemRangeChanged(0,imageList.size)
    }

}