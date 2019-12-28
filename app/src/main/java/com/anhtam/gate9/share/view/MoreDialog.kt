package com.anhtam.gate9.share.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.LinearLayout
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.debounceClick
import kotlinx.android.synthetic.main.layout_dialog_more.*


class MoreDialog constructor(context: Context, mListener: IMore) : Dialog(context, R.style.Theme_Dialog){
    var idPost: String?= null
    var listenter: IMore?= null

    init {
        this.listenter = mListener
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_dialog_more)
        val window = window
        window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        setCancelable(false)
        addControl()
    }

    fun addControl(){
        tvCancel.debounceClick { hide() }
        tvReport.debounceClick {
            listenter?.let {
                listenter?.onreport()
                dismiss()
            }
        }
    }

    interface IMore{
        fun onreport()
    }
}