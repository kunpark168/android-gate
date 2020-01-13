package com.anhtam.gate9.v2.discussion.common.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.convertInt
import com.anhtam.gate9.v2.discussion.DiscussionViewModel
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.fragment_info_user.*
import of.bum.network.helper.Resource

class UserInfoFragment: DaggerNavigationFragment() {


    private var mUser: User? = null

    private var mDiscussionViewModel: DiscussionViewModel? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        parentFragment?.let {
            mDiscussionViewModel = ViewModelProviders.of(it, vmFactory).get(DiscussionViewModel::class.java)
        }
        init()
    }

    private fun init() {
        mDiscussionViewModel?._bottomStatus?.value = false
        observer()
    }

    private fun observer() {
        mDiscussionViewModel?.mUser?.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    hideProgress()
                    mUser = it.data
                    bindInfo()
                }
                else -> {

                }
            }
        })
    }

    private fun bindInfo() {
        tvId?.text = infoOrDefault(mUser?.mId?.toString())
        tvShortName?.text = infoOrDefault(mUser?.mName)
        tvDisplayName?.text = infoOrDefault(mUser?.mName)
//        tvMedal?.text = infoOrDefault(mUser?)
        tvTotalPoint?.text = infoOrDefault(mUser?.mPoint)

        tvReview?.text = "-"
        tvRanking?.text = "-"
        val icon = when(mUser?.mGender?.convertInt()) {
            1 -> "Nữ"
            2 -> "Nam"
            else -> "Không xác định"
        }
        tvGender?.text = infoOrDefault(icon)
        tvBirthDate?.text = infoOrDefault(mUser?.mBirth)
        tvCurrentLive?.text = infoOrDefault(mUser?.mAddress)
        tvStatus?.text = "-"
        tvCreatedDate?.text = infoOrDefault(mUser?.mCreatedDate)
        tvIntro?.text = infoOrDefault(mUser?.mNote)
//        tvHome?.text = infoOrDefault(mUser?.url)
        tvFacebook?.text = infoOrDefault(mUser?.mFacebook)
        tvEmail?.text = infoOrDefault(mUser?.mEmail)
        tvPhone?.text = infoOrDefault(mUser?.mPhone)
        tvSkype?.text = infoOrDefault(mUser?.mSkype)
        tvQQ?.text = infoOrDefault(mUser?.mQQ)
    }

    companion object {
        fun newInstance(): UserInfoFragment {
            return UserInfoFragment()
        }
    }

    private fun infoOrDefault(text: String?) : String {
        if (text.isNullOrBlank()) {
            return "-"
        }
        return text
    }
}