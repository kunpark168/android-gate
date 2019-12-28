package com.anhtam.reactlibs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anhtam.reactlibs.R

class EmptyHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    companion object {
        fun from(parent: ViewGroup): EmptyHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                    .inflate(R.layout.share_loading_load_more_view, parent, false)
            return EmptyHolder(view)
        }
    }
}
