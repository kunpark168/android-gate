package com.anhtam.gate9.v2.discussion.game

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.discussion.DiscussionViewModel
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.header_game_discussion.*
import javax.inject.Inject
import javax.inject.Named

class GameHeaderFragment : DaggerNavigationFragment(R.layout.header_game_discussion) {

    private val mDiscussionViewModel: DiscussionViewModel by viewModels({requireParentFragment()}, {vmFactory})
    @Inject @field:Named("banner") lateinit var bannerOption: RequestOptions
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerGame()
    }

    private fun observerGame(){
        mDiscussionViewModel.mGame.observe(viewLifecycleOwner, Observer {
            val game = it ?: return@Observer
            initView(game)
        })
    }

    private fun initView(game: Game){
        val unwrapContext = context ?: return
        Glide.with(unwrapContext)
                .load(game.imgCover?.toImage())
                .apply(bannerOption)
                .into(bannerImageView)

        Glide.with(unwrapContext)
                .load(game.avatar?.toImage())
                .apply(bannerOption)
                .into(avatarImageView)
        tvTitle?.text = game.name
        tvFollowAndPost?.text = Phrase.from(getString(R.string.follower_amount_and_post_amount))
                .put("follower", game.follower?.toString() ?: "0")
                .put("post", game.post?.toString() ?: "0")
                .format()
    }

    companion object {
        fun newInstance() : GameHeaderFragment {
            return GameHeaderFragment()
        }
    }
}