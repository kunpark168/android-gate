package com.anhtam.gate9.v2.game_detail.du_lieu

import androidx.fragment.app.viewModels
import com.anhtam.domain.v2.Article
import com.anhtam.gate9.adapter.v2.du_lieu.DuLieuGameAdapter
import com.anhtam.gate9.v2.game_detail.AbstractTabGameFragment

class DuLieuFragment : AbstractTabGameFragment<Article, DuLieuGameAdapter, DuLieuViewModel>() {
    override fun initViewModel(id: Int) {
        mViewModel.initialize(id)
    }

    companion object {
        fun newInstance() = DuLieuFragment()
    }

    override val mViewModel: DuLieuViewModel by viewModels { vmFactory }
}