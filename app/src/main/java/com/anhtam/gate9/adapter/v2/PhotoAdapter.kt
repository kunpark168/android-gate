package com.anhtam.gate9.adapter.v2

import com.anhtam.domain.v2.protocol.User
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

    companion object{
        private const val DEFAULT_MORE = 4
        private const val DEFAULT_MORE_COMMENT = 2

    }
    
    lateinit var user: User
    private var default = DEFAULT_MORE
    
    init {
        addItemType(PhotoEntity.GRID_1, R.layout.photo_1_item_layout)
        addItemType(PhotoEntity.GRID_4, R.layout.photo_4_item_layout)
        addItemType(PhotoEntity.GRID_N, R.layout.photo_n_item_layout)
    }

    /*
     * Return span size
     */
    fun setPhoto(photo: String, isComment: Boolean = false): Int {
        if (isComment) default = DEFAULT_MORE_COMMENT
        val photos = photo.replace("\\","").replace("\"", "").split(',').map { it.trim() }
        if (photos[0].isEmpty()) return 1

        val entities = photos.map {
            PhotoEntity(
                    when(photos.size) {
                        1 -> PhotoEntity.GRID_1
                        in 2..4 -> PhotoEntity.GRID_4
                        else -> PhotoEntity.GRID_N
                    }, it
            )
        }
        setSpanSizeLookup{_, pos -> data[pos].getSpanSize()}
        if (entities.size > default) {
            val morePhotoList = arrayListOf<PhotoEntity>()
            for (index in 0..default) {
                if (index == default - 1) {
                    morePhotoList.add(PhotoEntity(PhotoEntity.GRID_N, entities[index].photo))
                } else {
                    morePhotoList.add(PhotoEntity(PhotoEntity.GRID_4, entities[index].photo))
                }
            }
            setNewData(morePhotoList)
        } else {
            setNewData(entities)
        }
        return when(entities.size){
            1 -> 1
            else -> 2
        }
    }

    override fun getItemCount() = if(data.size > default) default else data.size

    override fun convert(helper: BaseViewHolder?, item: PhotoEntity?) {
        val photo = item?.photo ?: return
        val view = helper?.itemView ?: return
        when(itemCount){
            1 -> {
                Glide.with(mContext)
                        .load(photo.toImage())
                        .apply(bannerOptions)
                        .centerCrop()
                        .into(view.imgPhoto)
            }
            else -> {
                Glide.with(mContext)
                        .load(photo.toImage())
                        .apply(bannerOptions)
                        .fitCenter()
                        .into(view.imgPhoto)
            }
        }
        if (item.type == PhotoEntity.GRID_N) {
            val more = data.size - default
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