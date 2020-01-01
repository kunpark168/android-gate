package com.anhtam.gate9.v2.search.result

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.anhtam.gate9.R

class PageFooter @JvmOverloads constructor(
        context: Context?,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    init {
        View.inflate(context, R.layout.footer_page, this)
    }
}