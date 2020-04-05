package com.anhtam.gate9.adapter.v2

import com.anhtam.gate9.R
import com.anhtam.gate9.utils.toImage
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.select_photo_item_layout.view.*
import javax.inject.Inject

class ChooseGalleryAdapter @Inject constructor(): BaseQuickAdapter<String, BaseViewHolder>(R.layout.select_photo_item_layout, mutableListOf()) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        val path = item ?: return
//        val bitmap = BitmapFactory.decodeFile(path)
        val view = helper?.itemView ?: return
        Glide.with(mContext)
//                .load(bitmap)
                .load(path.toImage())
                .apply(RequestOptions().placeholder(R.color.white).error(R.color.white))
                .centerCrop()
                .into(view.imgPhoto)
        helper.addOnClickListener(R.id.tvCancel)
    }
}