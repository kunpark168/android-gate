package com.anhtam.gate9.adapter.v2.du_lieu

import com.anhtam.domain.v2.Article
import com.anhtam.gate9.R
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.chi_tiet_bai_viet.tin_game.ChiTietBaiVietTinGameScreen
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.du_lieu_game_item_layout.view.*
import javax.inject.Inject
import javax.inject.Named

class DuLieuGameAdapter @Inject constructor(
        private val navigation: Navigation,
        @Named("banner") val bannerOptions: RequestOptions
): BaseQuickAdapter<Article, BaseViewHolder>(R.layout.du_lieu_game_item_layout){

    init {
        setOnItemChildClickListener { adapter, view, position ->
            when(view.id) {
                R.id.tvTinTuc, R.id.imgTinTuc -> {
                    val article = data[position] ?: return@setOnItemChildClickListener
                    val id = article.mId ?: return@setOnItemChildClickListener
                    val type = article.mArticleType ?: return@setOnItemChildClickListener
                    navigation.addFragment(ChiTietBaiVietTinGameScreen.newInstance(id, article, type))
                }
            }
        }
    }

    override fun convert(helper: BaseViewHolder?, item: Article?) {
        val view = helper?.itemView ?: return
        val article = item ?: return
        view.tvTinTuc?.text = article.mTitle
        view.tvTime?.text = "${article.mCreatedDate ?: ""} ${article.mCategory?.mCategoryName ?: ""}"
        Glide.with(mContext)
                .load(article.mAvatar?.toImage())
                .into(view.imgTinTuc)

        helper.addOnClickListener(R.id.tvTinTuc)
                .addOnClickListener(R.id.imgTinTuc)
    }
}