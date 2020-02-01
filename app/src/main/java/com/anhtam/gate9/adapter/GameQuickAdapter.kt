package com.anhtam.gate9.adapter

import android.view.View
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.toImage
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.shared_item_game_layout.view.*
import kotlinx.android.synthetic.main.shared_play_banner_game_layout.view.*

class GameQuickAdapter :
        BaseQuickAdapter<Game, BaseViewHolder>(R.layout.shared_item_game_layout) {


    override fun convert(helper: BaseViewHolder?, item: Game?) {
        val unwrappedHolder = helper ?: return
        val unwrappedGame = item ?: return
        unwrappedHolder.itemView.apply {
            tvTitle?.text = unwrappedGame.name
            Glide.with(mContext)
                    .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.img_holder_banner)
                    .error(R.drawable.img_holder_banner))
                    .load(unwrappedGame.avatar?.toImage())
                    .centerCrop()
                    .into(imgAvatarGame)
            val theme = unwrappedGame.imgCover // Xu ly
            if (theme == null) {
                imgBannerGame.visibility = View.GONE
            } else {
                Glide.with(mContext)
                        .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.img_holder_banner)
                        .error(R.drawable.img_holder_banner))
                        .load(theme.toImage())
                        .into(imgBannerGame)
            }
            tvCategoryGame?.text = Phrase.from(context?.getString(R.string.game_type_and_download))
                    .put("type", unwrappedGame.gameTypeStr ?: "")
                    .put("size", unwrappedGame.capacity ?: "0MB").format()
            csRating?.init(unwrappedGame.point ?: "0", "-" ?: "0")
//
//            imgAvatarGame?.debounceClick {
//                GameDiscussionScreen.start(context, unwrappedGame.link, unwrappedGame.gameId)
//            }
//
//            tvTitle?.debounceClick {
//                GameDiscussionScreen.start(context, unwrappedGame.link, unwrappedGame.gameId)
//            }

        }
    }
}
