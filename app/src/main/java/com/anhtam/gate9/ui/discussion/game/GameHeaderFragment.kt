package com.anhtam.gate9.ui.discussion.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment

class GameHeaderFragment : DaggerNavigationFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.header_game_discussion, container, false)
    }

    companion object {
        fun newInstance() : GameHeaderFragment {
            return GameHeaderFragment()
        }
    }
}