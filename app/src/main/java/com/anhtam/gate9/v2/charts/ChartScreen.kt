package com.anhtam.gate9.v2.charts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.chart_screen.*

class ChartScreen : DaggerNavigationFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.chart_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initControls()
    }

    companion object{
        fun newInstance() = ChartScreen()
    }

    private fun initRecyclerView(){
        rvCharts.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvCharts.isNestedScrollingEnabled = false
        rvCharts.adapter = ChartsAdapter()
    }

    private fun initControls(){
        csBack.setOnClickListener { navigation?.back() }
    }
}
