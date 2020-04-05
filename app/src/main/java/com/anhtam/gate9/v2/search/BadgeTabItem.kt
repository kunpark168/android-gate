package com.anhtam.gate9.components.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.anhtam.gate9.R
import kotlinx.android.synthetic.main.badge_tab_item.view.*

class BadgeTabItem @JvmOverloads constructor(
        context: Context?,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr){

    private var text: CharSequence? = null
    private var enable = false

    init {
        View.inflate(context, R.layout.badge_tab_item, this)
        attrStyle(attributeSet)
        loadView()
    }

    private fun attrStyle(attrs: AttributeSet?) {
        attrs?.run {
            val a = context.obtainStyledAttributes(attrs, R.styleable.BadgeTabItem)
            text = a.getString(R.styleable.BadgeTabItem_badgeText)
            enable = a.getBoolean(R.styleable.BadgeTabItem_badgeEnable, false)
            a.recycle()
        }
    }

    private fun loadView() {
        tvIcon?.text = text
        enableState(enable)
    }

    fun enableState(enable: Boolean) {
        val unwrappedColorIndicator = ContextCompat.getColor(context, R.color.tab_blue)
        val unwrappedColorNormal = ContextCompat.getColor(context, R.color.colorGray)
        if (enable) {
            tvIcon?.setTextColor(unwrappedColorIndicator)
            tvBadge?.setTextColor(unwrappedColorIndicator)
            tabIndicator?.visibility = View.VISIBLE
        } else {
            tabIndicator?.visibility = View.GONE
            tvIcon?.setTextColor(unwrappedColorNormal)
            tvBadge?.setTextColor(unwrappedColorNormal)
        }
    }
}