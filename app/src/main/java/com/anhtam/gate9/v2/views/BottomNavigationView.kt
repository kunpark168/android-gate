package com.anhtam.gate9.v2.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.lib._color
import com.anhtam.gate9.v2.lib._drawable
import kotlinx.android.synthetic.main.bottom_navigation_view.view.*

class BottomNavigationView @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    init {
        View.inflate(context, R.layout.bottom_navigation_view, this)
        eventClicks()
    }

    private var mListener: (()->Unit)? = null
    private var mListenerGate: (()->Unit)? = null

    private val defaultIcon = mutableListOf(R.drawable.ic_new_feed_grey, R.drawable.ic_follow_grey, R.drawable.ic_person_grey, R.drawable.ic_tab_data)
    private val activeIcon = mutableListOf(R.drawable.ic_new_feed_blue, R.drawable.ic_follow_blue, R.drawable.ic_person_blue, R.drawable.ic_tab_data)
    private val icon by lazy {  mutableListOf(iconNewFeed, iconFollow, iconPerson, iconGate) }
    private val tv by lazy { mutableListOf(tvNewFeed, tvFollow, tvPerson, tvGate) }
    private var previous = 0
    private var mPosition = 0

    private fun eventClicks() {
        /* bottom click */
        llNewFeed?.setOnClickListener { onBottomNavChanged(0) }
        llFollow?.setOnClickListener { onBottomNavChanged(1) }
        llPerson?.setOnClickListener { onPersonalClick() }
        ll9Gate?.setOnClickListener { onGateClick() }
    }

    private fun onBottomNavChanged(position: Int) {
        if (position == previous) return
        changePreviousState(previous)
        changeActiveState(position)
        previous = position
        mPosition = position
    }

    private fun onPersonalClick() {
        mListener?.invoke()
    }

    fun openPersonal(listener: ()->Unit){
        mListener = listener
    }

    private fun onGateClick() {
        mListenerGate?.invoke()
    }

    fun openGate(listener: ()->Unit){
        mListenerGate = listener
    }

    fun syncWithViewPager(viewPager: ViewPager?) {
        if(mPosition == 2 || mPosition == 3) return
        viewPager?.currentItem = mPosition
    }


    private fun changePreviousState(previous: Int) {
        icon[previous]?.setImageDrawable(context?._drawable(defaultIcon[previous]))
        if(previous == 3){
            icon[previous]?.setColorFilter(ContextCompat.getColor(context, R.color.default_color_text), android.graphics.PorterDuff.Mode.MULTIPLY)
        }
        tv[previous]?.setTextColor(context?._color(R.color.default_color_text) ?: return)
    }

    private fun changeActiveState(current: Int) {
        icon[current]?.setImageDrawable(context?._drawable(activeIcon[current]))
        if(current == 3){
            icon[current]?.setColorFilter(ContextCompat.getColor(context, R.color.color_main_blue), android.graphics.PorterDuff.Mode.MULTIPLY)
        }
        tv[current]?.setTextColor(context?._color(R.color.color_main_blue) ?: return)
    }
}