package com.anhtam.gate9.share.view.donate

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.LinearLayout
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.debounceClick
import kotlinx.android.synthetic.main.layout_custom_donate_dialog.*
import kotlinx.android.synthetic.main.layout_dialog_more.tvCancel


class DonateDialog constructor(context: Context) : Dialog(context, R.style.Theme_Dialog){
    var idPost: String?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_custom_donate_dialog)
        val window = window
        window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        setCancelable(false)
        addControl()
    }

    fun addControl(){
        tvCancel.debounceClick { hide() }

        tv1P.debounceClick { onType(DonateType.P1.type) }
        tv5P.debounceClick { onType(DonateType.P5.type) }
        tv10P.debounceClick { onType(DonateType.P10.type) }
        tv50P.debounceClick { onType(DonateType.P50.type) }
        tv100P.debounceClick { onType(DonateType.P100.type) }
        tv520P.debounceClick { onType(DonateType.P520.type) }
    }

    private fun onType(type: Int){
        etDonate?.setText(type.toString())
        tv1P.setBackgroundDrawable(if(type == DonateType.P1.type)
            context.resources.getDrawable(R.drawable.bg_donate_chosen)
        else context.resources.getDrawable(R.drawable.bg_donate))

        tv5P.setBackgroundDrawable(if(type == DonateType.P5.type)
            context.resources.getDrawable(R.drawable.bg_donate_chosen)
        else context.resources.getDrawable(R.drawable.bg_donate))

        tv10P.setBackgroundDrawable(if(type == DonateType.P10.type)
            context.resources.getDrawable(R.drawable.bg_donate_chosen)
        else context.resources.getDrawable(R.drawable.bg_donate))

        tv50P.setBackgroundDrawable(if(type == DonateType.P50.type)
            context.resources.getDrawable(R.drawable.bg_donate_chosen)
        else context.resources.getDrawable(R.drawable.bg_donate))

        tv100P.setBackgroundDrawable(if(type == DonateType.P100.type)
            context.resources.getDrawable(R.drawable.bg_donate_chosen)
        else context.resources.getDrawable(R.drawable.bg_donate))

        tv520P.setBackgroundDrawable(if(type == DonateType.P520.type)
            context.resources.getDrawable(R.drawable.bg_donate_chosen)
        else context.resources.getDrawable(R.drawable.bg_donate))

        tv1P.setTextColor(if(type == DonateType.P1.type) context.resources.getColor(R.color.colorWhite)
        else context.resources.getColor(R.color.colorHome))

        tv5P.setTextColor(if(type == DonateType.P5.type) context.resources.getColor(R.color.colorWhite)
        else context.resources.getColor(R.color.colorHome))

        tv10P.setTextColor(if(type == DonateType.P10.type) context.resources.getColor(R.color.colorWhite)
        else context.resources.getColor(R.color.colorHome))

        tv50P.setTextColor(if(type == DonateType.P50.type) context.resources.getColor(R.color.colorWhite)
        else context.resources.getColor(R.color.colorHome))

        tv100P.setTextColor(if(type == DonateType.P100.type) context.resources.getColor(R.color.colorWhite)
        else context.resources.getColor(R.color.colorHome))

        tv520P.setTextColor(if(type == DonateType.P520.type) context.resources.getColor(R.color.colorWhite)
        else context.resources.getColor(R.color.colorHome))

    }


}