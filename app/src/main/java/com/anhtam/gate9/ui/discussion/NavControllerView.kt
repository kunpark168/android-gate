package com.anhtam.gate9.ui.discussion

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.shared.CustomTabItem
import kotlinx.android.synthetic.main.view_nav_controller_discussion.view.*

class NavControllerView @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_nav_controller_discussion, this)
    }

    fun setUpTab(tabInfo: List<TabInfo>) {
        val tabList = ArrayList<CustomTabItem>()
        tabList.add(tab01)
        tabList.add(tab02)
        tabList.add(tab03)
        tabList.add(tab04)
        repeat(4) {
            tabList[it].setTabInfo(tabInfo[it])
        }
    }

}