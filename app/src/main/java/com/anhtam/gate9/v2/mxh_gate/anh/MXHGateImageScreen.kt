package com.anhtam.gate9.v2.mxh_gate.anh

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.anhtam.gate9.adapter.v2.du_lieu.HinhAnhAdapter
import com.anhtam.gate9.v2.mxh_gate.tin_game.MXHGateTinGameScreen

class MXHGateImageScreen: MXHGateTinGameScreen<HinhAnhAdapter>(4){
    override var mLayoutManager: RecyclerView.LayoutManager? = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
}