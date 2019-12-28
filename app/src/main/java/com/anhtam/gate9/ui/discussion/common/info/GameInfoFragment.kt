package com.anhtam.gate9.ui.discussion.common.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anhtam.domain.Game
import com.anhtam.domain.User
import com.anhtam.gate9.R
import com.anhtam.gate9.ui.base.BaseFragment
import com.anhtam.gate9.ui.discussion.DiscussionViewModel
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.fragment_info_game.*
import kotlinx.android.synthetic.main.fragment_info_game.tvEmail
import kotlinx.android.synthetic.main.fragment_info_game.tvHome
import kotlinx.android.synthetic.main.fragment_info_game.tvIntro
import of.bum.network.helper.Resource

class GameInfoFragment: DaggerNavigationFragment() {


    private var mGame: Game? = null

    private var mDiscussionViewModel: DiscussionViewModel? = null
    private val viewModel: DiscussionViewModel by viewModels { vmFactory }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.let {
            mDiscussionViewModel = ViewModelProviders.of(it, vmFactory).get(DiscussionViewModel::class.java)
        }
        init()
    }

    private fun init() {
        mDiscussionViewModel?._bottomStatus?.value = false
        observer()
    }

    private fun observer() {
        mDiscussionViewModel?.mGame?.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    hideProgress()
                    mGame = it.data
                    bindInfo()
                }
                else -> {

                }
            }
        })
    }

    private fun bindInfo() {
        tvNameGame?.text = infoOrDefault(mGame?.name)
        tvTypeGame?.text = infoOrDefault(mGame?.types?.get(0)?.name)
        tvNPH?.text = infoOrDefault(mGame?.publisher?.name)
        tvCountry?.text = infoOrDefault(mGame?.country?.name)
        tvStatus?.text = infoOrDefault(mGame?.status)

        tvPublishDate?.text = "-"
        tvIntro?.text = infoOrDefault(mGame?.content)

        tvHome?.text = infoOrDefault(mGame?.contactInfo?.homepage)
        tvFanpage?.text = infoOrDefault(mGame?.contactInfo?.fanpage)
        tvEmail?.text = infoOrDefault(mGame?.contactInfo?.email)
        tvGroup?.text = infoOrDefault(mGame?.contactInfo?.group)
        tvPhone?.text = infoOrDefault(mGame?.contactInfo?.tel)
    }

    companion object {
        fun newInstance(): GameInfoFragment {
            return GameInfoFragment()
        }
    }

    private fun infoOrDefault(text: String?) : String {
        if (text.isNullOrBlank()) {
            return "-"
        }
        return text
    }
}