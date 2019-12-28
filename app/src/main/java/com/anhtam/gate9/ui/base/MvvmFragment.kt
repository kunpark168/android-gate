package com.anhtam.gate9.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.anhtam.gate9.navigation.NavigationFragment
import com.anhtam.gate9.v2.main.DaggerNavigationFragment

abstract class MvvmFragment : DaggerNavigationFragment() {

    @LayoutRes abstract fun getLayoutRes(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        loadData()
        initView()
        initEvents()
        observer()
    }

    abstract fun loadData()
    abstract fun initView()
    abstract fun observer()
    abstract fun initEvents()


}