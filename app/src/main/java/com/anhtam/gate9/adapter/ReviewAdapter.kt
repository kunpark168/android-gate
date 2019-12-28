package com.anhtam.gate9.adapter

import android.text.Html
import com.anhtam.domain.ReviewDTO
import com.anhtam.gate9.R
import com.bumptech.glide.RequestManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_review_user.view.*

class ReviewAdapter(private val requestManager: RequestManager) : BaseQuickAdapter<ReviewDTO, BaseViewHolder>(R.layout.item_review_user, emptyList()) {
    override fun convert(helper: BaseViewHolder?, item: ReviewDTO?) {
        val holder = helper ?: return
        val review = item ?: return
        holder.itemView.apply {
            ratingBar?.rating = review.rating ?: 0f
            requestManager.load(review.avatar).into(avatarImageView)
            userNameTextView?.text = review.name
            contentTextView?.text = review.content?.let { Html.fromHtml(it) } ?: ""
            likeTextView?.text = review.reaction?.totalLike?.toString() ?: "0"
            dislikeTextView?.text = review.reaction?.totalDislike?.toString() ?: "0"
            commentTextView?.text = "0"
            loveTextView?.text = "0"
        }
    }
}