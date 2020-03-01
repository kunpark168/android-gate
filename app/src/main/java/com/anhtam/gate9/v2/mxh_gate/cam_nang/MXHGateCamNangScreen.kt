package com.anhtam.gate9.v2.mxh_gate.cam_nang

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.ArticleAdapter
import com.anhtam.gate9.v2.chi_tiet_bai_viet.ChiTietBaiVietScreen
import com.anhtam.gate9.v2.mxh_gate.tin_game.MXHGateTinGameScreen

class MXHGateCamNangScreen(tab: Int): MXHGateTinGameScreen<ArticleAdapter>(tab){
    override var mLayoutManager: RecyclerView.LayoutManager? = LinearLayoutManager(context)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter.setOnItemClickListener { _, _, position ->
            val id = mAdapter.getItem(position)?.mId ?: return@setOnItemClickListener
            val article = mAdapter.getItem(position)
            navigation?.addFragment(ChiTietBaiVietScreen.newInstance(id, article, R.layout.chi_tiet_bai_viet_screen, mTab))
        }
    }
}