package com.anhtam.gate9.adapter.article

import com.anhtam.domain.v2.Article
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.toImage
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.mxh_gate_anh_item_layout.view.*

class ArticleImageAdapter : BaseQuickAdapter<Article, BaseViewHolder>(R.layout.mxh_gate_anh_item_layout, ArrayList()) {
    override fun convert(helper: BaseViewHolder?, item: Article?) {
        val view = helper?.itemView ?: return
        val article = item ?: return
        view.tvTitle?.text = article.mTitle
        Glide.with(mContext)
                .load(article.mAvatar?.toImage())
                .into(view.imgBannerGame)
    }
}