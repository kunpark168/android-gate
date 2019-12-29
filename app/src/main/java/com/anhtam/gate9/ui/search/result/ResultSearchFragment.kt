package com.anhtam.gate9.ui.search.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anhtam.gate9.components.custom.BadgeTabItem
import com.anhtam.gate9.R
import com.anhtam.gate9.ui.search.SearchViewModel
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.fragment_result_search.*

/**
 * A simple [Fragment] subclass.
 */
class ResultSearchFragment : DaggerNavigationFragment() {

    private val viewModel: SearchViewModel by viewModels { vmFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_result_search, container, false)
    }

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
        viewModel.games.observe(this, Observer {
        })
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
