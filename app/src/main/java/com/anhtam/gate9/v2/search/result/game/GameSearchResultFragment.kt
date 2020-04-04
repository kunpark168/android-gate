package com.anhtam.gate9.v2.search.result.game

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.gate9.adapter.GameQuickAdapter
import com.anhtam.gate9.v2.search.AbstractTabSearchFragment

class GameSearchResultFragment: AbstractTabSearchFragment<Game, GameQuickAdapter, GameSearchViewModel>(){
    override val mViewModel: GameSearchViewModel by viewModels { vmFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.mKey = "Kiem hiep"
    }
}