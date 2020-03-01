package com.anhtam.gate9.v2.mxh_gate.video

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.du_lieu.VideoAdapter
import com.anhtam.gate9.v2.chi_tiet_bai_viet.ChiTietBaiVietScreen
import com.anhtam.gate9.v2.mxh_gate.tin_game.MXHGateTinGameScreen

class MXHGateVideoScreen: MXHGateTinGameScreen<VideoAdapter>(2){
    override var mLayoutManager: RecyclerView.LayoutManager? = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter.setOnItemClickListener { _, _, position ->
            val article = mAdapter.getItem(position)
            val id = article?.mId ?: return@setOnItemClickListener
            navigation?.addFragment(ChiTietBaiVietScreen.newInstance(id, article, R.layout.chi_tiet_video_screen, mTab))
        }
    }
}