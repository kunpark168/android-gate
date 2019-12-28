package com.anhtam.gate9.v2.shared

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.anhtam.gate9.R
import com.anhtam.gate9.ui.discussion.TabInfo
import kotlinx.android.synthetic.main.custom_tab_item.view.*

class CustomTabItem @JvmOverloads constructor(
        context: Context?,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    private var text: CharSequence? = null
    private var icon: Drawable? = null
    private var backgroundIcon: Drawable? = null
    private var color: Int? = null
    private var enable = false
    private var colorTint: Int? = null
    private var margin: Float? = null

    init {
        View.inflate(context, R.layout.custom_tab_item, this)
        attrStyle(attributeSet)
        loadView()
    }
    private fun attrStyle(attrs: AttributeSet?) {
        attrs?.run {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CustomTabItem)
            text = a.getString(R.styleable.CustomTabItem_text)
            icon = a.getDrawable(R.styleable.CustomTabItem_icon)
            backgroundIcon = a.getDrawable(R.styleable.CustomTabItem_backgroundIcon)
            color = a.getColor(R.styleable.CustomTabItem_colorIndicator, -1)
            colorTint = a.getColor(R.styleable.CustomTabItem_colorTint, -1)
            enable = a.getBoolean(R.styleable.CustomTabItem_enable, false)
            margin = a.getDimension(R.styleable.CustomTabItem_margin, 0f)
            a.recycle()
        }
    }

    private fun loadView() {
        tvIcon?.text = text
        imgIcon?.setImageDrawable(icon)
        if (enable) {
            tabIndicator?.visibility = View.VISIBLE
        } else {
            tabIndicator?.visibility = View.GONE
        }
        imgIcon?.background = backgroundIcon
        val unwrappedColor = color ?: return
        if (unwrappedColor != -1) {
            tvIcon?.setTextColor(unwrappedColor)
            tabIndicator?.setBackgroundColor(unwrappedColor)
        }
        val unwrappedColorTint = colorTint ?: return
        if (unwrappedColorTint != -1) {
            imgIcon.setColorFilter(unwrappedColorTint)
        }

        margin?.let {

            val params = tabIndicator.layoutParams as? MarginLayoutParams
            params?.setMargins(it.toInt(), 0, it.toInt(), 0)
        }
    }

    fun setVisibilityIndicator(enable: Boolean) {
        if (enable) {
            tabIndicator?.visibility = View.VISIBLE
        } else {
            tabIndicator?.visibility = View.GONE
        }
    }

    fun setTabInfo(tabInfo: TabInfo) {
        val color = ContextCompat.getColor(context,tabInfo.color)
        tvIcon?.text = context?.getString(tabInfo.title)
        tvIcon?.setTextColor(color)
        tabIndicator?.setBackgroundColor(color)
        imgIcon?.setImageDrawable(ContextCompat.getDrawable(context, tabInfo.icon))
    }
}