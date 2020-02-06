package com.anhtam.gate9.v2.discussion.common.game

import androidx.fragment.app.viewModels
import com.anhtam.domain.v2.protocol.User
import com.anhtam.domain.v2.wrap.WrapGame
import com.anhtam.gate9.adapter.GameQuickAdapter

import com.anhtam.gate9.R
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionFragment
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.shared_discussion_layout.*
import kotlin.math.roundToLong


class GGameFragment: CommonDiscussionFragment<WrapGame, GameQuickAdapter, GGameViewModel>() {
    private var mCountTab1: Double = 0.0
    private var mCountTab2: Double = 0.0
    private var mCountTab3: Double = 0.0
    private var mCountTab4: Double = 0.0
    private var mCurrentCategory = GameCategory.OPEN

    private val viewModel: GGameViewModel by viewModels { vmFactory }

    override val colorTextTab: Int = R.color.colorTabGame

    override fun loadData() {
        viewModel.loadData(mCurrentCategory.category, refresh = true)
    }

    override fun configTabLayout() {
        tabLayout.apply {
            repeat(4) {
                addTab(newTab())
            }
        }
        updateTabLayout()
    }

    override fun updateTabLayout() {
        tabLayout.getTabAt(0)?.text = Phrase.from(resources.getString(R.string.open_category))
                .put("amount", mCountTab1.roundToLong().toString()).format()
        tabLayout.getTabAt(1)?.text = Phrase.from(resources.getString(R.string.beta_category))
                .put("amount", mCountTab2.roundToLong().toString()).format()
        tabLayout.getTabAt(2)?.text = Phrase.from(resources.getString(R.string.coming_category))
                .put("amount", mCountTab3.roundToLong().toString()).format()
        tabLayout.getTabAt(3)?.text = Phrase.from(resources.getString(R.string.closed_category))
                .put("amount", mCountTab4.roundToLong().toString()).format()
    }

    override fun onTabChanged(id: Int) {
        when(id) {
            0 -> {
                newRequestType(GameCategory.OPEN)
            }
            1 -> {
                newRequestType(GameCategory.BETA)
            }
            2 -> {
                newRequestType(GameCategory.COMING)
            }
            else -> {
                newRequestType(GameCategory.CLOSED)
            }
        }
    }

    private fun newRequestType(category: GameCategory) {
        mCurrentCategory = category
        mAdapter.data.clear()
        mAdapter.notifyDataSetChanged()
        if (viewModel.mCategory != category.category) {
            viewModel.loadData(category.category)
        }
    }

    companion object {
        fun newInstance() = GGameFragment()
    }

    override fun onAttachUser(user: User) {
        super.onAttachUser(user)
        viewModel.initialize(user)
    }
}
