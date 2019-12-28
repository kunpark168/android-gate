package com.anhtam.gate9.ui.discussion

import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.SharePageAdapter
import com.anhtam.gate9.ui.base.MvvmFragment
import com.anhtam.gate9.utils.debounceClick
import com.anhtam.gate9.v2.main.home.HomeFragment
import com.anhtam.gate9.v2.newfeed.NewFeedScreen
import kotlinx.android.synthetic.main.activity_shared_discussion.*
import kotlinx.android.synthetic.main.view_footer_discussion.*
import kotlinx.android.synthetic.main.view_nav_controller_discussion.*

abstract class DiscussionActivity : MvvmFragment() {

    abstract val headerFragment: Fragment
    abstract fun fragments(): List<Fragment>
    abstract fun tabInfoHeader(): List<TabInfo>
    abstract fun tabInfoBottom(): List<TabInfo>
    private var mAdapter: SharePageAdapter? = null
    private val navViewModel: NavViewModel by viewModels()
    abstract val fragmentInfo: Fragment
    protected val viewModel: DiscussionViewModel by viewModels { vmFactory }


    override fun getLayoutRes() = R.layout.activity_shared_discussion

    override fun initView() {
        setHasOptionsMenu(true)
        setSupportActionBar(toolbar)
        toolbar?.title = ""
        setUpContainer()
        childFragmentManager.beginTransaction()
                .replace(R.id.headerFrameLayout, headerFragment)
                .commit()
        // Set up view pager
        mAdapter = SharePageAdapter(childFragmentManager, fragments())
        vpContainer?.adapter = mAdapter
        vpContainer?.offscreenPageLimit = 4
        vpContainer?.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                navViewModel.mPositon.value = position
            }

        })
        navControllerView?.setUpTab(tabInfoHeader())
        footerFrameLayout?.setUpTab(tabInfoBottom())
    }

    override fun observer() {
        navViewModel.mPositon.observe(this, Observer {
            if (it == -1){
                vpContainer.visibility = View.GONE
            } else{
                if(vpContainer.visibility == View.GONE) vpContainer.visibility = View.VISIBLE
                vpContainer.currentItem = it
            }
            enableTab(it)
        })
    }

    private fun enableTab(position: Int) {
        tab01.setVisibilityIndicator(position == 0)
        tab02.setVisibilityIndicator(position == 1)
        tab03.setVisibilityIndicator(position == 2)
        tab04.setVisibilityIndicator(position == 3)
        tabInfo.setVisibilityIndicator(position == -1)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_chat_search_more, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun initEvents() {
        tab01?.debounceClick {
            displayContainerFragment()
            navViewModel.mPositon.value = 0
        }
        tab02?.debounceClick {
            displayContainerFragment()
            navViewModel.mPositon.value = 1
        }
        tab03?.debounceClick {
            displayContainerFragment()
            navViewModel.mPositon.value = 2
        }
        tab04?.debounceClick {
            displayContainerFragment()
            navViewModel.mPositon.value = 3
        }

        tabInfo?.debounceClick {
            enableTab(-1)
            displayContainerFragment()
            navViewModel.mPositon.value = -1
            childFragmentManager.beginTransaction().show(fragmentInfo).commit()
            container?.visibility = View.VISIBLE
        }

        tabNewFeed?.debounceClick {
            navigation?.newRootFragment(HomeFragment.newInstance())
        }
        backTextView?.setOnClickListener {
            navigation?.back()
        }
    }

    private fun displayContainerFragment() {
        container?.visibility = View.GONE
        childFragmentManager.beginTransaction().hide(fragmentInfo).commit()
    }

    private fun setUpContainer() {
        childFragmentManager.beginTransaction().add(R.id.container, fragmentInfo).hide(fragmentInfo).commit()
    }
}