package com.anhtam.gate9.v2.ca_nhan

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import com.anhtam.gate9.R
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.storage.StorageManager
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.auth.register.RegisterScreen
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.main.home.HomeFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.ca_nhan_screen.*
import of.bum.network.helper.Resource

class CaNhanScreen : DaggerNavigationFragment() {

    companion object{
        fun newInstance() = CaNhanScreen()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.ca_nhan_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun menuRes() = R.menu.chat_message_notify

    private fun init() {
        eventClick()
        if(StorageManager.getAccessToken().isNotEmpty()) {
            hide()
        } else {
            display()
        }
        observer()
    }

    private fun observer() {
        mSessionManager.cachedUser.observe(viewLifecycleOwner, Observer {
            val avatar = when(it) {
                is Resource.Loading -> null
                is Resource.Error -> ""
                is Resource.Success -> {
                    tvTitle?.text = it.data?.mName
                    val genderSrc = when(it.data?.mGender) {
                        1 -> R.drawable.ic_male
                        2 -> R.drawable.ic_femail
                        else -> R.drawable.ic_gender
                    }
                    tvTitle?.setCompoundDrawablesWithIntrinsicBounds(0, 0, genderSrc, 0)
                    Config.IMG_URL + it.data?.mAvatarPath
                }
            }

            avatar?.run {
                Glide.with(this@CaNhanScreen)
                        .load(avatar)
                        .apply {RequestOptions.circleCropTransform().placeholder(R.drawable.img_avatar_holder)
                                .error(R.drawable.img_avatar_holder) }
                        .into(imgAvatar)
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
            navigation?.addFragment(LoginScreen.newInstance(true))
        }
        tvRegister?.setOnClickListener {
            navigation?.addFragment(RegisterScreen.newInstance())
        }
        imgBack?.setOnClickListener {
//            gotoSetting
        }
        logoutLayout?.setOnClickListener {
            mSessionManager.logout()
            navigation?.newRootFragment(HomeFragment.newInstance())
        }
    }
}