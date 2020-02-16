package com.anhtam.gate9.v2.mxh_gate.cam_nang

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhtam.gate9.adapter.v2.ArticleAdapter
import com.anhtam.gate9.v2.mxh_gate.tin_game.MXHGateTinGameScreen

class MXHGateCamNangScreen(tab: Int): MXHGateTinGameScreen<ArticleAdapter>(tab){
    override var mLayoutManager: RecyclerView.LayoutManager? = LinearLayoutManager(context)
}