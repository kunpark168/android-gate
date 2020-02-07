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

import com.anhtam.domain.v2.Gamev1;
import com.anhtam.gate9.R;

public class RatingGame extends ConstraintLayout {
    private Context mContext;
    private Resources res;
    private String rating = "0";
    private String countingUser = "0";
    private String titleGame = "";
    private TextView tvTitleGame, tvRatingCount, tvCounting, tvCountingFollow;
    private ImageView imgStar1, imgStar2, imgStar3, imgStar4, imgStar5;
    private String follow, post;
    private Gamev1 game;
    public RatingGame(Context context) {
        super(context);
        addView(context, null);
    }

    public RatingGame(Context context, AttributeSet attrs) {
        super(context, attrs);
        addView(context, attrs);
    }

    public RatingGame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addView(context, attrs);
    }

    private void addView(Context context, AttributeSet attrs) {
        this.mContext = context;
        View view = inflate(getContext(), R.layout.layout_rating_game, null);
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
        tvTitleGame = view.findViewById(R.id.tvTitleGame);
        imgStar1 = view.findViewById(R.id.imgStar1);
        imgStar2 = view.findViewById(R.id.imgStar2);
        imgStar3 = view.findViewById(R.id.imgStar3);
        imgStar4 = view.findViewById(R.id.imgStar4);
        imgStar5 = view.findViewById(R.id.imgStar5);
        tvRatingCount = view.findViewById(R.id.tvRatingCount);
        tvCounting = view.findViewById(R.id.tvCounting);
        tvCountingFollow = view.findViewById(R.id.tvCountingFollow);
    }

    private void setData (){
        try {
            tvTitleGame.setText(titleGame);
            tvRatingCount.setText(Float.parseFloat(rating) / 2 + "  /");
            tvCounting.setText(countingUser);
            tvCountingFollow.setText("Follow: " + follow + "  |  " + "Post: " + post);
            if (rating != null) {
                float ratingFl = Float.parseFloat(rating) / 2;
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
            titleGame = ta.getString(R.styleable.RatingGame_titleGame);
            countingUser = ta.getString(R.styleable.RatingGame_countingUser);
            follow = ta.getString(R.styleable.RatingGame_follow);
            post = ta.getString(R.styleable.RatingGame_post);
        } finally {
            ta.recycle();
        }
    }

    public RatingGame setRating(String rating) {
        this.rating = rating;
        return this;
    }

    public void setGameInformation(Gamev1 game) {
        this.game = game;
        setDataGame ();
    }

    private void setDataGame (){
//       try {
//           if (game.getRating() != null) {
//               rating = game.getRating().toString();
//           }
//           if (game.getName() != null) titleGame = game.getName();
//           if (game.getTotalPost() != null) post = game.getTotalPost().toString();
//           if (game.getFollower() != null) follow = game.getFollower().toString();
//           if (game.getVote() != null && game.getVote().getTotalVote() != null)
//               countingUser = game.getVote().getTotalVote().toString();
//           setData();
//       } catch (Exception ex){
//           ex.printStackTrace();
//       }
    }
}
