package com.anhtam.gate9.v2.discussion.user

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.discussion.DiscussionFragment
import com.anhtam.gate9.v2.discussion.DiscussionViewModel
import com.anhtam.gate9.v2.discussion.TabInfo
import com.anhtam.gate9.v2.discussion.common.data.DataFragment
import com.anhtam.gate9.v2.discussion.common.game.GGameFragment
import com.anhtam.gate9.v2.discussion.common.info.NPHInfoFragment
import com.anhtam.gate9.v2.discussion.common.info.UserInfoFragment
import com.anhtam.gate9.v2.discussion.common.newfeed.NewFeedFragment
import com.anhtam.gate9.v2.discussion.common.rating.RatingFragment
import com.anhtam.gate9.v2.report.user.ReportUserActivity
import com.anhtam.gate9.v2.shared.views.AbstractVisibleFragment
import com.anhtam.gate9.vo.model.Category
import kotlinx.android.synthetic.main.shared_discussion_fragment.*
import of.bum.network.helper.Resource

class UserDiscussionScreen : DiscussionFragment() {

    private var mUserId: Int = 0
    private lateinit var mType: Category
    private val viewModel: DiscussionViewModel by viewModels { vmFactory }
    override val fragmentInfo: Fragment
        get() =
            if (mType == Category.Member)  UserInfoFragment.newInstance()
            else  NPHInfoFragment.newInstance()

    override fun navigateToReport() {
        navigation?.addFragment(ReportUserActivity.newInstance())
    }
    override val headerFragment: Fragment
        get() = UserHeaderFragment.newInstance(mType)

    override fun fragments(): List<AbstractVisibleFragment> {
        val fragments = ArrayList<AbstractVisibleFragment>()
        fragments.add(NewFeedFragment())
        fragments.add(RatingFragment.newInstance(mUserId, mType))
        fragments.add(DataFragment.newInstance(mUserId))
        fragments.add(GGameFragment.newInstance())
        return fragments
    }

    override fun tabInfoHeader(): List<TabInfo> {
        val tabInfo = ArrayList<TabInfo>()
        tabInfo.add(TabInfo(R.drawable.ic_tab_chat, R.color.tab_orange, R.string.tab_discussion))
        tabInfo.add(TabInfo(R.drawable.ic_tab_rating, R.color.tab_orange_light, R.string.tab_rating))
        tabInfo.add(TabInfo(R.drawable.ic_tab_data, R.color.tab_green_light, R.string.tab_data))
        tabInfo.add(TabInfo(R.drawable.ic_tab_game, R.color.tab_orange_light, R.string.tab_game))
        return tabInfo
    }

    override fun tabInfoBottom(): List<TabInfo> {
        val tabInfo = ArrayList<TabInfo>()
        tabInfo.add(TabInfo(R.drawable.ic_new_feed_grey, R.color.colorGray, R.string.new_feed))
        tabInfo.add(TabInfo(R.drawable.ic_messenger, R.color.tab_message, R.string.message))
        tabInfo.add(TabInfo(R.drawable.ic_chat_bottom, R.color.tab_orange, R.string.chat))
        return tabInfo
    }

    override fun loadData() {
        viewModel.mUserId.value = mUserId
    }

    override fun observer() {
        super.observer()
        viewModel.mUser.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    val user = it.data ?: return@Observer
                    val id = user.mId ?: return@Observer
                    val roleId = user.mRoleId?.toInt() ?: return@Observer
                    // following here
                    val following = user.mIsFollowing ?: false
                    navControllerView?.initialize(following, id, roleId)
                }
                is Resource.Error -> {

                }
            }
        })
    }

    companion object {
        fun newInstance(idUser: Int, type: Category): UserDiscussionScreen {
            val fragment = UserDiscussionScreen()
            fragment.mUserId = idUser
            fragment.mType = type
            return fragment
        }
    }
}