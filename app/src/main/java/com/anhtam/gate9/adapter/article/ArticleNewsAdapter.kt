package com.anhtam.gate9.adapter.article

import com.anhtam.domain.v2.protocol.Game
import com.bumptech.glide.RequestManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.shared_item_data_layout.view.*
import com.anhtam.gate9.R
import com.bumptech.glide.request.RequestOptions

class ArticleNewsAdapter(private val requestManager: RequestManager
) : BaseQuickAdapter<Game, BaseViewHolder>(R.layout.shared_item_data_layout) {

    override fun convert(helper: BaseViewHolder?, item: Game?) {
        val unwrappedHolder = helper ?: return
        val unwrappedArticle = item ?: return
        unwrappedHolder.itemView.apply {
            tvTitleArticle?.text = unwrappedArticle.name
            tvTitleGame?.text = unwrappedArticle.name
            tvCreatedAt?.text = unwrappedArticle.createdDate?.plus(" | ")?.plus(unwrappedArticle.gameTypes)
            tvTypeGame?.text = unwrappedArticle.gameTypeStr
            requestManager.load(item.photos).apply(RequestOptions().placeholder(R.drawable.img_holder_banner).error(R.drawable.img_holder_banner)).into(imgBannerGame)
            requestManager.load(item.avatar).apply(RequestOptions().placeholder(R.drawable.img_holder_banner).error(R.drawable.img_holder_banner)).into(imgNewGame)
        }
    }
}