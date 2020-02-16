package com.anhtam.gate9.v2.mxh_gate.video

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.anhtam.gate9.adapter.v2.du_lieu.VideoAdapter
import com.anhtam.gate9.v2.mxh_gate.tin_game.MXHGateTinGameScreen

class MXHGateVideoScreen: MXHGateTinGameScreen<VideoAdapter>(3){
    override var mLayoutManager: RecyclerView.LayoutManager? = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
}