package com.anhtam.gate9.share.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.anhtam.gate9.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_dialog_more.*


class MoreDialog constructor(
        context: Context,
        private val mIsOwner: Boolean) : Dialog(context, R.style.Theme_Dialog){

    companion object {
        fun newInstance(context: Context, isOwner: Boolean = false) = MoreDialog(context, isOwner)
    }

    private var mIsBlockAccount: Boolean = false
    private var mIsBlockComment: Boolean = false
    private var mIsTurnOnNotification: Boolean = false
    private var mListener: IMore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_dialog_more)
        val window = window
        window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        setCancelable(false)
        initView()
        initEvents()
    }

    private fun initView() {
        editLayout?.visibility = if(mIsOwner) View.VISIBLE else View.GONE
        deleteLayout?.visibility = if (mIsOwner) View.VISIBLE else View.GONE

        // --> Block account
        var icon = if (mIsBlockAccount) R.drawable.ic_checked else R.drawable.ic_check
        Glide.with(context).load(icon).into(blockAccountSwitchButton)

        // --> Block comment
        icon = if (mIsBlockComment) R.drawable.ic_checked else R.drawable.ic_check
        Glide.with(context).load(icon).into(blockCommentSwitchButton)

        // --> turn off notification
        icon = if (mIsTurnOnNotification) R.drawable.ic_checked else R.drawable.ic_check
        Glide.with(context).load(icon).into(notificationSwitchButton)
    }


    private fun initEvents(){
        tvCancel.setOnClickListener { hide() }
        tvReport.setOnClickListener {
            dismiss()
        }

        deleteLayout?.setOnClickListener { mListener?.delete(); dismiss()}
        editLayout?.setOnClickListener { mListener?.edit(); dismiss() }
        reportLayout?.setOnClickListener { mListener?.report(); dismiss() }

        blockAccountLayout?.setOnClickListener {
            mIsBlockAccount = !mIsBlockAccount
            val icon = if (mIsBlockAccount) R.drawable.ic_checked else R.drawable.ic_check
            Glide.with(context).load(icon).into(blockAccountSwitchButton)
        }
        commentLayout?.setOnClickListener {
            mIsBlockComment = !mIsBlockComment
            val icon = if (mIsBlockComment) R.drawable.ic_checked else R.drawable.ic_check
            Glide.with(context).load(icon).into(blockCommentSwitchButton)
        }
        notificationLayout?.setOnClickListener {
            mIsTurnOnNotification = !mIsTurnOnNotification
            val icon = if (mIsTurnOnNotification) R.drawable.ic_checked else R.drawable.ic_check
            Glide.with(context).load(icon).into(notificationSwitchButton)
        }
    }

    fun setMoreListener(listener: IMore?) {
        mListener = listener
    }

    interface IMore {
        fun delete()
        fun edit()
        fun report()
    }
}