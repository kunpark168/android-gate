package com.anhtam.gate9.v2.mxh_gate.video

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.du_lieu.VideoAdapter
import com.anhtam.gate9.v2.chi_tiet_bai_viet.ChiTietBaiVietScreen
import com.anhtam.gate9.v2.mxh_gate.tin_game.MXHGateTinGameScreen
import com.anhtam.gate9.v2.nph_detail.DetailNPHFragment
import com.anhtam.gate9.v2.user_detail.DetailUserFragment

class MXHGateVideoScreen: MXHGateTinGameScreen<VideoAdapter>(2){
    override var mLayoutManager: RecyclerView.LayoutManager? = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter.setOnItemChildClickListener setOnItemClickListener@{ _, childView, position ->
            when (childView.id) {
                R.id.tvName, R.id.imgUser -> {
                    val user = mAdapter.data[position]?.mUser
                    val roleId = user?.mRoleId ?: return@setOnItemClickListener
                    val id = user.mId ?: return@setOnItemClickListener
                    if (roleId != 5){
                        navigation?.addFragment(DetailUserFragment.newInstance(id))
                    } else {
                        navigation?.addFragment(DetailNPHFragment.newInstance(id))
                    }
                }
                R.id.imgTinTuc, R.id.tvTinTuc -> {
                    val id = mAdapter.getItem(position)?.mId ?: return@setOnItemClickListener
                    val article = mAdapter.getItem(position)
                    navigation?.addFragment(ChiTietBaiVietScreen.newInstance(id, article, R.layout.chi_tiet_bai_viet_screen, mTab))
                }
                else -> {
                }
            }
        }
    }
}