package com.anhtam.gate9.v2.search.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anhtam.gate9.R


class GameSearchFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_search, container, false)
    }

    companion object {
        fun newInstance(): GameSearchFragment = GameSearchFragment()
    }
}
