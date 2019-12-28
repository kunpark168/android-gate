package com.anhtam.reactlibs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class ViewTypeHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    companion object {
        fun from(parent: ViewGroup, @LayoutRes layoutRes: Int): ViewTypeHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                    .inflate(layoutRes, parent, false)
            return ViewTypeHolder(view)
        }
    }
}