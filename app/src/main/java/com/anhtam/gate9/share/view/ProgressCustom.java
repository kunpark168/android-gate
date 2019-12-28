package com.anhtam.gate9.share.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.anhtam.gate9.R;

public class ProgressCustom extends RelativeLayout {
    private Context mContext;
    private ImageView imgProgress;

    public ProgressCustom(Context context) {
        super(context);
        addView(context);
    }

    public ProgressCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        addView(context);
    }

    public ProgressCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addView(context);
    }

    private void addView (Context mContext){
        this.mContext = mContext;
        View view = inflate(getContext(), R.layout.layout_progress, null);
        imgProgress = view.findViewById(R.id.imgProgress);
        show(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        addView(view);
    }

    public void show (Context context){
        imgProgress.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate_loading));
    }
}
