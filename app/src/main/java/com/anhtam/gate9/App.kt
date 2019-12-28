package com.anhtam.gate9

import android.content.Context
import androidx.multidex.MultiDex
import com.anhtam.gate9.di.DaggerAppComponent
import com.anhtam.gate9.utils.NetworkUtils
import com.orhanobut.hawk.Hawk
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class App: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        init()
    }

    private fun init() {
        initLogger()
        initStorage()
        initFont()
    }

    private fun initFont() {

    }

    private fun initLogger() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initStorage() {
        Hawk.init(mContext).build()
    }

    /*
     * 64K Method
     */
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object{
        private lateinit var mContext: Context
        fun isInternetAvailable(): Boolean {
            return NetworkUtils.isNetworkConnected(mContext)
        }
    }

}