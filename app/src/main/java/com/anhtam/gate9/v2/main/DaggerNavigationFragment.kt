package com.anhtam.gate9.v2.main

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.anhtam.gate9.R
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.fcm.FCMService
import com.anhtam.gate9.navigation.NavigationFragment
import com.anhtam.gate9.session.SessionManager
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.viewmodel.ViewModelProviderFactory
import com.bumptech.glide.Glide
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.layout_avatar_menu.view.*
import of.bum.network.helper.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.lang.IllegalArgumentException
import javax.inject.Inject

abstract class DaggerNavigationFragment constructor(
        @LayoutRes private val layoutId: Int
) : NavigationFragment(){

    @Inject lateinit var vmFactory: ViewModelProviderFactory
    @Inject lateinit var mSessionManager: SessionManager

    private var mProgressDialog: MaterialDialog? = null
    private var mAvatar: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    @MenuRes
    open fun menuRes():  Int?  = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(view)
        initProgressDialog()
        observer()
    }

    private fun observer(){
        mSessionManager.cachedUser.observe(viewLifecycleOwner, Observer {
            if(it is Resource.Success){
                mAvatar = it.data?.mAvatarPath ?: it.data?.mAvatar
                mAvatar?.let {
                    activity?.invalidateOptionsMenu()
                }

                it.data?.mId?.let {
                    updateToken(it)
                }
            }
        })
    }

    private fun initProgressDialog() {
        mProgressDialog = this.context?.let {
            MaterialDialog.Builder(it)
                    .content(getString(R.string.please_wait))
                    .progress(true, 0)
                    .widgetColorRes(R.color.colorPrimary)
                    .canceledOnTouchOutside(false)
                    .build()
        }
    }


    private fun initToolbar(view: View){
        menuRes()?.run {
            setHasOptionsMenu(true)
            val toolbar = view.findViewById<Toolbar>(R.id.toolbar) ?: throw IllegalArgumentException("Please make your toolbar id is toolbar")
            setSupportActionBar(toolbar)
            val backFrameLayout = view.findViewById<FrameLayout>(R.id.backFrameLayout)
            backFrameLayout?.setOnClickListener { navigation?.back() }
        }
    }

    fun showProgress(content: String = getString(R.string.please_wait)) {
        mProgressDialog?.setContent(content)
        mProgressDialog?.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        menuRes()?.run {
            inflater.inflate(this, menu)
        }
        val avatarItem = menu.findItem(R.id.avatar)?.actionView?.viewAvatarImageView
        avatarItem?.run {
            Glide.with(this)
                    .load(mAvatar?.toImage())
                    .into(this)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun hideProgress() {
        if(isDetached) return
        mProgressDialog?.dismiss()
    }

    private fun setSupportActionBar(toolbar: Toolbar) {
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

    private fun updateToken(idUser: Int){
        FirebaseInstanceId.getInstance().token?.let {
            Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(FCMService::class.java)
                    .updateTokenFCM(idUser, it).enqueue(object : Callback<Map<String, Any>> {
                        override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                            Timber.d(if(t.message != null) t.message else "Error Server")

                        }

                        override fun onResponse(call: Call<Map<String, Any>>, response: Response<Map<String, Any>>) {
                            Timber.d("Update FCM Token Successfully with token is :%s", it)
                        }

                    })
        }
    }
}