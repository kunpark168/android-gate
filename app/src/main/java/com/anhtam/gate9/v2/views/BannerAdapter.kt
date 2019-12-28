package com.anhtam.gate9.v2.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.anhtam.gate9.R
import com.bumptech.glide.Glide

// For fragment we usually implement FragmentStatePagerAdapter whereas for Views we will be implementing PagerAdapter
class BannerAdapter(private val context: Context, private val mImages: ArrayList<String>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.view_banner_item, null)
        val imageView = view.findViewById<ImageView>(R.id.image)
        Glide.with(view).load(mImages[position]).into(imageView!!)
        container.addView(view)
        return view
    }
    override fun isViewFromObject(view: View, `object`: Any) = `object` == view

    override fun getCount() = mImages.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun setNewData(images: List<String>) {
        mImages.addAll(images)
        notifyDataSetChanged()
    }
}