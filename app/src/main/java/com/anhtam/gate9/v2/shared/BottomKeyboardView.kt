package com.anhtam.gate9.v2.shared

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.anhtam.gate9.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_bar_type_layout.view.*

class BottomKeyboardView @JvmOverloads constructor(
        context: Context?,
        attrs: AttributeSet? = null,
        defStyle: Int = 0): LinearLayout(context, attrs, defStyle){

    private enum class State{
        Keyboard, Gallery, None
    }

    private var mState: State = State.None

    init {
        View.inflate(context, R.layout.bottom_bar_type_layout, this)
        initViews()
        initEvents()
    }

    private fun initViews(){
        BottomSheetBehavior.from(bottomSheetBehavior)
    }

    private fun initEvents(){
        imgFrameLayout?.setOnClickListener {
//            when(mState){
//                State.Keyboard -> TODO()
//                State.Gallery -> TODO()
//                State.None -> TODO()
//            }

        }
    }


}