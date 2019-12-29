package com.anhtam.gate9.adapter

import android.content.Context
import android.widget.ImageView
import com.anhtam.domain.Game
import com.anhtam.gate9.adapter.navigator.IBannerNavigator
import com.anhtam.gate9.R
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.v2.discussion.game.GameDiscussionActivity
import com.anhtam.gate9.utils.debounceClick
import com.anhtam.gate9.utils.ifNotNull
import com.bumptech.glide.RequestManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class GroupBannerAdapter(private val navigation: Navigation?, private val requestManager: RequestManager):
        BaseQuickAdapter<Game, BaseViewHolder>(R.layout.item_new_game, emptyList()),
        IBannerNavigator {

    override fun navigateToSocialDiscussion(context: Context?, link: String, gameId: String) {
        navigation?.addFragment(GameDiscussionActivity.newInstance(link, gameId))
    }

    override fun convert(helper: BaseViewHolder?, item: Game?) {
        ifNotNull(helper, item) {
            holder, game ->
            holder.setText(R.id.tvTitle, game.name)
            val type = game.types?.get(0)?.name
            holder.setText(R.id.tvContent, type)
            val bannerImageView = holder.getView<ImageView>(R.id.imgNewGame)
            requestManager.load(Config.IMG_URL + game.avatarGame)
                    .into(bannerImageView)
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

            val link = game.link ?: (game.avatar ?: "")
            val gameId = game.gameId ?: ""
            holder.itemView.apply {
                debounceClick {
                    navigateToSocialDiscussion(context, link, gameId)
                }
            }
        }
    }
}