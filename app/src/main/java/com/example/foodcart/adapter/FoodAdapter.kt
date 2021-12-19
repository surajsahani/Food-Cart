package com.example.foodblogs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.foodblogs.model.Card
import com.example.foodblogs.model.Where
import com.example.foodblogs.utils.Extensions.setImage
import com.example.foodblogs.utils.OnRecyclerViewItemClickListener
import com.example.foodcart.R
import com.google.android.material.textview.MaterialTextView

class FoodAdapter(private val foodList:MutableList<Card> = mutableListOf(), private val callback: OnRecyclerViewItemClickListener<Card>): RecyclerView.Adapter<FoodAdapter.ViewHolder>()  {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val foodImage = itemView.findViewById<ImageView>(R.id.iv_food_one)
        val foodTitle = itemView.findViewById<MaterialTextView>(R.id.tv_food_one_title)
        val foodDescription = itemView.findViewById<MaterialTextView>(R.id.tv_food_one_description)
        val infoTitle = itemView.findViewById<MaterialTextView>(R.id.tv_about_title)
        val info = itemView.findViewById<MaterialTextView>(R.id.tv_about)
        val location = itemView.findViewById<MaterialTextView>(R.id.tv_where)
        val bestDishTitle = itemView.findViewById<MaterialTextView>(R.id.tv_best_dishes_title)
        val bestDish = itemView.findViewById<MaterialTextView>(R.id.tv_best_dishes)
        val photoRecycler = itemView.findViewById<RecyclerView>(R.id.photo_recycler)
        val linearView = itemView.findViewById<LinearLayout>(R.id.ll_food_info)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = foodList[position]
        holder.apply {
            foodImage.setImage(element.img)
            foodTitle.text = element.title
            foodDescription.text = element.desc
            //About section
            when{
                element.details.about.isEmpty() -> {
                    infoTitle.visibility = View.GONE
                    info.visibility = View.GONE
                }
                else -> info.text = element.details.about[0]
            }
            //Where section
            var whereString = ""
            when(element.details.where.size){
                1 -> {
                    when(element.details.where[0].distance){
                        null -> location.text = "${element.details.where[0].name}"
                        else -> location.text = "${element.details.where[0].name}- ${element.details.where[0].distance.toString()} km"
                    }
                }
                else -> {
                    for(item in element.details.where.indices){
                        whereString += when(element.details.where[item].distance){
                            null -> "${item + 1}. ${element.details.where[item].name}\n"
                            else -> "${item + 1}. ${element.details.where[item].name}- ${element.details.where[item].distance.toString()} km\n"
                        }
                    }
                    location.text = whereString
                }
            }
            //Best dishes section
            var bestDishesString = ""
            when(element.details.dishes.size){
                0 -> {
                    bestDishTitle.visibility = View.GONE
                    bestDish.visibility = View.GONE
                }
                else -> {
                    bestDishTitle.visibility = View.VISIBLE
                    bestDish.visibility = View.VISIBLE
                    bestDishTitle.text = "BEST " + element.title + " DISHES"
                    when(element.details.dishes.size){
                        1 -> bestDish.text = element.details.dishes[0]
                        else -> {
                            for(item in element.details.dishes.indices){
                                bestDishesString += "${item+1}. ${element.details.dishes[item]}\n"
                            }
                            bestDish.text = bestDishesString
                        }
                    }
                }
            }
            //Photos section
            val photoAdapter = PhotoAdapter()
            photoRecycler.adapter = photoAdapter
            photoAdapter.updateList(element.details.images)
            //Handling UI
            itemView.setOnClickListener {
                when(linearView.visibility){
                    View.VISIBLE -> linearView.visibility = View.GONE
                    View.GONE -> linearView.visibility = View.VISIBLE
                }
                callback.invoke(itemView,adapterPosition,element)
            }
        }
    }

    override fun getItemCount(): Int = foodList.size

    fun updateList(list:List<Card>){
        foodList.clear()
        foodList.addAll(list)
        notifyItemRangeChanged(0,foodList.size)
    }
}