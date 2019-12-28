package com.anhtam.gate9.adapter

import com.anhtam.domain.dto.Game
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.ifNotNull
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_recently_hot_tag.view.*

class BannerGameAdapter : BaseQuickAdapter<Game, BaseViewHolder>(R.layout.item_recently_hot_tag, emptyList()) {

    override fun convert(helper: BaseViewHolder?, item: Game?) {
        ifNotNull(helper, item) {
            holder, game ->
            holder.itemView.tvTitleGame.text = game.mTitle
        }
    }

}