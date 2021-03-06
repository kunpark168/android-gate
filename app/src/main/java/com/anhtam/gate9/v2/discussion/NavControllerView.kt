package com.anhtam.gate9.v2.discussion

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.anhtam.gate9.R
import com.anhtam.gate9.restful.BackgroundTasks
import com.anhtam.gate9.v2.shared.CustomTabItem
import kotlinx.android.synthetic.main.view_nav_controller_discussion.view.*

class NavControllerView @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    private var mIsFollow: Boolean = false
    private var mUserId: Int = 0
    private var mRoleId: Int = 0

    init {
        View.inflate(context, R.layout.view_nav_controller_discussion, this)
        initEvents()
    }

    private fun initEvents(){
        tabFollow?.setOnClickListener {
            BackgroundTasks.postUserFollow(mUserId, mRoleId)
            mIsFollow = if (mIsFollow){
                // un follow
                unFollow()
                false
            } else {
                // follow
                follow()
                true
            }
        }
    }

    fun initialize(isFollowing: Boolean, userId: Int, roleId: Int){
        mIsFollow = isFollowing
        mUserId = userId
        mRoleId = roleId
        when(mIsFollow){
            true -> follow()
            false -> unFollow()
        }
    }

    private fun follow(){
        tabFollow?.changeText(context.getString(R.string.following))
        tabFollow?.changeColor(R.color.color_main_blue)
    }

    private fun unFollow(){
        tabFollow?.changeText(context.getString(R.string.follow))
        tabFollow?.changeColor(R.color.color_main_orange)
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