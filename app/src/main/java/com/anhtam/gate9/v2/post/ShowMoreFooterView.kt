package com.anhtam.gate9.v2.post

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.anhtam.gate9.R
import kotlinx.android.synthetic.main.show_more_footer_view.view.*

class ShowMoreFooterView @JvmOverloads constructor(context: Context?, attr: AttributeSet? = null,
                                                   defStyle: Int = 0) : LinearLayout(context, attr, defStyle) {
    init {
        View.inflate(context, R.layout.show_more_footer_view, this)
        // set event for view
        tvShowMore?.setOnClickListener { mListener?.invoke() }
    }

    private var mListener: (()->Unit)? = null

    fun setText(text: String, behavior: ()->Unit) {
        tvShowMore?.text = text
        mListener = behavior
    }
}