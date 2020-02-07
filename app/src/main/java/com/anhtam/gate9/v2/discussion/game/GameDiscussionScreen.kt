package com.anhtam.gate9.v2.discussion.game

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.anhtam.domain.v2.Gamev1
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.discussion.DiscussionFragment
import com.anhtam.gate9.v2.discussion.TabInfo
import com.anhtam.gate9.v2.discussion.common.data.DataFragment
import com.anhtam.gate9.v2.discussion.common.discussion.DiscussionGameFragment
import com.anhtam.gate9.v2.discussion.common.document.GameDocumentFragment
import com.anhtam.gate9.v2.discussion.common.info.GameInfoFragment
import com.anhtam.gate9.v2.discussion.common.rating.RatingFragment
import com.anhtam.gate9.v2.report.game.ReportGameActivity
import com.anhtam.gate9.v2.shared.views.AbstractVisibleFragment
import com.anhtam.gate9.vo.model.Category
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.shared_discussion_fragment.*
import kotlinx.android.synthetic.main.header_game_discussion.*
import of.bum.network.helper.Resource

class GameDiscussionScreen: DiscussionFragment() {

    private lateinit var mLink: String
    private lateinit var mGameId: String
    override val fragmentInfo: Fragment
        get() = GameInfoFragment.newInstance()

    override fun navigateToReport() {
        navigation?.addFragment(ReportGameActivity.newInstance())
    }

    override val headerFragment: Fragment
        get() = GameHeaderFragment.newInstance()

    override fun fragments(): List<AbstractVisibleFragment> {
        val fragments = ArrayList<AbstractVisibleFragment>()
        fragments.add(GameDocumentFragment.newInstance(mGameId))
        fragments.add(RatingFragment.newInstance(mGameId.toInt(), Category.Member))
        fragments.add(DataFragment.newInstance(mGameId.toInt()))
        fragments.add(DiscussionGameFragment.newInstance(mLink))
        return fragments
    }

    override fun tabInfoHeader(): List<TabInfo> {
        val tabInfo = ArrayList<TabInfo>()
        tabInfo.add(TabInfo(R.drawable.ic_tab_chat, R.color.tab_orange, R.string.tab_discussion))
        tabInfo.add(TabInfo(R.drawable.ic_tab_rating, R.color.tab_orange_light, R.string.tab_rating))
        tabInfo.add(TabInfo(R.drawable.ic_tab_data, R.color.tab_green_light, R.string.tab_data))
        tabInfo.add(TabInfo(R.drawable.ic_tab_hoso, R.color.tab_pink, R.string.tab_hoso))
        return tabInfo
    }

    override fun tabInfoBottom(): List<TabInfo> {
        val tabInfo = ArrayList<TabInfo>()
        tabInfo.add(TabInfo(R.drawable.ic_new_feed_grey, R.color.colorGray, R.string.new_feed))
        tabInfo.add(TabInfo(R.drawable.ic_tab_new_post, R.color.colorGray, R.string.tab_new_post))
        tabInfo.add(TabInfo(R.drawable.ic_download_game, R.color.colorGray, R.string.download_game))
        return tabInfo
    }

    override fun loadData() {
        viewModel.mGameId.value = mGameId
    }

    override fun observer() {
        super.observer()
        viewModel._bottomStatus.observe(this, Observer {

            if(it) {
                footerFrameLayout?.visibility = View.VISIBLE
            } else {
                footerFrameLayout?.visibility = View.GONE
            }
        })
        viewModel.mGame.observe(this, Observer {
            when(it) {
                is Resource.Success -> {
                    it.data?.let {game ->
                        bindingView(game)
                    }
                }
                is Resource.Loading -> {

                }
                else -> {

                }
            }
        })
    }

    private fun bindingView(game: Gamev1) {
        csRating.setGameInformation(game)
        Glide.with(this)
                .load(game.avatar)
                .into(avatarImageView)
        Glide.with(this)
                .load(game.avatar)
                .into(bannerImageView)
    }

    companion object {
        fun newInstance(linkGame: String, gameId: String): GameDiscussionScreen {
            val fragment = GameDiscussionScreen()
            fragment.mLink = linkGame
            fragment.mGameId = gameId
            return fragment
        }
    }
}