package com.anhtam.gate9.adapter.v2.du_lieu

import android.view.View
import com.anhtam.domain.v2.Article
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.toImage
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.mxh_gate_video_item_layout.view.*
import javax.inject.Inject
import javax.inject.Named


class VideoAdapter@Inject constructor(
        @Named("banner") val bannerOptions: RequestOptions,
        @Named("avatar") val avatarOptions: RequestOptions
): BaseQuickAdapter<Article, BaseViewHolder>(R.layout.mxh_gate_video_item_layout, arrayListOf()){
    override fun convert(helper: BaseViewHolder?, item: Article?) {
        val article = item ?: return
        val view = helper?.itemView ?: return
        view.tvTinTuc?.text = article.mTitle
        Glide.with(mContext)
                .load(article.mAvatar?.toImage())
                .apply(bannerOptions)
                .into(view.imgTinTuc)

        val user: User? = article.mUser
        if (user == null || user.mId == 0) {
            view.group?.visibility = View.GONE
        } else {
            view.group?.visibility = View.VISIBLE
            Glide.with(mContext)
                    .load(user.mAvatar?.toImage())
                    .apply(avatarOptions)
                    .into(view.imgUser)
            view.tvName?.text = user.mName
            view.tvTime?.text = user.mCreatedDate
        }
    }
}