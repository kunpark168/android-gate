package com.anhtam.gate9.v2.main

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStoreOwner
import com.anhtam.gate9.R
import com.anhtam.gate9.navigation.NavigationFragment
import com.anhtam.gate9.utils.DialogProgressUtils
import com.anhtam.gate9.utils.autoCleared
import com.anhtam.gate9.v2.MainViewModel
import com.anhtam.gate9.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

abstract class DaggerNavigationFragment : NavigationFragment(){

    @Inject lateinit var vmFactory: ViewModelProviderFactory

    private var mProgressDialog: DialogProgressUtils? = null
    protected val mMainViewModel by viewModels<MainViewModel>({activity!!}, {vmFactory})

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMainViewModel.getUserDetail()
    }

    fun showProgress() {
        mProgressDialog = DialogProgressUtils(context, false)
        mProgressDialog?.setOnCancelListener { mProgressDialog = null }
        mProgressDialog?.show()

        object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                if(mProgressDialog != null && mProgressDialog?.isShowing == true) {
                    mProgressDialog?.cancel()
                    Toast.makeText(context, resources.getString(R.string.unable_connect_server), Toast.LENGTH_SHORT).show()
                }
            }

        }.start()
    }

    fun hideProgress() {
        if (mProgressDialog != null && mProgressDialog?.isShowing == true) {
            mProgressDialog?.cancel()
        }
    }

    fun setSupportActionBar(toolbar: Toolbar) {
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.title = ""
    }

    fun hideKeyboard() {
        val view = activity?.currentFocus
        if (view != null) {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}