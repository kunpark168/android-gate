package com.anhtam.gate9.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.anhtam.domain.v2.PostEntity

class CommentDiffCallback : DiffUtil.ItemCallback<PostEntity>() {
    override fun areItemsTheSame(oldItem: PostEntity, newItem: PostEntity) = oldItem.commentId == newItem.commentId

    override fun areContentsTheSame(oldItem: PostEntity, newItem: PostEntity) = oldItem === newItem
}