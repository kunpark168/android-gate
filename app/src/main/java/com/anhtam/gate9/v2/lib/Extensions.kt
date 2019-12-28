package com.anhtam.gate9.v2.lib

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

/*
 * Chain two func to get
 * if (boolean == true) then ...
 * else (boolean == false or boolean == null) then ...
 * boolean?.then{}.elseThen{}
 */
fun Boolean?.then(task: ()->Unit) {
    if (this == true) {
        task()
    }
}

/*
 *  Load image with Fragment
 */
fun ImageView.loadImage(fragment: Fragment, url: String?){
    Glide.with(fragment)
            .load(url)
            .into(this)
}

/*
 * Drawable
 */
fun Context._drawable(@DrawableRes id: Int) = ContextCompat.getDrawable(this, id)

fun Context._color(@ColorRes id: Int) = ContextCompat.getColor(this, id)

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}