package com.anhtam.gate9.ui.discussion.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anhtam.domain.v2.User
import com.anhtam.gate9.R
import com.anhtam.gate9.ui.discussion.DiscussionViewModel
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

class UserHeaderFragment : DaggerNavigationFragment() {

    private lateinit var mType: Category
    private var viewModel: DiscussionViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = parentFragment?.let { ViewModelProviders.of(it, vmFactory).get(DiscussionViewModel::class.java) }
        observer()
    }

    private fun layoutRes(): Int {
        return when(mType) {
            Category.Member -> R.layout.header_gamer_discussion
            Category.Publisher -> R.layout.header_publisher_discussion
        }
    }

    private fun observer() {
        viewModel?.mUser?.observe(this, Observer {
            when(it) {
                is Resource.Success -> {
                    val user = it.data ?: return@Observer
                    bindView(user)
                }
                else -> {
                    Timber.d("Type")
                }
            }
        })
    }

    private fun bindView(user: User) {
        when(mType) {
            Category.Member -> bindMember(user)
            Category.Publisher -> bindPublisher(user)
        }
    }

    private fun bindMember(user: User) {
        Glide.with(this).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.img_avatar_holder))
                .load(user.mAvatarPath)
                .into(imgAvatar)

        tvUserName?.text = user.mName
        tvFollow?.text = user.mFlowing
        tvFollower?.text = user.mFlower
        tvCounting?.text = Phrase.from(resources.getString(R.string.number_point))
                .put("point", user.mPoint)
                .put("medal", /*user.medal TODO */ "").format()
        tvSlogan?.text = user.mNote
        val gioitinh = when(user.mGender) {
            1 -> R.drawable.ic_female
            2 -> R.drawable.ic_male_circle
            else -> R.drawable.ic_gender
        }
        Glide.with(this).load(gioitinh)
                .into(imgGender)
    }

    private fun bindPublisher(user: User) {
        Glide.with(this).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.img_avatar_holder))
                .load(user.mAvatarPath)
                .into(imgAvatar)
        tvUserName?.text = user.mName
        csRating?.setColor(true)
        csRating?.init("4.3", "4356")

        val followString = Phrase.from(resources.getString(R.string.following_follower_publisher))
                .put("following", user.mFlowing ?: "0")
                .put("follower", user.mFlower ?: "0")
                .put("game", "100")
                .format()
        tvGameCount?.text = followString

        tvSlogan?.text = user.mNote
        val gioitinh = when(user.mGender) {
            1 -> R.drawable.ic_male
            2 -> R.drawable.ic_femail
            else -> R.drawable.ic_gender
        }
        Glide.with(this).load(gioitinh)
                .into(imgGender)
    }

    companion object {
        fun newInstance(type: Category) : UserHeaderFragment {
            val fragment = UserHeaderFragment()
            fragment.mType = type
            return fragment
        }
    }
}