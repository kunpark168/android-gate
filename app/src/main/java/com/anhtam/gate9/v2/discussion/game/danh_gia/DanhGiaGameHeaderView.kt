package com.anhtam.gate9.v2.discussion.game.danh_gia

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.anhtam.gate9.R

class DanhGiaGameHeaderView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyle: Int = 0)
    : LinearLayout(context, attrs, defStyle){

    init {
        View.inflate(context, R.layout.danh_gia_game_header_view, this)
    }
}