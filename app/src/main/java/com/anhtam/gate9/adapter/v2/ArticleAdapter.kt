package com.anhtam.gate9.adapter.v2

import android.view.View
import com.anhtam.domain.v2.Article
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.toImage
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.mxh_gate_tin_game_item_layout.view.*
import javax.inject.Inject
import javax.inject.Named

class ArticleAdapter @Inject constructor(
        @Named("banner") val bannerOptions: RequestOptions
): BaseQuickAdapter<Article, BaseViewHolder>(R.layout.mxh_gate_tin_game_item_layout){

    override fun convert(helper: BaseViewHolder?, item: Article?) {val view = helper?.itemView ?: return
        val article = item ?: return
        view.tvTinTuc?.text = article.mTitle
        view.tvTime?.text = "${article.mCreatedDate ?: ""} ${article.mCategory?.mCategoryName ?: ""}"
        Glide.with(mContext)
                .load(article.mAvatar?.toImage())
                .into(view.imgTinTuc)

        val game: Game? = article.mGame
        if (game == null || game.gameId == 0) {
            view.group?.visibility = View.GONE
        } else {
            view.group?.visibility = View.VISIBLE
            Glide.with(mContext)
                    .load(game.avatar?.toImage())
                    .apply(bannerOptions)
                    .into(view.imgGame)
            view.tvTitleGame?.text = game.name
            view.tvTypeGame?.text = game.gameType?.name

            helper.addOnClickListener(R.id.tvTitleGame)
                    .addOnClickListener(R.id.imgGame)
                    .addOnClickListener(R.id.tvTinTuc)
                    .addOnClickListener(R.id.imgTinTuc)

        }
    }
}