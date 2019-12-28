package com.anhtam.gate9.utils

import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.anhtam.gate9.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object DataBindingHelper {

    private val avatarOptions = RequestOptions().placeholder(R.drawable.img_avatar_holder)
            .error(R.drawable.img_avatar_holder)

    private val bannerOptions = RequestOptions().placeholder(R.drawable.img_holder_banner)
            .error(R.drawable.img_holder_banner)

    @BindingAdapter("loadImage")
    @JvmStatic
    fun ImageView.loadImage(url: String) {
        Glide.with(this.context.applicationContext).applyDefaultRequestOptions(avatarOptions)
                .load(url)
                .into(this)
    }

    @BindingAdapter("loadBanner")
    @JvmStatic
    fun ImageView.loadBanner(url: String) {
        Glide.with(this.context.applicationContext).applyDefaultRequestOptions(bannerOptions)
                .load(url)
                .into(this)
    }

    @BindingAdapter("setTextFromHtml")
    @JvmStatic
    fun TextView.setTextFromHtml(htmlFormat: String) {
        this.text = Html.fromHtml(htmlFormat)
    }
}