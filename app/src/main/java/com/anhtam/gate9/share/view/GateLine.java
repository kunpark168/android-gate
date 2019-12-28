package com.anhtam.gate9.share.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.anhtam.gate9.R;

public class GateLine extends ConstraintLayout {

    private Context mContext;
    private TextView tvTitle, tvContent;
    private String title = "", content ="";

    public GateLine(Context context) {
        super(context);
        addView(context, null);
    }

    public GateLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        addView(context, attrs);
    }

    public GateLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addView(context, attrs);
    }

    private void addView(Context context, AttributeSet attrs) {
        this.mContext = context;
        View view = inflate(getContext(), R.layout.custom_line_profile, null);
        initAtrrs (attrs);
        initView(view);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        addView(view);
    }

    private void initView (View view){
        tvTitle = view.findViewById(R.id.tvTitle);
        tvContent = view.findViewById(R.id.tvContent);

    }

    private void setData (){
        if (title != null && content != null) {
            tvTitle.setText(title);
            tvContent.setText(content);
        }
    }

    private void initAtrrs (AttributeSet attrs){
        if (attrs == null)
            return;
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.RatingGame, 0, 0);
        try {
            title = ta.getString(R.styleable.RatingGame_follow);
            content = ta.getString(R.styleable.RatingGame_post);
            setData();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void init (String title, String content){
        this.title = title;
        this.content = content;
        setData();
    }

    public void setColorText(int colorText) {
        tvContent.setTextColor(colorText);
    }
}
