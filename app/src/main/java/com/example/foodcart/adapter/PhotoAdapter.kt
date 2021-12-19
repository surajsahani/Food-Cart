package com.example.foodblogs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.foodblogs.model.Card
import com.example.foodblogs.utils.Extensions.setImage
import com.example.foodblogs.utils.OnRecyclerViewItemClickListener
import com.example.foodcart.R
import com.google.android.material.textview.MaterialTextView

class PhotoAdapter(private val photoList:MutableList<String> = mutableListOf()): RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val foodImage = itemView.findViewById<ImageView>(R.id.iv_photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_photo_recycler,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = photoList[position]
        holder.foodImage.setImage(element)
    }

    override fun getItemCount(): Int = photoList.size

    fun updateList(list:List<String>){
        photoList.clear()
        photoList.addAll(list)
        notifyItemRangeChanged(0,photoList.size)
    }

}