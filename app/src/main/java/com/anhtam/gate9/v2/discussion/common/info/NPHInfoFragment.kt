package com.anhtam.gate9.v2.discussion.common.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.discussion.DiscussionViewModel
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.fragment_info_nph.*

import of.bum.network.helper.Resource

class NPHInfoFragment: DaggerNavigationFragment(R.layout.fragment_info_nph) {


    private var mUser: User? = null

    private val viewModel: DiscussionViewModel by viewModels ({requireParentFragment()}, { vmFactory })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
    }

    private fun init() {
        viewModel._bottomStatus.value = false
        observer()
    }

    private fun observer() {
        viewModel.mUser.observe(viewLifecycleOwner, Observer {
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

        tvReview?.text = "-"
        tvRanking?.text = infoOrDefault(mUser?.mRanking)
        tvTotalGame?.text = "-"
        tvCountry?.text = "-"
        tvCreatedDate?.text = infoOrDefault(mUser?.mCreatedDate)
        tvIntro?.text = infoOrDefault(mUser?.mNote)
        tvHome?.text = infoOrDefault(mUser?.mWeb)
        tvEmail?.text = infoOrDefault(mUser?.mEmail)
        tvPhone?.text = infoOrDefault(mUser?.mPhone)
        tvSkype?.text = infoOrDefault(mUser?.mSkype)
        tvLocation?.text = infoOrDefault(mUser?.mAddress)
        tvQQ?.text = infoOrDefault(mUser?.mQQ)
    }

    companion object {
        fun newInstance(): NPHInfoFragment {
            return NPHInfoFragment()
        }
    }

    private fun infoOrDefault(text: String?) : String {
        if (text.isNullOrBlank()) {
            return "-"
        }
        return text
    }
}