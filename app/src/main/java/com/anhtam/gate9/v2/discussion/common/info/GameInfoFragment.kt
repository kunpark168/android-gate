package com.anhtam.gate9.v2.discussion.common.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.ChiTietGameViewModel
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.fragment_info_game.*
import of.bum.network.helper.Resource

class GameInfoFragment: DaggerNavigationFragment(R.layout.fragment_info_game) {


    private var mGame: Game? = null

    private val mViewModel: ChiTietGameViewModel by viewModels ({requireParentFragment()}, { vmFactory })


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
    }

    private fun init() {
        mViewModel._bottomStatus.value = false
        observer()
    }

    private fun observer() {
        mViewModel.mGame.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success, is Resource.Error ->{
                    mGame = it.data
                    updateUI()
                }
                else -> {}
            }
        })
    }

    private fun updateUI() {
        tvNameGame?.text = infoOrDefault(mGame?.name)
        tvTypeGame?.text = infoOrDefault(mGame?.gameType?.name)
        tvNPH?.text = infoOrDefault("-")
        tvCountry?.text = infoOrDefault(mGame?.country)
        tvStatus?.text = infoOrDefault("")

        tvPublishDate?.text = infoOrDefault(mGame?.createdDate)
        tvIntro?.text = infoOrDefault(mGame?.description)

        tvHome?.text = infoOrDefault(mGame?.homepage)
        tvFanpage?.text = infoOrDefault(mGame?.fanPage)
        tvEmail?.text = infoOrDefault(mGame?.email)
        tvGroup?.text = infoOrDefault(mGame?.group)
        tvPhone?.text = infoOrDefault(mGame?.phone)
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