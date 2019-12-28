package com.anhtam.gate9.ui.discussion.common.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anhtam.domain.v2.User
import com.anhtam.gate9.R
import com.anhtam.gate9.ui.base.BaseFragment
import com.anhtam.gate9.ui.discussion.DiscussionViewModel
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.fragment_info_nph.*
import kotlinx.android.synthetic.main.fragment_info_nph.tvEmail
import kotlinx.android.synthetic.main.fragment_info_nph.tvHome
import kotlinx.android.synthetic.main.fragment_info_nph.tvIntro
import kotlinx.android.synthetic.main.fragment_info_nph.tvPhone

import of.bum.network.helper.Resource

class NPHInfoFragment: DaggerNavigationFragment() {


    private var mUser: User? = null

    private var mDiscussionViewModel: DiscussionViewModel? = null
    private val viewModel: DiscussionViewModel by viewModels { vmFactory }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info_nph, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.let {
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
//        tvId?.text = infoOrDefault(mUser?.user_id)
//        tvShortName?.text = infoOrDefault(mUser?.userName)
//        tvDisplayName?.text = infoOrDefault(mUser?.name)
//
//        tvReview?.text = "-"
//        tvRanking?.text = "-"
//        tvTotalGame?.text = infoOrDefault(mUser?.totalGame?.totalgameopen)
////        tvCountry?.text = infoOrDefault(mUser?.)
//        tvCreatedDate?.text = infoOrDefault(mUser?.createdDate)
//        tvIntro?.text = infoOrDefault(mUser?.note)
//        tvHome?.text = infoOrDefault(mUser?.url)
//        tvEmail?.text = infoOrDefault(mUser?.email)
//        tvPhone?.text = infoOrDefault(mUser?.phone)
//        tvSkype?.text = infoOrDefault(mUser?.skype)
//        tvLocation?.text = infoOrDefault(mUser?.address)
//        tvQQ?.text = infoOrDefault(mUser?.qq)
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