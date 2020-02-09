package com.anhtam.gate9.adapter

import android.content.Context
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.gate9.adapter.navigator.IBannerNavigator
import com.anhtam.gate9.R
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.v2.discussion.game.GameDiscussionScreen
import com.anhtam.gate9.utils.ifNotNull
import com.anhtam.gate9.utils.toImage
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_new_game.view.*
import javax.inject.Inject
import javax.inject.Named

class GroupBannerAdapter @Inject constructor(val navigation: Navigation?,
                                             @Named("banner")val bannerOptions: RequestOptions
): BaseQuickAdapter<Game, BaseViewHolder>(R.layout.item_new_game, emptyList()){

    init {
        setOnItemClickListener { _, _, position ->
            val game = data[position]
            navigation?.addFragment(GameDiscussionScreen.newInstance(game))
        }
    }

    override fun convert(helper: BaseViewHolder?, item: Game?) {
        val game = item ?: return
        val view = helper?.itemView ?: return
        val type = game.gameType?.name ?: ""
        view.tvContent?.text = type
        view.tvTitle?.text = game.name ?: ""
        Glide.with(mContext)
                .load(game.avatar?.toImage())
                .apply(bannerOptions)
                .into(view.imgNewGame)
        ifNotNull(helper, item) {
            holder, _ ->
            if (holder.adapterPosition < 2) {
                holder.setVisible(R.id.vHor, true)
            } else {
                holder.setVisible(R.id.vHor, false)
            }
            if (holder.adapterPosition % 2 == 0) {
                holder.setVisible(R.id.vVer, true)
            } else {
                holder.setVisible(R.id.vVer, false)
            }
        }
    }
}