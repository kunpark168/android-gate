package com.anhtam.gate9.adapter.chat

import androidx.fragment.app.Fragment
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.lib.loadImage
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import kotlinx.android.synthetic.main.item_receive_message.view.*

class MessengerAdapter(private val fragment: Fragment, private val url: String?) : BaseMultiItemQuickAdapter<Message, BaseViewHolder>(emptyList()){

    init {
        addItemType(0, R.layout.item_sent_message)
        addItemType(1, R.layout.item_receive_message)
    }
    override fun convert(helper: BaseViewHolder?, item: Message?) {
        val message = item ?: return
        when(helper?.itemViewType) {
            0 -> {
                helper.setText(R.id.tvMessage, message.text)
                helper.setText(R.id.tvTimestamp, message.time)
            }
            1 -> {
                helper.setText(R.id.tvMessage, message.text)
                helper.setText(R.id.tvTimestamp, message.time)
                helper.itemView.apply {
                    imgAvatar?.loadImage(fragment, url)
                }
            }
        }
    }

}

data class Message(val messageType: Int, val text: String?, val time: String?) : MultiItemEntity{
    override fun getItemType() = messageType

}