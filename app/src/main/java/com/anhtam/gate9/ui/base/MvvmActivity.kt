package com.anhtam.gate9.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes

abstract class MvvmActivity
    : BaseActivity() {

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        init()
    }

    open fun init() {
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