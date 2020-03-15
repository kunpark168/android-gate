package com.anhtam.gate9.v2.mxh_gate.cam_nang

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.ArticleAdapter
import com.anhtam.gate9.v2.chi_tiet_bai_viet.ChiTietBaiVietScreen
import com.anhtam.gate9.v2.chi_tiet_bai_viet.tin_game.ChiTietBaiVietTinGameScreen
import com.anhtam.gate9.v2.game_detail.DetailGameFragment
import com.anhtam.gate9.v2.mxh_gate.tin_game.MXHGateTinGameScreen

class MXHGateCamNangScreen(tab: Int) : MXHGateTinGameScreen<ArticleAdapter>(tab) {
    override var mLayoutManager: RecyclerView.LayoutManager? = LinearLayoutManager(context)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter.setOnItemChildClickListener setOnItemClickListener@{ _, childView, position ->
            when (childView.id) {
                R.id.tvTitleGame, R.id.imgGame -> {
                    val id = mAdapter.data[position]?.mGame?.gameId ?: return@setOnItemClickListener
                    navigation?.addFragment(DetailGameFragment.newInstance(id))
                }
                R.id.imgTinTuc, R.id.tvTinTuc -> {
                    val id = mAdapter.getItem(position)?.mId ?: return@setOnItemClickListener
                    val article = mAdapter.getItem(position)
                    navigation?.addFragment(ChiTietBaiVietTinGameScreen.newInstance(id, article, mTab))
                }
                else -> {
                }
            }
        }
    }
}