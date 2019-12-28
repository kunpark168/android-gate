package com.anhtam.gate9.ui.base

import android.content.Context
import android.os.CountDownTimer
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.anhtam.gate9.R
import com.anhtam.gate9.share.view.MoreDialog
import com.anhtam.gate9.share.view.donate.DonateDialog
import com.anhtam.gate9.ui.report.post.ReportPostActivity
import com.anhtam.gate9.utils.DialogProgressUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.android.support.DaggerAppCompatActivity


abstract class BaseActivity : DaggerAppCompatActivity() {

    private var mProgressDialog: DialogProgressUtils? = null
    private var mDonateDialog: DonateDialog? = null
    private var mMoreDialog: MoreDialog? = null
    protected val mRequestManager: RequestManager by lazy {
        Glide.with(this)
    }

    /*
     * Tam Utils
     */
    fun action(v: View, runnable: Runnable?) {
        if (runnable == null)
            return
        v.setOnTouchListener { _, e ->
            when (e.action) {
                MotionEvent.ACTION_DOWN -> v.alpha = 0.5f
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> v.alpha = 1.0f
            }
            false
        }

        v.setOnClickListener { runnable.run() }
    }

    fun showProgress() {
        mProgressDialog = DialogProgressUtils(this, false)
        mProgressDialog?.setOnCancelListener { mProgressDialog = null }
        mProgressDialog?.show()

        object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                if(mProgressDialog != null && mProgressDialog?.isShowing == true) {
                    mProgressDialog?.cancel()
                    Toast.makeText(this@BaseActivity, resources.getString(R.string.unable_connect_server), Toast.LENGTH_SHORT).show()
                }
            }

        }.start()
    }

    fun hideProgress() {
        if (mProgressDialog != null && mProgressDialog?.isShowing == true) {
            mProgressDialog?.cancel()
        }
    }

    fun hideKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    /*Donate Dialog*/
    fun showDonateDialog(idPost: String?) {
        if (mDonateDialog == null)
            mDonateDialog = DonateDialog(this)
        mDonateDialog?.idPost = idPost
        mDonateDialog?.show()
    }

    /*More Dialog*/
    fun showMoreDialog(idPost: String) {
        if (mMoreDialog == null)
            mMoreDialog = MoreDialog(this, object : MoreDialog.IMore {
                override fun onreport() {
                    ReportPostActivity.start(this@BaseActivity)
                }
            })
        mMoreDialog?.idPost = idPost
        mMoreDialog?.show()
    }
}