package com.anhtam.gate9.share.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.Constraints;

import com.anhtam.gate9.R;

public class DoubleDialog  {
    private Context mContext;
    private Dialog dialog;

    public DoubleDialog(Context mContext) {
        this.mContext = mContext;
        addView();
    }

    private void addView (){
        dialog = new Dialog(mContext);
       // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.setContentView(R.layout.layout_dialog_one_button);
        dialog.setCancelable(false);

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_one_button, null);
        view.setLayoutParams(new Constraints.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dialog.setContentView(view);
        dialog.getWindow().setGravity(Gravity.CENTER);
    }

    public void show (String title, String message){
        dialog.show();
    }

}
