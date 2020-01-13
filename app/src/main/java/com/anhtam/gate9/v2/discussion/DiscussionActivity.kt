package com.anhtam.gate9.v2.discussion

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.SharePageAdapter
import com.anhtam.gate9.utils.debounceClick
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.main.home.HomeFragment
import kotlinx.android.synthetic.main.activity_shared_discussion.*
import kotlinx.android.synthetic.main.view_footer_discussion.*
import kotlinx.android.synthetic.main.view_nav_controller_discussion.*

abstract class DiscussionActivity : DaggerNavigationFragment() {

    abstract val headerFragment: Fragment
    abstract fun fragments(): List<Fragment>
    abstract fun tabInfoHeader(): List<TabInfo>
    abstract fun tabInfoBottom(): List<TabInfo>
    abstract fun navigateToReport()
    private var mAdapter: SharePageAdapter? = null
    private val navViewModel: NavViewModel by viewModels()
    abstract val fragmentInfo: Fragment
    protected val viewModel: DiscussionViewModel by viewModels { vmFactory }
    abstract fun loadData()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_shared_discussion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        initView()
        initEvents()
        observer()
    }

    override fun statusColor() = android.R.color.transparent
    
    override fun menuRes() = R.menu.menu_chat_search_more

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.icReport -> {
                navigateToReport()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected open fun initView() {
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

    protected open fun observer() {
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

    protected open fun initEvents() {
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
        swipeRefreshLayout?.setOnRefreshListener {
            swipeRefreshLayout?.isRefreshing = false
        }
        tabNewFeed?.debounceClick {
            navigation?.newRootFragment(HomeFragment.newInstance())
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