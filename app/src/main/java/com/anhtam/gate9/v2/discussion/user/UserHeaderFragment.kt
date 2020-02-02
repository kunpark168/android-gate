package com.anhtam.gate9.v2.discussion.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.convertInt
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.charts.ChartScreen
import com.anhtam.gate9.v2.discussion.DiscussionViewModel
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.vo.model.Category
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.header_gamer_discussion.*
import kotlinx.android.synthetic.main.header_gamer_discussion.imgAvatar
import kotlinx.android.synthetic.main.header_gamer_discussion.imgGender
import kotlinx.android.synthetic.main.header_gamer_discussion.tvFollower
import kotlinx.android.synthetic.main.header_gamer_discussion.tvSlogan
import kotlinx.android.synthetic.main.header_gamer_discussion.tvUserName
import kotlinx.android.synthetic.main.header_publisher_discussion.*
import of.bum.network.helper.Resource
import timber.log.Timber

class UserHeaderFragment(private val mType: Category, @LayoutRes layoutId: Int) : DaggerNavigationFragment(layoutId){

    private val viewModel: DiscussionViewModel by viewModels({requireParentFragment()}, {vmFactory})
    private var mUser: User? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
    }

    private fun observer() {
        viewModel.mUser.observe(this, Observer {
            when(it) {
                is Resource.Success -> {
                    mUser = it.data ?: return@Observer
                    bindView()
                }
                else -> {
                    Timber.d("Type")
                }
            }
        })
    }

    private fun bindView() {
        when(mType) {
            Category.Member -> bindMember()
            Category.Publisher -> bindPublisher()
        }
    }

    private fun bindMember() {
        val user = mUser ?: return
        Glide.with(this).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.img_avatar_holder))
                .load(user.mAvatar?.toImage())
                .into(imgAvatar)

        tvUserName?.text = user.mName
        tvFollow?.text = user.mFlowing?.toString() ?: "0"
        tvFollower?.text = user.mFlower?.toString() ?: "0"
        tvCounting?.text = Phrase.from(resources.getString(R.string.number_point))
                .put("point", user.mPoint?.toString())
                .put("medal", user.mAppellation).format()
        tvSlogan?.text = user.mNote
        val icon = when(user.mGender?.convertInt()) {
            1 -> R.drawable.ic_female
            2 -> R.drawable.ic_male_circle
            else -> R.drawable.ic_gender
        }
        Glide.with(this).load(icon)
                .into(imgGender)
    }

    private fun bindPublisher() {
        val user = mUser ?: return
        Glide.with(this).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.img_avatar_holder))
                .load(user.mAvatar?.toImage())
                .into(imgAvatar)
        tvUserName?.text = user.mName
        csRating?.setColor(true)
        csRating?.init("4.3", "4356")

        val followString = Phrase.from(resources.getString(R.string.following_follower_publisher))
                .put("following", user.mFlowing?.toString() ?: "0")
                .put("follower", user.mFlower?.toString() ?: "0")
                .put("game", "100")
                .format()
        tvGameCount?.text = followString

        tvSlogan?.text = user.mNote
        val icon = when(user.mGender?.convertInt()) {
            1 -> R.drawable.ic_male
            2 -> R.drawable.ic_femail
            else -> R.drawable.ic_gender
        }
        Glide.with(this).load(icon)
                .fitCenter()
                .into(imgGender)

        imgChart?.setOnClickListener {
            navigation?.addFragment(ChartScreen.newInstance(user))
        }
    }

    companion object {
        fun newInstance(type: Category)  : UserHeaderFragment{
            val layout = when(type) {
                Category.Member -> R.layout.header_gamer_discussion
                Category.Publisher -> R.layout.header_publisher_discussion
            }
            return UserHeaderFragment(type, layout)
        }
    }
}