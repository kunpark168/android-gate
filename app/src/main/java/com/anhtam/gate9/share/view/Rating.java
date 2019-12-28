package com.anhtam.gate9.share.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.anhtam.gate9.R;

public class Rating extends ConstraintLayout {

    private Context mContext;
    private Resources res;
    private ImageView imgStar1, imgStar2, imgStar3, imgStar4, imgStar5, imgUser;
    private String rating = "0";
    private String totalVote = "0";
    private TextView tvPoint, tvCountingUser;
    private boolean hasInfor = true;
    public Rating(Context context) {
        super(context);
        addView(context, null);
    }

    public Rating(Context context, AttributeSet attrs) {
        super(context, attrs);
        addView(context, attrs);
    }

    public Rating(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addView(context, attrs);
    }

    private void addView(Context context, AttributeSet attrs) {
        this.mContext = context;
        View view = inflate(getContext(), R.layout.layout_rating, null);
        res = context.getResources();
        initAtrrs (attrs);
        initView(view);
        setData ();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        addView(view);
        initControls();
    }

    private void initView (View view){
        imgStar1 = view.findViewById(R.id.imgStar1);
        imgStar2 = view.findViewById(R.id.imgStar2);
        imgStar3 = view.findViewById(R.id.imgStar3);
        imgStar4 = view.findViewById(R.id.imgStar4);
        imgStar5 = view.findViewById(R.id.imgStar5);
        tvPoint = view.findViewById(R.id.tvPoint);
        tvCountingUser = view.findViewById(R.id.tvCountingUser);
        imgUser = view.findViewById(R.id.imgUser);

    }

    private void setData (){
        try {
            if (rating != null) {
                float ratingFl = Float.parseFloat(rating) / 2;
                tvPoint.setText(rating + " /");
                tvCountingUser.setText(totalVote);
                tvPoint.setVisibility(hasInfor ? VISIBLE : GONE);
                tvCountingUser.setVisibility(hasInfor ? VISIBLE : GONE);
                imgUser.setVisibility(hasInfor ? VISIBLE : GONE);

                imgStar1.setImageDrawable(ratingFl <= 0 ? res.getDrawable(R.drawable.ic_star_non) :
                        ratingFl > 0 && ratingFl < 1? res.getDrawable(R.drawable.ic_half_star) : res.getDrawable(R.drawable.ic_star_done));

                imgStar2.setImageDrawable(ratingFl <= 1 ? res.getDrawable(R.drawable.ic_star_non) :
                        ratingFl > 1 && ratingFl < 2? res.getDrawable(R.drawable.ic_half_star) : res.getDrawable(R.drawable.ic_star_done));

                imgStar3.setImageDrawable(ratingFl <= 2 ? res.getDrawable(R.drawable.ic_star_non) :
                        ratingFl > 2 && ratingFl < 3? res.getDrawable(R.drawable.ic_half_star) : res.getDrawable(R.drawable.ic_star_done));

                imgStar4.setImageDrawable(ratingFl <= 3 ? res.getDrawable(R.drawable.ic_star_non) :
                        ratingFl > 3 && ratingFl < 4? res.getDrawable(R.drawable.ic_half_star) : res.getDrawable(R.drawable.ic_star_done));

                imgStar5.setImageDrawable(ratingFl <= 4 ? res.getDrawable(R.drawable.ic_star_non) :
                        ratingFl > 4 && ratingFl < 5? res.getDrawable(R.drawable.ic_half_star) : res.getDrawable(R.drawable.ic_star_done));
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void initControls (){
    }

    private void initAtrrs (AttributeSet attrs){
        if (attrs == null)
            return;
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.RatingGame, 0, 0);
        try {
            rating = ta.getString(R.styleable.RatingGame_rating);
            hasInfor = ta.getBoolean(R.styleable.RatingGame_isShowInfor, true);
        } finally {
            ta.recycle();
        }
    }

    public void init (String rating, String totalVote){
        this.rating = rating;
        this.totalVote = totalVote;
        setData();
    }

    public void setColor (boolean isWhite){
        if (isWhite){
            tvPoint.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            tvCountingUser.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            imgUser.setColorFilter(ContextCompat.getColor(mContext,
                    R.color.colorWhite));
        }
    }

}
