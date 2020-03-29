package com.anhtam.gate9.v2.report.post

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anhtam.domain.v2.Post
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.nph_detail.DetailNPHFragment
import com.anhtam.gate9.v2.user_detail.DetailUserFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.activity_report_post.*
import of.bum.network.helper.Resource
import javax.inject.Inject
import javax.inject.Named

class ReportPostActivity(private val mId: Long) : DaggerNavigationFragment(R.layout.activity_report_post) {

    @Inject @field:Named("avatar") lateinit var options: RequestOptions
    private val mViewModel by viewModels<ReportPostViewModel> { vmFactory }
    private var mCurrentReason = 1
    private var mPost: Post? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        initView()
        initEvent()
        observer()
    }

    companion object{
        fun newInstance(id: Long) = ReportPostActivity(id)
    }

    private fun loadData() {
        showProgress()
        mViewModel.getDetailPost(mId)
    }

    private fun observer() {
        mViewModel.comments.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    hideProgress()
                    mPost = it.data?.mDetails
                    initView()
                }
                is Resource.Error -> {
                    hideProgress()
                }
            }
        })
    }

    private fun initView() {
        val avatar = if(mPost?.user?.mAvatarPath.isNullOrEmpty()) mPost?.user?.mAvatar else mPost?.user?.mAvatarPath
        Glide.with(this).load(avatar?.toImage()).apply(options).into(avatarImageView)
        nameTextView?.text = mPost?.user?.mName
        descriptionTextView?.text = mPost?.createdDate
        contentTextView?.text = mPost?.content
    }

    private fun navigateToUserDetail() {
        val user = mPost?.user ?: mPost?.createdUser ?: return
        val roleId = user.mRoleId ?: return
        val id = user.mId ?: return
        if (roleId != 5){
            navigation?.addFragment(DetailUserFragment.newInstance(id))
        } else {
            navigation?.addFragment(DetailNPHFragment.newInstance(id))
        }
    }


    private fun initEvent(){

        avatarImageView?.setOnClickListener { navigateToUserDetail() }
        nameTextView?.setOnClickListener { navigateToUserDetail() }
        detailTextView?.setOnClickListener { navigateToUserDetail() }

        reason1Layout?.setOnClickListener { eventElement(reason1ImageView, 1) }
        reason2Layout?.setOnClickListener { eventElement(reason2ImageView, 2) }
        reason3Layout?.setOnClickListener { eventElement(reason3ImageView, 3) }
        reason4Layout?.setOnClickListener { eventElement(reason4ImageView, 4) }
        otherReasonLayout?.setOnClickListener { eventElement(otherReasonImageView, 5) }
        confirmButton?.setOnClickListener {
            showProgress()
            val id = mPost?.commentId ?: return@setOnClickListener hideProgress()
            val reason: String = when(mCurrentReason) {
                1 -> getString(R.string.report_post_make_me_angry)
                2 -> getString(R.string.report_post_not_suitable)
                3 -> getString(R.string.report_post_not_shouldnot_appare)
                4 -> getString(R.string.report_post_not_spam)
                5 -> reasonEditText?.text?.toString() ?: return@setOnClickListener hideProgress()
                else -> return@setOnClickListener hideProgress()
            }
            mViewModel.report(id, reason).observe(viewLifecycleOwner, Observer {
                when(it) {
                    is Resource.Success -> {
                        hideProgress()
                        navigation?.back()
                    }
                    is Resource.Error -> {
                        hideProgress()
                        Toast.makeText(context, "Không thành công", Toast.LENGTH_SHORT).show()
                    }
                }
            })

        }
    }

    private fun eventElement(imageView: ImageView, index: Int) {

        if (mCurrentReason == index) return
        if (index != 5) {
            reasonEditText?.isEnabled = false; reasonEditText?.setText("")
        } else {
            reasonEditText?.isEnabled = true
        }
        when(mCurrentReason) {
            1 -> Glide.with(this).load(R.drawable.ic_non_check).into(reason1ImageView)
            2 -> Glide.with(this).load(R.drawable.ic_non_check).into(reason2ImageView)
            3 -> Glide.with(this).load(R.drawable.ic_non_check).into(reason3ImageView)
            4 -> Glide.with(this).load(R.drawable.ic_non_check).into(reason4ImageView)
            5 -> Glide.with(this).load(R.drawable.ic_non_check).into(otherReasonImageView)
        }
        mCurrentReason = index
        Glide.with(this).load(R.drawable.ic_checked_radio).into(imageView)
    }

    override fun menuRes() = R.menu.chat_avatar_report_menu
}
