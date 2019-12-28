package com.anhtam.gate9.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.anhtam.gate9.R;

public class DialogProgressUtils extends Dialog {
    private Context mContext;
    private OnCancelListener mOnCancelListener;

    public DialogProgressUtils (Context context, boolean isCancel) {
        super(context);
        mContext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCancelable(isCancel);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void setOnCancelListener(OnCancelListener onCancelListener) {
        mOnCancelListener = onCancelListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_progress);

        ImageView img = findViewById(R.id.imgProgress);
        img.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.rotate_loading));
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mOnCancelListener != null) {
            mOnCancelListener.onCancel(this);
        }
    }
}
