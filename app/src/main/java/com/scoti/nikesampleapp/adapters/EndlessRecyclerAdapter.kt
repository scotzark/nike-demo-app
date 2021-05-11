package com.scoti.nikesampleapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scoti.nikesampleapp.models.Image
import com.scoti.nikesampleapp.R

class EndlessRecyclerAdapter(val context: Context): RecyclerView.Adapter<EndlessRecyclerAdapter.ViewHolder>() {
    var items = mutableListOf<Image>()

    var numberOfItems = 0

    fun load(items: List<Image>?) {
        this.items.clear()

        if (items != null) {
            this.items.addAll(items)
        }

        numberOfItems = items?.size?:0

        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size*2
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position%items.size])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(image: Image) {
            Glide.with(context).asBitmap().load(image.url).into(itemView.findViewById(
                R.id.image
            ))
        }
    }
}

