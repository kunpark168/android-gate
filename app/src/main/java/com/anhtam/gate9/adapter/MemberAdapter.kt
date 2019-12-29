package com.anhtam.gate9.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.anhtam.domain.User
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.debounceClick
import com.anhtam.gate9.utils.ifNotNull
import com.bumptech.glide.RequestManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class MemberAdapter(private val requestManager: RequestManager,private val type: String)
    : BaseQuickAdapter<User, BaseViewHolder>(R.layout.member_item_layout, emptyList()){

    override fun convert(helper: BaseViewHolder?, item: User?) {
        ifNotNull(helper, item) {
            holder, user ->
            holder.setText(R.id.nameTextView, user.name)
            holder.setText(R.id.userIdTextView, "ID: ${user.user_id}")
            val imgAvatar = holder.getView<ImageView>(R.id.avatarImageView)
            requestManager.load(user.avatar)
                    .into(imgAvatar)
            mContext?.let {
                holder.itemView.debounceClick {
//                    if(type == "member") UserDiscussionActivity.start(mContext, user.user_id?.toInt() ?: 0, Category.Member)
//                    else {
//                        UserDiscussionActivity.start(mContext, user.user_id?.toInt() ?: 0, Category.Publisher)
//                    }
                }
            }
            mContext?.let {
                val followButton = holder.getView<TextView>(R.id.followButton)
                if (user.follow == "not_follow") {
                    followButton.background = ContextCompat.getDrawable(mContext, R.drawable.bg_follow)
                    followButton.text = mContext.resources.getString(R.string.follow_plus)
                    followButton.setTextColor(ContextCompat.getColor(mContext, R.color.color_follow))
                }
                else {
                    followButton.background = ContextCompat.getDrawable(mContext, R.drawable.bg_following)
                    followButton.text = mContext.resources.getString(R.string.following)
                    followButton.setTextColor(ContextCompat.getColor(mContext, R.color.color_following))
                }
            }
        }
    }

}