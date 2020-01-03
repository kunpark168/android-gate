package com.anhtam.gate9.adapter.v2

import com.anhtam.domain.v2.User
import com.anhtam.gate9.R
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.gallery.GalleryScreen
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import kotlinx.android.synthetic.main.photo_n_item_layout.view.*
import javax.inject.Inject
import javax.inject.Named

class PhotoAdapter @Inject constructor(
        val navigation: Navigation,
        @Named("banner") val  bannerOptions: RequestOptions
) : BaseMultiItemQuickAdapter<PhotoEntity, BaseViewHolder>(mutableListOf()){
    
    lateinit var user: User
    
    init {
        addItemType(PhotoEntity.GRID_1, R.layout.photo_1_item_layout)
        addItemType(PhotoEntity.GRID_4, R.layout.photo_4_item_layout)
        addItemType(PhotoEntity.GRID_N, R.layout.photo_n_item_layout)
    }

    override fun convert(helper: BaseViewHolder?, item: PhotoEntity?) {
        val photo = item?.photo ?: return
        val view = helper?.itemView ?: return
        Glide.with(mContext)
                .load(photo.toImage())
                .apply(bannerOptions)
                .into(view.imgPhoto)
        if (item.type == PhotoEntity.GRID_N) {
            val more = data.size - 4
            view.tvMore.text = "+".plus(more.toString())
        }
        view.imgPhoto?.setOnClickListener { openGalleryScreen() }
    }
    
    private fun openGalleryScreen(){
        navigation.addFragment(GalleryScreen.newInstance(data.map { it.photo }, user))
    }
}

class PhotoEntity(val type: Int, val photo: String) : MultiItemEntity {
    companion object {
        const val GRID_1 = 1
        const val GRID_4 = 2
        const val GRID_N = 3
    }
    override fun getItemType() = type

    fun getSpanSize():Int = when(type) {
        GRID_1 -> 1
        else -> 1
    }
}