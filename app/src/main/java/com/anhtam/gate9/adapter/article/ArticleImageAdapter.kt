package com.anhtam.gate9.adapter.article

import android.widget.ImageView
import android.widget.TextView
import com.anhtam.domain.v2.Article
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.toImage
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import javax.inject.Inject
import javax.inject.Named

class ArticleImageAdapter @Inject constructor(
        @Named("banner") val bannerOptions: RequestOptions
) : BaseMultiItemQuickAdapter<ArticleEntity, BaseViewHolder>(ArrayList()) {

    init {
        addItemType(1, R.layout.mxh_gate_tin_game_item_layout)
        addItemType(2, R.layout.mxh_gate_tin_game_item_layout)
        addItemType(3, R.layout.mxh_gate_video_item_layout)
        addItemType(4, R.layout.mxh_gate_anh_item_layout)
    }
    override fun convert(helper: BaseViewHolder?, item: ArticleEntity?) {
        val holder = helper ?: return
        val article = item?.article ?: return
        holder.setText(R.id.tvTinTuc, article.mTitle)
        val tvTime = holder.getView<TextView>(R.id.tvTime)
        tvTime?.text = "${article.mCreatedTime} ${article.mCategoryName}"
        val imgTinTuc = helper.getView<ImageView>(R.id.imgTinTuc)
        imgTinTuc?.run {
            Glide.with(mContext)
                    .load(article.mAvatar?.toImage())
                    .apply(bannerOptions)
                    .into(imgTinTuc)
        }
//
        if (item.category in 1..2){
            val game: Game = article.mGame ?: return
            val imgGame = holder.getView<ImageView>(R.id.imgGame)
            imgGame?.run {
                Glide.with(mContext)
                        .load(game.avatar?.toImage())
                        .apply(bannerOptions)
                        .into(imgGame)
            }
            holder.setText(R.id.tvTitleGame, game.name)
                    .setText(R.id.tvTypeGame, game.gameType?.name)
        }
        if (item.category in 3..4){
            val game: Game = article.mGame ?: return
            val imgAvatar = holder.getView<ImageView>(R.id.imgAvatar)
            imgAvatar?.run {
                Glide.with(mContext)
                        .load(game.avatar?.toImage())
                        .apply(bannerOptions)
                        .into(imgAvatar)
            }
            holder.setText(R.id.tvName, game.name)
                    .setText(R.id.tvCreateTime, game.gameType?.name)
        }
    }
}

class ArticleEntity(val article: Article, val category: Int): MultiItemEntity{
    override fun getItemType() = category
}