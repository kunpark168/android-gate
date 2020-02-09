package com.anhtam.gate9.adapter.chat

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.anhtam.domain.v2.Userv1
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.lib.loadImage
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_channel.view.*

class ChannelAdapter(private val fragment: Fragment) : BaseQuickAdapter<Channel, BaseViewHolder>(R.layout.item_channel) {
    override fun convert(helper: BaseViewHolder?, item: Channel?) {
        helper?.itemView?.apply {
            val user = item?.user ?:return
            imgAvatar?.loadImage(fragment, user.mAvatar)
            tvUserName?.text = user.mName
            tvMessage?.text = item.messageLatest
            tvTimestamp?.text = item.timestamp
            if(item.hasNewMessage) {
                icon.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_message_unread_gray))
                tvMessage?.setTextColor(ContextCompat.getColor(context, R.color.black))
            } else {
                icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_message_read_gray))
                tvMessage?.setTextColor(ContextCompat.getColor(context, R.color.grey))
            }
        }
    }

}

data class Channel(val user: Userv1, val messageLatest: String?, val timestamp: String?, val hasNewMessage: Boolean)