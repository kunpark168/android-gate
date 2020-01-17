package com.anhtam.gate9.v2.shared.keyboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anhtam.gate9.R
import kotlinx.android.synthetic.main.abstract_bottom_keyboard_fragment.*

class AbstractBottomKeyboardFragment : Fragment(){

    private lateinit var mViewModel: BottomKeyboardViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.abstract_bottom_keyboard_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        mViewModel = ViewModelProviders.of(this)[BottomKeyboardViewModel::class.java]
        initEvents()
        observer()
    }

    private fun initEvents(){
        selectImageView?.setOnClickListener { mViewModel.mState.value = State.SelectImage }
        messageEditText?.setOnFocusChangeListener { _, hasFocus -> mViewModel.mState.value = State.Type }
        expandFrameLayout?.setOnClickListener {
            featureLayout?.visibility = View.VISIBLE
            expandFrameLayout?.visibility = View.GONE
        }
    }

    private fun observer(){
        mViewModel.mState.observe(viewLifecycleOwner, Observer {
            updateUI()
        })
    }

    private fun updateUI(){
        val unwrapContext = context ?: return
        when(mViewModel.mState.value ?: State.None){
            State.SelectImage -> {
                expandFrameLayout?.clearFocus()
                selectImageView?.setColorFilter(ContextCompat.getColor(unwrapContext, R.color.color_main_blue))
                featureLayout?.visibility = View.VISIBLE
                expandFrameLayout?.visibility = View.GONE
            }
            State.Type -> {
                selectImageView?.setColorFilter(ContextCompat.getColor(unwrapContext, R.color.color_default_icon))
                featureLayout?.visibility = View.GONE
                expandFrameLayout?.visibility = View.VISIBLE
            }
            State.None -> {

            }
        }
    }

    internal enum class State{
        SelectImage, Type, None
    }
}