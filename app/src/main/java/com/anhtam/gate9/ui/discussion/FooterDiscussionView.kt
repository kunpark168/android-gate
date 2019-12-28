package com.anhtam.gate9.ui.discussion


import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.shared.CustomTabItem
import kotlinx.android.synthetic.main.view_footer_discussion.view.*

class FooterDiscussionView @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_footer_discussion, this)
    }

    fun setUpTab(tabInfo: List<TabInfo>) {
        val tabList = ArrayList<CustomTabItem>()
        tabList.add(tabNewFeed)
        tabList.add(tabMessage)
        tabList.add(tabChat)
        repeat(3) {
            tabList[it].setTabInfo(tabInfo[it])
        }
    }

}