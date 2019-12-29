package com.anhtam.gate9.ui.search

import android.content.Context
import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.anhtam.gate9.R
import com.anhtam.gate9.ui.base.MvvmActivity
import com.anhtam.gate9.ui.search.chart.ChartSearchFragment
import com.anhtam.gate9.ui.search.result.ResultSearchFragment
import com.anhtam.gate9.ui.search.temp.TempSearchFragment
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : MvvmActivity() {

    override fun initEvents() {
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
        hideKeyBoard()
        if(content.isEmpty()){
            return
        } else {
            supportFragmentManager.beginTransaction().hide(currentFragment).show(fragmentResultSearch).commit()
            currentFragment = fragmentTemp
        }
    }

    private val fragmentTemp : TempSearchFragment by lazy { TempSearchFragment.newInstance() }
    private val fragmentChartSearch: ChartSearchFragment by lazy { ChartSearchFragment.newInstance() }
    private val fragmentResultSearch: ResultSearchFragment by lazy { ResultSearchFragment.newInstance() }
    private var currentFragment: Fragment = fragmentChartSearch

    override fun getLayoutId() = R.layout.activity_search

    override fun loadData() {

    }

    override fun initView() {
        supportFragmentManager.beginTransaction().add(R.id.container, fragmentTemp).hide(fragmentTemp).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, fragmentResultSearch).hide(fragmentResultSearch).commit()
        supportFragmentManager.beginTransaction().add(R.id.container, fragmentChartSearch).commit()

        //
        backFrameLayout?.setOnClickListener {
            onBackPressed()
        }
    }


    override fun observer() {
    }


    companion object {
        fun start(context: Context?) {
            val intent = Intent(context, SearchActivity::class.java)
            context?.startActivity(intent)
        }
    }

}