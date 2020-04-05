package com.anhtam.gate9.v2.reaction

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.restful.BackgroundTasks
import com.anhtam.gate9.session.SessionManager
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.nph_detail.DetailNPHFragment
import com.anhtam.gate9.v2.user_detail.DetailUserFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.member_item_layout.view.*

class CurrentUserHeaderView @JvmOverloads constructor(
        context: Context?,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
): LinearLayout(context, attrs, defStyle) {

    private lateinit var mUser: User
    private lateinit var mOptions: RequestOptions
    private var mNavigation: Navigation? = null
    private lateinit var mSessionManager: SessionManager
    private var mIsFollowing = false

    init {
        View.inflate(context, R.layout.member_item_layout, this)
        initEvents()
    }

    fun setUser(user: User, options: RequestOptions, navigation: Navigation?, sessionManager: SessionManager) {
        mUser = user
        mOptions = options
        mNavigation = navigation
        mSessionManager = sessionManager
        initView()
    }


    private fun initView() {
        nameTextView?.text = mUser.mName
        userIdTextView?.text = mUser.mId?.toString()
        Glide.with(context)
                .load(mUser.mAvatar?.toImage())
                .apply(mOptions)
                .into(avatarImageView)
        mIsFollowing = mUser.mIsFollowing ?: false
        onUpdateFollow()
    }



    private fun unFollowing(){
        // un follow
        followButton?.background = ContextCompat.getDrawable(context, R.drawable.bg_follow)
        followButton?.text = context?.resources?.getString(R.string.follow_plus)
        followButton?.setTextColor(ContextCompat.getColor(context, R.color.color_follow))
    }

    private fun following(){
        followButton?.background = ContextCompat.getDrawable(context, R.drawable.bg_following)
        followButton?.text = context?.resources?.getString(R.string.following)
        followButton?.setTextColor(ContextCompat.getColor(context, R.color.color_following))
    }

    private fun initEvents() {
        nameTextView?.setOnClickListener {
            navigateToUser()
        }
        avatarImageView?.setOnClickListener {
            navigateToUser()
        }
        followButton?.setOnClickListener {
            val idUser = mUser.mId ?: return@setOnClickListener
            val role = mUser.mRoleId ?: return@setOnClickListener
            if (mSessionManager.checkLogin(isDirect = true)){
                mIsFollowing = !mIsFollowing
                onUpdateFollow()
                BackgroundTasks.postUserFollow(idUser, role)
            }
        }
    }

    private fun onUpdateFollow() {
        if (mIsFollowing){
            following()
        } else {
            // follow
            unFollowing()
        }
    }


    private fun navigateToUser() {
        val roleId = mUser.mRoleId ?: return
        val id = mUser.mId ?: return
        if (roleId != 5){
            mNavigation?.addFragment(DetailUserFragment.newInstance(id))
        } else {
            mNavigation?.addFragment(DetailNPHFragment.newInstance(id))
        }
    }
}