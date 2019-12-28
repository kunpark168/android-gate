package com.anhtam.gate9.v2.charts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anhtam.gate9.R

class ChartsAdapter : RecyclerView.Adapter<ChartsAdapter.ChartsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_charts_user, parent, false)
        return ChartsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChartsViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 7
    }

     class ChartsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}