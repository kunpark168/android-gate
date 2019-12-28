package com.anhtam.gate9.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.anhtam.domain.Notification

class NotificationDiffCallback : DiffUtil.ItemCallback<Notification>(){
    override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem.title == newItem.title
    }

}