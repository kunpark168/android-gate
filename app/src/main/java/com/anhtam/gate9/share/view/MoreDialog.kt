package com.anhtam.gate9.share.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.LinearLayout
import com.anhtam.gate9.R
import kotlinx.android.synthetic.main.layout_dialog_more.*


class MoreDialog constructor(context: Context, mListener: IMore) : Dialog(context, R.style.Theme_Dialog){
    var idPost: String?= null
    private var listener: IMore?= null

    init {
        this.listener = mListener
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_dialog_more)
        val window = window
        window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        setCancelable(false)
        initEvents()
    }


    private fun initEvents(){
        tvCancel.setOnClickListener { hide() }
        tvReport.setOnClickListener {
            listener?.onReport()
            dismiss()
        }
        deleteLayout?.setOnClickListener { listener?.delete() }
        editLayout?.setOnClickListener { listener?.update() }
    }

    interface IMore{
        fun onReport()
        fun delete()
        fun update()
    }


}