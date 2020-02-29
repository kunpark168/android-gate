package com.anhtam.gate9.v2.game_detail.danh_gia

import androidx.fragment.app.viewModels
import com.anhtam.gate9.adapter.v2.RatingGameAdapter
import com.anhtam.gate9.v2.game_detail.AbstractTabGameFragment
import com.anhtam.gate9.vo.Rating

class DanhGiaGameFragment : AbstractTabGameFragment<Rating, RatingGameAdapter, DanhGiaViewModel>(){

    override fun initViewModel(id: Int) {
        mViewModel.initialize(id)
    }

    override val mViewModel: DanhGiaViewModel by viewModels { vmFactory }
    private lateinit var mHeaderView: DanhGiaGameHeaderView

    override fun setUpAdapter() {
        super.setUpAdapter()
        mHeaderView = DanhGiaGameHeaderView(context)
        mAdapter.addHeaderView(mHeaderView)
    }
    companion object{
        fun newInstance() = DanhGiaGameFragment()
    }
}