package com.anhtam.gate9.v2.discussion.game

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.ChiTietGameViewModel
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.header_game_discussion.*
import of.bum.network.helper.Resource
import javax.inject.Inject
import javax.inject.Named

class GameHeaderFragment : DaggerNavigationFragment(R.layout.header_game_discussion) {

    private val mViewModel: ChiTietGameViewModel by viewModels({requireParentFragment()}, {vmFactory})
    private var mGame: Game? = null
    @Inject @field:Named("banner") lateinit var bannerOption: RequestOptions
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
        observerGame()
    }

    private fun initEvent(){
        iconBXH?.setOnClickListener {
//            navigation?.addFragment(BXH)
        }
    }

    private fun observerGame(){
        mViewModel.mGame.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success, is Resource.Error -> {
                    mGame = it.data
                    updateUI()
                }
                else -> {}
            }
        })
    }

    private fun updateUI(){
        val unwrapContext = context ?: return
        Glide.with(unwrapContext)
                .load(mGame?.imgCover?.toImage())
                .apply(bannerOption)
                .into(bannerImageView)

        Glide.with(unwrapContext)
                .load(mGame?.avatar?.toImage())
                .apply(bannerOption)
                .into(avatarImageView)
        tvTitle?.text = mGame?.name
        tvFollowAndPost?.text = Phrase.from(getString(R.string.follower_amount_and_post_amount))
                .put("follower", mGame?.follower?.toString() ?: "0")
                .put("post", mGame?.post?.toString() ?: "0")
                .format()
    }

    companion object {
        fun newInstance() : GameHeaderFragment {
            return GameHeaderFragment()
        }
    }
}