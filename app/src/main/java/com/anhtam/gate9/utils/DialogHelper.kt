package com.anhtam.gate9.utils

import android.app.Dialog
import android.content.Context

class DialogHelper {
  /*  fun showDialog(type: DialogType): Dialog {
        when(type){
            DialogType.SINGLE -> {

            }
        }
    }

    fun createSingleDialog(context: Context) : Dialog {
        val dialog:

        // custom dialog
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.custom)
        dialog.setTitle("Title...")

        // set the custom dialog components - text, image and button
        val text = dialog.findViewById<View>(R.id.text) as TextView
        text.setText("Android custom dialog example!")
        val image = dialog.findViewById<View>(R.id.image) as ImageView
        image.setImageResource(R.drawable.ic_launcher)

        val dialogButton = dialog.findViewById<View>(R.id.dialogButtonOK) as Button
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(object : OnClickListener() {
            fun onClick(v: View) {
                dialog.dismiss()
            }
        })

        dialog.show()
    }*/
}

enum class DialogType {
    SINGLE, DOUBLE, PROGRESS
}