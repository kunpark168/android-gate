package com.anhtam.gate9.v2.game_detail.du_lieu

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.anhtam.gate9.R

class GiftCodeHeaderView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyle: Int = 0): LinearLayout(context, attrs, defStyle) {

    init {
        View.inflate(context, R.layout.gift_code_header_view, this)
    }
}