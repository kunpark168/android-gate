package com.anhtam.gate9.v2.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.SharePageAdapter
import com.anhtam.gate9.navigation.FragmentResultListener
import com.anhtam.gate9.v2.createimage.CreateImageScreen
import com.anhtam.gate9.v2.createpost.CreatePostScreen
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.newfeed.NewFeedScreen
import com.anhtam.gate9.v2.follow.FollowScreen
import kotlinx.android.synthetic.main.main_fragment.*
import com.anhtam.gate9.storage.StorageManager
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.ca_nhan.CaNhanScreen
import com.anhtam.gate9.v2.mxh_gate.MXHGateScreen


class HomeFragment : DaggerNavigationFragment(), FragmentResultListener {

    override fun onFragmentResult(args: Bundle) {
        mNewFeedScreen?.update()
    }

    companion object {
        fun newInstance() = HomeFragment()
    }


    /* Manager bottom view vars */
    private var mStateFb = false
    private var mNewFeedScreen: NewFeedScreen? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initOptionsMore()
        eventClicks()
    }

    private fun eventClicks() {
        layoutPostText?.setOnClickListener {
            if (checkLogin()) {
                mStateFb = false
                displayFeature()
                navigation?.addFragment(CreatePostScreen.newInstance())
                displayFeature()
            }
        }
        layoutPostImage?.setOnClickListener {
            mStateFb = false
            displayFeature()
            navigation?.addFragment(CreateImageScreen.newInstance())
            displayFeature()
        }
        layoutPostVideo?.setOnClickListener {
            mStateFb = false
            displayFeature()
            navigation?.addFragment(CreateImageScreen.newInstance())
            displayFeature()
        }
        layoutPostTopic?.setOnClickListener {  }
    }

    private fun checkLogin() : Boolean {
        val accessToken = StorageManager.getAccessToken()
        return if (accessToken.isEmpty()) {
            navigation?.addFragment(LoginScreen.newInstance(false))
            false
        } else {
            true
        }
    }

    private fun initOptionsMore() {
        fbOptionsMore?.setOnClickListener {
            displayFeature()
        }
    }

    private fun displayFeature() {
        if (mStateFb) {
            mStateFb = false
            fbOptionsMore?.animate()?.rotation(0f)
            csOption?.visibility = View.GONE
        } else {
            mStateFb = true
            fbOptionsMore?.animate()?.rotation(135f)
            csOption?.visibility = View.VISIBLE
        }
    }

    private fun initViewPager() {
        val fragments = arrayListOf<Fragment>()
        mNewFeedScreen = NewFeedScreen.newInstance()
        fragments.add(mNewFeedScreen!!)
        fragments.add(FollowScreen.newInstance())
        childFragmentManager.run {
            val adapter = SharePageAdapter(this, fragments)
            viewPager?.adapter = adapter
            viewPager?.offscreenPageLimit = 2
        }
        bottomView?.syncWithViewPager(viewPager)
        bottomView?.openPersonal {
            navigation?.addFragment(CaNhanScreen.newInstance())
        }
        bottomView?.openGate {
            navigation?.addFragment(MXHGateScreen.newInstance())
        }
    }
}