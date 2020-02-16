package com.anhtam.gate9.adapter.v2.du_lieu

import android.view.View
import com.anhtam.domain.v2.Article
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.toImage
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.mxh_gate_anh_item_layout.view.*
import javax.inject.Inject
import javax.inject.Named


class HinhAnhAdapter@Inject constructor(
        @Named("banner") val bannerOptions: RequestOptions
): BaseQuickAdapter<Article, BaseViewHolder>(R.layout.mxh_gate_anh_item_layout, arrayListOf()){
    override fun convert(helper: BaseViewHolder?, item: Article?) {
        val article = item ?: return
        val view = helper?.itemView ?: return
        view.tvTinTuc?.text = article.mTitle
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
            helper.setText(R.id.tvTitleGame, game.name)
                    .setText(R.id.tvTypeGame, game.gameType?.name)
        }
    }
}