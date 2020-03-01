package com.anhtam.gate9.adapter.v2.du_lieu

import com.anhtam.domain.v2.Article
import com.anhtam.gate9.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.related_aricle_item_layout.view.*
import javax.inject.Inject
import javax.inject.Named

class RelatedAdapter @Inject constructor(
        @Named("banner") private val bannerOptions: RequestOptions
) : BaseQuickAdapter<Article, BaseViewHolder>(R.layout.related_aricle_item_layout, mutableListOf()) {
    override fun convert(helper: BaseViewHolder?, item: Article?) {
        val view = helper?.itemView ?: return
        val article = item ?: return
        Glide.with(mContext)
                .load(article.mAvatar)
                .apply(bannerOptions)
                .into(view.imgTinTuc)
        view.tvTinTuc?.text = article.mTitle
        view.tvTime?.text = article.mCreatedTime
    }
}