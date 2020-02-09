package com.anhtam.gate9.v2.newfeed

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.anhtam.gate9.R
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.v2.search.SearchScreen
import kotlinx.android.synthetic.main.search_component.view.*

class SearchHeaderView(context: Context?, attr: AttributeSet? = null, defStyle: Int = 0) : LinearLayout(context, attr, defStyle) {

    private var mNavigation: Navigation? = null

    init {
        View.inflate(context, R.layout.search_component, this)
        initEvents()
    }

    fun initialize(navigation: Navigation?){
        mNavigation = navigation
    }

    private fun initEvents(){
        imgSearch?.setOnClickListener{
            mNavigation?.addFragment(SearchScreen.newInstance())
        }
        tvSearch?.setOnClickListener {
            mNavigation?.addFragment(SearchScreen.newInstance())
        }
    }
}