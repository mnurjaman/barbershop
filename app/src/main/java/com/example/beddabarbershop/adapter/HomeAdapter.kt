package com.example.beddabarbershop.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beddabarbershop.model.HomeModel
import com.example.beddabarbershop.R

class HomeAdapter (private val newList: ArrayList<HomeModel>):
    RecyclerView.Adapter<HomeAdapter.RecyclerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_homemodelrambut,parent,false)
        return RecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currentItem = newList[position]
        holder.img_Model.setImageResource(currentItem.img_Model)
        holder.item_Jenismodel.text = currentItem.item_Jenismodel
        holder.item_Modelrambut.text = currentItem.item_Modelrambut
    }

    override fun getItemCount(): Int {
        return newList.size
    }

    class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val img_Model: ImageView = itemView.findViewById(R.id.img_model)
        val item_Jenismodel: TextView = itemView.findViewById(R.id.item_jenismodel)
        val item_Modelrambut: TextView = itemView.findViewById(R.id.item_modelrambut)

    }
}