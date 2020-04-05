package com.anhtam.gate9.adapter

import com.anhtam.gate9.R
import com.anhtam.gate9.helper.formatToHtml
import com.anhtam.gate9.restful.entities.Notification
import com.anhtam.gate9.utils.setHtml
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.utils.userStyle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_notification_layout.view.*
import javax.inject.Inject
import javax.inject.Named

class NotificationAdapter @Inject constructor(
        @Named("avatar") val options: RequestOptions)
    : BaseQuickAdapter<Notification, BaseViewHolder>(R.layout.item_notification_layout, ArrayList()) {

    override fun convert(helper: BaseViewHolder?, item: Notification?) {
        val notification = item ?: return
        val view =  helper?.itemView ?: return
        Glide.with(mContext)
                .load(notification.createdUser?.mAvatar?.toImage())
                .apply(options)
                .into(view.avatarImageView)

        val name = notification.createdUser?.mName ?: ""
        val nameStyle = name.userStyle(mContext)
        val content = nameStyle.plus(" đã bình luận bài viết của bạn.")
        view.titleTextView?.setHtml(content.formatToHtml())
        view.timestampTextView?.text = notification.createdDate
        helper.addOnClickListener(R.id.turnOffTextView)
    }
}