package com.anhtam.gate9.ui.base

import android.content.Context
import com.anhtam.gate9.viewmodel.ViewModelProviderFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment: DaggerFragment() {

    @Inject lateinit var factory: ViewModelProviderFactory
    protected val mRequestManager: RequestManager by lazy {
        Glide.with(this)
    }

    private var mBaseActivity: BaseActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            mBaseActivity = context
        } else {
            throw Exception("Invalid Activity")
        }
    }

    fun showProgress() {
        mBaseActivity?.showProgress()
    }
    fun hideKeyboard() {
        mBaseActivity?.hideKeyBoard()
    }
    fun hideProgress() {
        mBaseActivity?.hideProgress()
    }
    fun onShowDonateDialog(idPost: String){
        mBaseActivity?.showDonateDialog(idPost)
    }
    fun onShowMoreDialog(idPost: String){
        mBaseActivity?.showMoreDialog(idPost)
    }
}