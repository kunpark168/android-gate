package com.anhtam.gate9.v2.search

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.search.chart.ChartSearchFragment
import com.anhtam.gate9.v2.search.result.ResultSearchFragment
import com.anhtam.gate9.v2.search.temp.TempSearchFragment
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.search_screen.*


class SearchScreen : DaggerNavigationFragment(R.layout.search_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvents()
    }

    override fun menuRes() = R.menu.menu_chat_search_more

    private fun initEvents() {
        imgSearch?.setOnClickListener {
           search(edtSearch.text.toString())
        }

        edtSearch?.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                search(edtSearch.text.toString())
                true
            } else {
                false
            }
        }

    }

    private fun search(content: String){
        hideKeyboard()
        if(content.isEmpty()){
            return
        } else {
            childFragmentManager.beginTransaction().hide(currentFragment).show(fragmentResultSearch).commit()
            currentFragment = fragmentTemp
        }
    }

    private val fragmentTemp : TempSearchFragment by lazy { TempSearchFragment.newInstance() }
    private val fragmentChartSearch: ChartSearchFragment by lazy { ChartSearchFragment.newInstance() }
    private val fragmentResultSearch: ResultSearchFragment by lazy { ResultSearchFragment.newInstance() }
    private var currentFragment: Fragment = fragmentChartSearch

    private fun initView() {
        childFragmentManager.beginTransaction().add(R.id.container, fragmentTemp).hide(fragmentTemp).commit()
        childFragmentManager.beginTransaction().add(R.id.container, fragmentResultSearch).hide(fragmentResultSearch).commit()
        childFragmentManager.beginTransaction().add(R.id.container, fragmentChartSearch).commit()
    }

    companion object {
        fun newInstance() = SearchScreen()
    }

}