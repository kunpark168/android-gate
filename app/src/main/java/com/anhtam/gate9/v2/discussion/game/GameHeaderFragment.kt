package com.anhtam.gate9.v2.discussion.game

import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment

class GameHeaderFragment : DaggerNavigationFragment(R.layout.header_game_discussion) {

    companion object {
        fun newInstance() : GameHeaderFragment {
            return GameHeaderFragment()
        }
    }
}