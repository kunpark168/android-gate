package com.anhtam.gate9.navigation.toobar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.LinearLayout.VERTICAL
import com.anhtam.gate9.R

class NavigationLayoutFactory(
        private val includeToolbar: Boolean,
        private val origin: LayoutFactory
) : LayoutFactory {

    override fun produceLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        val parent = LinearLayout(inflater.context)
        parent.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        parent.orientation = VERTICAL
        val child = origin.produceLayout(inflater, parent )
        val childParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)

        if (includeToolbar) {
            inflater.inflate(R.layout.navigation_toolbar, parent)
        }
        parent.addView(child, childParams)
        return parent
    }

}