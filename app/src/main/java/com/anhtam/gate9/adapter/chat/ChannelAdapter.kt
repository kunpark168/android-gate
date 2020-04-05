package com.anhtam.gate9.adapter.chat

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.anhtam.domain.v2.Userv1
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.lib.loadImage
import com.anhtam.gate9.vo.Letter
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_channel.view.*
import javax.inject.Inject

class ChannelAdapter @Inject constructor():  BaseQuickAdapter<Letter, BaseViewHolder>(R.layout.item_channel) {
    override fun convert(helper: BaseViewHolder?, item: Letter?) {
        helper?.itemView?.apply {
            val user = item?.mSender ?:return
            val avatar = if (user.mAvatarPath.isNullOrEmpty()) user.mAvatar else user.mAvatarPath
            Glide.with(context).load(avatar?.toImage()).into(imgAvatar)
            tvUserName?.text = user.mName
            tvMessage?.text = item.mTitle
            tvTimestamp?.text = item.mCreatedDate
            if(item.mRead != true) {
                icon.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_message_unread_gray))
                tvMessage?.setTextColor(ContextCompat.getColor(context, R.color.black))
            } else {
                icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_message_read_gray))
                tvMessage?.setTextColor(ContextCompat.getColor(context, R.color.grey))
            }
        }
    }

}
