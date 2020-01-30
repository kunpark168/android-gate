package com.anhtam.gate9.v2.discussion.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.discussion.DiscussionViewModel
import com.anhtam.gate9.utils.getColorCompat
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.shared_discussion_layout.*
import of.bum.network.helper.Resource

abstract class CommonDiscussionFragment : DaggerNavigationFragment(R.layout.shared_discussion_layout) {

    protected var mUser: User? = null

    private var mDiscussionViewModel: DiscussionViewModel? = null

    abstract val colorTextTab: Int
    abstract fun configTabLayout()
    abstract fun updateTabLayout()
    abstract fun loadData()
    abstract fun initEvents()
    open fun inflateLayout() : Int? {
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        initView()
        initEvents()
        observer()
    }

    protected open fun initView() {
        activity?.let {
            mDiscussionViewModel = ViewModelProviders.of(it, vmFactory).get(DiscussionViewModel::class.java)
        }
        configTabLayout()
        val unwrappedContext = context ?: return
        context?.let {
            val dividerItemDecoration = DividerItemDecoration(it, LinearLayout.VERTICAL)
            val drawableDivider = ContextCompat.getDrawable(it, R.drawable.divider_item_decorator) ?: return
            dividerItemDecoration.setDrawable(drawableDivider)
            rvShareDiscussion?.addItemDecoration(dividerItemDecoration)

        }
        tabLayout.setTabTextColors(unwrappedContext.getColorCompat(R.color.defaultGrayColor), unwrappedContext.getColorCompat(colorTextTab))
    }

    protected open fun observer() {
        mDiscussionViewModel?.mUser?.observe(this, Observer {
            when(it) {
                is Resource.Success -> {
                    hideProgress()
                    mUser = it.data
                    updateTabLayout()
                }
                else -> {

                }
            }
        })
    }
}