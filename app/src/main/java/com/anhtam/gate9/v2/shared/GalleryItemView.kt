package com.anhtam.gate9.v2.shared

import android.content.Context
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.anhtam.gate9.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.gallery_item_view.view.*

class GalleryItemView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyle: Int = 0) : ConstraintLayout(context, attrs, defStyle){

    init {
        View.inflate(context, R.layout.gallery_item_view, this)
    }

    fun setImage(path: String){
        val bitmap = BitmapFactory.decodeFile(path)
        Glide.with(context)
                .load(bitmap)
                .apply(RequestOptions().placeholder(R.color.white).error(R.color.white))
                .centerCrop()
                .into(imgPhoto)
    }

    fun onSelected(isSelect: Boolean){
        selectedFrameLayout?.visibility = if(isSelect) View.VISIBLE else View.GONE
    }
}