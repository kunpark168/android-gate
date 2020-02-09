package com.anhtam.gate9.v2.main

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.anhtam.gate9.R
import com.anhtam.gate9.navigation.NavigationFragment
import com.anhtam.gate9.session.SessionManager
import com.anhtam.gate9.utils.DialogProgressUtils
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.viewmodel.ViewModelProviderFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_avatar_menu.view.*
import java.lang.IllegalArgumentException
import javax.inject.Inject

abstract class DaggerNavigationFragment constructor(
        @LayoutRes private val layoutId: Int
) : NavigationFragment(){

    @Inject lateinit var vmFactory: ViewModelProviderFactory
    @Inject lateinit var mSessionManager: SessionManager

    private var mProgressDialog: DialogProgressUtils? = null
    private var mAvatar: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    @MenuRes
    open fun menuRes():  Int?  = null
    @ColorRes
    open fun statusColor(): Int = R.color.color_main_blue

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStatus()
        initToolbar(view)
        observer()
    }

    private fun observer(){
        mSessionManager.cachedUser.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            mAvatar = it.data?.mAvatar ?: it.data?.mAvatar
            mAvatar?.let {
                activity?.invalidateOptionsMenu()
            }
        })
    }

    private fun initStatus(){
        requireActivity().window?.statusBarColor = ContextCompat.getColor(requireContext(), statusColor())
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
        if (mProgressDialog != null && mProgressDialog?.isShowing == true) {
            mProgressDialog?.cancel()
        }
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
}