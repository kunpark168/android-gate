package com.anhtam.gate9.ui.search.result

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anhtam.gate9.components.custom.BadgeTabItem
import com.anhtam.gate9.R
import com.anhtam.gate9.ui.base.MvvmFragment
import com.anhtam.gate9.ui.search.SearchViewModel
import kotlinx.android.synthetic.main.fragment_result_search.*

/**
 * A simple [Fragment] subclass.
 */
class ResultSearchFragment : MvvmFragment() {

    private val viewModel: SearchViewModel by viewModels { vmFactory }

    override fun getLayoutRes() = R.layout.fragment_result_search

    override fun loadData() {

    }

    override fun initView() {
        setUpRecyclerView()
    }

    override fun observer() {
        viewModel.games.observe(this, Observer {
        })
    }

    override fun initEvents() {
        tabGame?.setOnClickListener {
            enableTab(tabGame)
        }
        tabMember?.setOnClickListener {
            enableTab(tabMember)
        }
        tabData?.setOnClickListener {
            enableTab(tabData)
        }
    }
    private fun enableTab(tab: BadgeTabItem) {
        tabGame?.enableState(tab == tabGame)
        tabMember?.enableState(tab == tabMember)
        tabData?.enableState(tab == tabData)
    }

    private fun setUpRecyclerView() {
    }

    companion object {
        fun newInstance(): ResultSearchFragment {
            return ResultSearchFragment()
        }
    }
}
