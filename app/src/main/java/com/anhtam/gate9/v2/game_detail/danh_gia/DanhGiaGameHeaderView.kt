package com.anhtam.gate9.v2.game_detail.danh_gia

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.anhtam.gate9.R
import kotlinx.android.synthetic.main.danh_gia_game_header_view.view.*

class DanhGiaGameHeaderView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyle: Int = 0)
    : LinearLayout(context, attrs, defStyle){

    init {
        View.inflate(context, R.layout.danh_gia_game_header_view, this)
        ratingComponent?.onRatingButtonClicked { rating, content ->
            mListener?.invoke(rating, content)
        }
    }

    private var mListener: ((Float, String?)->Unit)? = null

    fun navigateToRatingFragment(listener: (Float, String?)-> Unit) {
        mListener = listener
    }


}