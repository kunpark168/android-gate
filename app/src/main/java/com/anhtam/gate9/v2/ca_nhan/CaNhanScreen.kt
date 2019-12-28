package com.anhtam.gate9.v2.ca_nhan

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProviders
import com.anhtam.domain.v2.User
import com.anhtam.gate9.R
import com.anhtam.gate9.session.AuthClient
import com.anhtam.gate9.storage.StorageManager
import com.anhtam.gate9.utils.autoCleared
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.auth.register.RegisterScreen
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.main.home.HomeFragment
import com.anhtam.gate9.v2.newfeed.NewFeedScreen
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.ca_nhan_screen.*
import of.bum.network.helper.RestResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class CaNhanScreen : DaggerNavigationFragment() {

    companion object{
        fun newInstance() = CaNhanScreen()
    }

    private var mViewModel: UserViewModel by autoCleared()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.ca_nhan_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.chat_message_notify, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun init() {
        mViewModel = ViewModelProviders.of(this, vmFactory).get(UserViewModel::class.java)
        setSupportActionBar(toolbar)
        eventClick()
        if(StorageManager.getAccessToken().isNotEmpty()) {
            hide()
        } else {
            display()
        }
        observer()
    }

    private fun observer() {
        mViewModel.getInfoUser().enqueue(object: Callback<RestResponse<User>> {
            override fun onFailure(call: Call<RestResponse<User>>, t: Throwable) {
                Timber.d("Fail to load user info")
            }

            override fun onResponse(call: Call<RestResponse<User>>, response: Response<RestResponse<User>>) {
                if(response.isSuccessful && response.code() == 200) {
                    val data = response.body() ?: return
                    Glide.with(this@CaNhanScreen).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.img_avatar_holder).error(R.drawable.img_avatar_holder)).load(data.data?.mAvatarPath).into(imgAvatar)
                    tvTitle?.text = data.data?.mName
                    val genderSrc: Int
                    genderSrc = when(data.data?.mGender) {
                        1 -> R.drawable.ic_male
                        2 -> R.drawable.ic_femail
                        else -> R.drawable.ic_gender
                    }
                    tvTitle?.setCompoundDrawablesWithIntrinsicBounds(0, 0, genderSrc, 0)
                }
            }
        })
    }

    private fun hide(){
        linearRegister?.visibility = View.GONE
        layoutInfo?.visibility = View.VISIBLE
    }

    private fun display() {
        linearRegister?.visibility = View.VISIBLE
        layoutInfo?.visibility = View.GONE
    }

    private fun eventClick() {
        tvLogin?.setOnClickListener {
            navigation?.addFragment(LoginScreen.newInstance())
        }
        tvRegister?.setOnClickListener {
            navigation?.addFragment(RegisterScreen.newInstance())
        }
        imgBack?.setOnClickListener {
//            navigation?.back()
//            gotoSetting
        }
        logoutLayout?.setOnClickListener {
            AuthClient.logout()
            navigation?.newRootFragment(HomeFragment.newInstance())
        }
    }
}