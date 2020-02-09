package com.anhtam.gate9.v2.shared.views

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anhtam.gate9.v2.main.DaggerNavigationFragment

abstract class AbstractVisibleFragment(
        @LayoutRes private val layoutId: Int
) : DaggerNavigationFragment(layoutId){

    private val mViewModel: VisibleViewModel by viewModels()
    private var mIsAttach: Boolean = false
    private var awaitingRunnable: Runnable? = null

    open fun onUiVisibleChange(isUiVisible: Boolean){}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerUIVisible()
    }

    private fun observerUIVisible(){
        mViewModel.mIsUIVisible.observe(viewLifecycleOwner, Observer { isUiVisible ->
            isUiVisible?.run {
                onUiVisibleChange(isUiVisible)
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mIsAttach = true
        awaitingRunnable?.run()
    }

    fun changeVisible(visible: Boolean){
        if(mIsAttach) {
            mViewModel.mIsUIVisible.value = visible
        }
        else {
            awaitingRunnable = Runnable {
                mViewModel.mIsUIVisible.value = visible
            }
        }
    }

}