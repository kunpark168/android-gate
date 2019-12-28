package com.anhtam.gate9.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.anhtam.domain.Game

class GameDiffCallback : DiffUtil.ItemCallback<Game>() {
    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.gameId == oldItem.gameId
    }

}