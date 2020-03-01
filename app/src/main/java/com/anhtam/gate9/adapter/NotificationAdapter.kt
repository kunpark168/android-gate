package com.anhtam.gate9.adapter

import com.anhtam.domain.Notification
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.diff.NotificationDiffCallback
import com.anhtam.gate9.utils.setHtml
import com.anhtam.gate9.utils.targetStyle
import com.anhtam.gate9.utils.userStyle
import com.anhtam.reactlibs.adapter.AmazingAdapter
import com.anhtam.reactlibs.adapter.ViewTypeHolder
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.item_notification_layout.view.*

class NotificationAdapter(private val requestManager: RequestManager)
    : AmazingAdapter<Notification>(R.layout.item_notification_layout, NotificationDiffCallback()) {
    override fun ViewTypeHolder.bind(item: Notification?) {
        val unwrappedNotification = item ?: return
        itemView.apply {
            requestManager.load("https://ui-avatars.com/api/?background=0D8ABC&color=fff&name=John+Noe")
                    .into(avatarImageView)
            val avatar = "Đỗ Anh Tâm".userStyle(context)
            val target = "Hội những yêu cún và động vật".targetStyle(context)
            val notificationTitle = avatar.plus(" đã đăng trong ").plus(target)
            titleTextView?.setHtml(notificationTitle)
        }

    }

    override fun loadingItem(): Notification {
        return Notification.loading()
    }

}