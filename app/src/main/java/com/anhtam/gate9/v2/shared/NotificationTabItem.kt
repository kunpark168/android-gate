package com.anhtam.gate9.v2.shared

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.anhtam.gate9.R
import kotlinx.android.synthetic.main.custom_tab_item.view.*

class NotificationTabItem @JvmOverloads constructor(
        context: Context?,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr){

    private var text: CharSequence? = null
    private var color: Int? = null
    private var enable = false

    init {
        View.inflate(context, R.layout.notification_tab_item, this)
        attrStyle(attributeSet)
        loadView()
    }

    private fun attrStyle(attrs: AttributeSet?) {
        attrs?.run {
            val a = context.obtainStyledAttributes(attrs, R.styleable.NotificationTabItem)
            text = a.getString(R.styleable.NotificationTabItem_notifyText)
            color = a.getColor(R.styleable.NotificationTabItem_notifyColorIndicator, -1)
            enable = a.getBoolean(R.styleable.NotificationTabItem_notifyEnable, false)
            a.recycle()
        }
    }

    private fun loadView() {
        tvIcon?.text = text
        setVisibilityIndicator(enable)
        val unwrappedColor = color ?: return
        if (unwrappedColor != -1 && enable) {
            tvIcon?.setTextColor(unwrappedColor)
            tabIndicator?.setBackgroundColor(unwrappedColor)
        }
    }

    fun setVisibilityIndicator(enable: Boolean) {
        if (enable) {
            val unwrappedColor = color ?: return
            if (unwrappedColor != -1) {
                tvIcon?.setTextColor(unwrappedColor)
            }
            tabIndicator?.visibility = View.VISIBLE
        } else {
            tabIndicator?.visibility = View.GONE
            tvIcon?.setTextColor(ContextCompat.getColor(context, R.color.black))
        }
    }
}