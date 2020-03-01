package com.anhtam.gate9.v2.search.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.anhtam.gate9.R
import com.anhtam.gate9.components.custom.BadgeTabItem
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.search.SearchViewModel
import kotlinx.android.synthetic.main.fragment_result_search.*

/**
 * A simple [Fragment] subclass.
 */
class ResultSearchFragment : DaggerNavigationFragment(R.layout.fragment_result_search) {

    private val viewModel: SearchViewModel by viewModels { vmFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvents()
        observer()
    }

    private fun initView() {
        setUpRecyclerView()
    }

    private fun observer() {
//        viewModel.games.observe(this, Observer {
//        })
    }

    private fun initEvents() {
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
