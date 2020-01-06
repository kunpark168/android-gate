package com.anhtam.gate9.adapter.v2

import android.graphics.BitmapFactory
import com.anhtam.gate9.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.select_photo_item_layout.view.*
import javax.inject.Inject
import javax.inject.Named

class ChooseGalleryAdapter @Inject constructor(
        @Named("banner") val bannerOptions: RequestOptions
): BaseQuickAdapter<String, BaseViewHolder>(R.layout.select_photo_item_layout, mutableListOf()) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        val path = item ?: return
        val bitmap = BitmapFactory.decodeFile(path)
        val view = helper?.itemView ?: return
        Glide.with(mContext)
                .load(bitmap)
                .apply(bannerOptions)
                .into(view.imgPhoto)
        helper.addOnClickListener(R.id.tvCancel)
    }
}