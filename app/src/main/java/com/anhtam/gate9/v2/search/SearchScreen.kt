package com.anhtam.gate9.v2.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.search.chart.ChartSearchFragment
import com.anhtam.gate9.v2.search.result.ResultSearchFragment
import com.anhtam.gate9.v2.search.temp.TempSearchFragment
import kotlinx.android.synthetic.main.search_screen.*


class SearchScreen : DaggerNavigationFragment(R.layout.search_screen) {

    private val mViewModel: SearchViewModel by viewModels { vmFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvents()
    }

    override fun menuRes() = R.menu.menu_search_avatar_more

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
        edtSearch?.setOnTouchListener { _, _ ->
            show(fragmentTemp)
            false
        }
    }

    private fun show(fragment: Fragment) {
        childFragmentManager.beginTransaction().hide(currentFragment).show(fragment).commit()
        currentFragment = fragment
    }

    private fun search(content: String){
        hideKeyboard()
        if(content.isEmpty()){
            return
        } else {
            mViewModel.mKey.value = content
            show(fragmentResultSearch)
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