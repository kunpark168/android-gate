package com.anhtam.gate9.v2.discussion.common.game

import androidx.fragment.app.viewModels
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.domain.v2.wrap.WrapGame
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.GameQuickAdapter
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionFragment


class GGameFragment: CommonDiscussionFragment<Game, GameQuickAdapter, GGameViewModel>() {

    override val tabTitle: List<Int>
        get() = arrayListOf(R.string.open_category, R.string.beta_category, R.string.coming_category, R.string.closed_category)
    override val mViewModel: GGameViewModel by viewModels { vmFactory }
    override val colorTextTab: Int = R.color.colorTabGame

    companion object {
        fun newInstance() = GGameFragment()
    }
}
