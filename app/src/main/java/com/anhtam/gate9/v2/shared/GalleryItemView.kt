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

    var mIsSelected: Boolean = false

    init {
        View.inflate(context, R.layout.gallery_item_view, this)
        initEvents()
    }

    fun setImage(path: String){
        val bitmap = BitmapFactory.decodeFile(path)
        Glide.with(context)
                .load(bitmap)
                .apply(RequestOptions().placeholder(R.color.white).error(R.color.white))
                .centerCrop()
                .into(imgPhoto)
    }

    private fun initEvents(){
        rootView?.setOnClickListener {
            mIsSelected = !mIsSelected
            selectedFrameLayout?.visibility = if(mIsSelected) View.VISIBLE else View.GONE
        }
    }
}