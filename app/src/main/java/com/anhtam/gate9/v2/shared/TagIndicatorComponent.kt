package com.anhtam.gate9.v2.shared

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.anhtam.gate9.R
import kotlinx.android.synthetic.main.share_tag_indicator_component.view.*

class TagIndicatorComponent @JvmOverloads constructor(
        context: Context?,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    private var text: CharSequence? = null
    private var enable = false

    init {
        View.inflate(context, R.layout.share_tag_indicator_component, this)
        attrStyle(attributeSet)
        loadView()
    }

    private fun attrStyle(attrs: AttributeSet?) {
        attrs?.run {
            val a = context.obtainStyledAttributes(attrs, R.styleable.TagIndicatorComponent)
            text = a.getString(R.styleable.TagIndicatorComponent_tag_text)
            enable = a.getBoolean(R.styleable.TagIndicatorComponent_tag_enable, false)
            a.recycle()
        }
    }

    private fun loadView() {
        tvLabel?.text = text
    }

}