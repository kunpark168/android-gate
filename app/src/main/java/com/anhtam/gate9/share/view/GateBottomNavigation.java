package com.anhtam.gate9.share.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.anhtam.gate9.R;
import com.anhtam.gate9.v2.newfeed.IMainNavigator;

public class GateBottomNavigation extends ConstraintLayout {

    private Context mContext;
    private ImageView imgNewFeed, imgFollow, imgProfile, imgGame, imgPost;
    private TextView tvNewFeed, tvFollow, tvProfile, tvGame;
    private IMainNavigator mListener;
    private boolean isShow = true;
    private ConstraintLayout csOption;
    private ConstraintLayout llOption, layoutPostText, layoutPostImage, layoutPostVideo, layoutPostTopic;
    private ConstraintLayout llNewFeed, llFollow, llProfile, llGame;

    public GateBottomNavigation(Context context) {
        super(context);
        addView(context);
    }

    public GateBottomNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
        addView(context);
    }

    public GateBottomNavigation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addView(context);
    }

    private void addView(Context context) {
        this.mContext = context;
        View view = inflate(getContext(), R.layout.layout_bottom_navigation, null);
        initView(view);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        addView(view);
        initControls();

    }

    private void initView(View view) {
        imgNewFeed = view.findViewById(R.id.imgNewFeed);
        imgFollow = view.findViewById(R.id.imgFollow);
        imgProfile = view.findViewById(R.id.imgProfile);
        imgGame = view.findViewById(R.id.imgGame);

        tvNewFeed = view.findViewById(R.id.tvNewFeed);
        tvFollow = view.findViewById(R.id.tvFollow);
        tvProfile = view.findViewById(R.id.tvProfile);
        tvGame = view.findViewById(R.id.tvGame);
        imgPost = view.findViewById(R.id.imgPost);
        csOption = view.findViewById(R.id.csOption);
        llOption = view.findViewById(R.id.llOption);
        layoutPostText = view.findViewById(R.id.layoutPostText);
        layoutPostImage = view.findViewById(R.id.layoutPostImage);
        layoutPostVideo = view.findViewById(R.id.layoutPostVideo);
        layoutPostTopic = view.findViewById(R.id.layoutPostTopic);
        llNewFeed = view.findViewById(R.id.llNewFeed);
        llFollow = view.findViewById(R.id.llFollow);
        llProfile = view.findViewById(R.id.llProfile);
        llGame = view.findViewById(R.id.llGame);
        llGame = view.findViewById(R.id.llGame);
    }

    private void initControls() {
        action(llNewFeed, this::onNewFeed);

        action(llFollow, this::onFollow);

        action(llProfile, this::onProfile);

        action(llGame, this::onGate);

        action(imgPost, this::onPost);
        action(layoutPostText, this::newPost);
        action(layoutPostImage, this::newImage);
        action(layoutPostVideo, this::newImage);
        action(layoutPostTopic, this::newRate);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void action(View v, Runnable runnable) {
        v.setOnTouchListener((view1, e) -> {
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.setAlpha(0.5f);
                    break;
                case MotionEvent.ACTION_UP:
                    v.setAlpha(1.0f);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    v.setAlpha(1.0f);
                    break;
            }
            return false;
        });

        v.setOnClickListener(view -> runnable.run());
    }

    public void setListener(IMainNavigator listener) {
        this.mListener = listener;
        onNewFeed();
    }

    private void onNewFeed() {
        mListener.onNewFeed();

        imgNewFeed.setImageDrawable(getResources().getDrawable(R.drawable.ic_new_feed_blue));
        imgFollow.setImageDrawable(getResources().getDrawable(R.drawable.ic_follow_grey));
        imgProfile.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_grey));
        imgGame.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_grey));

        tvNewFeed.setTextColor(getResources().getColor(R.color.colorHome));
        tvFollow.setTextColor(getResources().getColor(R.color.colorGray));
        tvProfile.setTextColor(getResources().getColor(R.color.colorGray));
        tvGame.setTextColor(getResources().getColor(R.color.colorGray));
    }

    private void onFollow() {
        mListener.onFollow();

        imgNewFeed.setImageDrawable(getResources().getDrawable(R.drawable.ic_new_feed_grey));
        imgFollow.setImageDrawable(getResources().getDrawable(R.drawable.ic_follow_blue));
        imgProfile.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_grey));
        imgGame.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_grey));

        tvNewFeed.setTextColor(getResources().getColor(R.color.colorGray));
        tvFollow.setTextColor(getResources().getColor(R.color.colorHome));
        tvProfile.setTextColor(getResources().getColor(R.color.colorGray));
        tvGame.setTextColor(getResources().getColor(R.color.colorGray));
    }

    private void onProfile() {
        mListener.onUser();

        imgNewFeed.setImageDrawable(getResources().getDrawable(R.drawable.ic_new_feed_grey));
        imgFollow.setImageDrawable(getResources().getDrawable(R.drawable.ic_follow_grey));
        imgProfile.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_blue));
        imgGame.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_grey));

        tvNewFeed.setTextColor(getResources().getColor(R.color.colorGray));
        tvFollow.setTextColor(getResources().getColor(R.color.colorGray));
        tvProfile.setTextColor(getResources().getColor(R.color.colorHome));
        tvGame.setTextColor(getResources().getColor(R.color.colorGray));
    }

    private void onGate() {
        mListener.onGate();

        imgNewFeed.setImageDrawable(getResources().getDrawable(R.drawable.ic_new_feed_grey));
        imgFollow.setImageDrawable(getResources().getDrawable(R.drawable.ic_follow_grey));
        imgProfile.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_grey));
        imgGame.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_blue));

        tvNewFeed.setTextColor(getResources().getColor(R.color.colorGray));
        tvFollow.setTextColor(getResources().getColor(R.color.colorGray));
        tvProfile.setTextColor(getResources().getColor(R.color.colorGray));
        tvGame.setTextColor(getResources().getColor(R.color.colorHome));
    }

    private void onPost() {
        if (isShow) {
            showOption();
        } else {
            hideOption();
        }
        isShow = !isShow;
    }

    private void newPost (){
        hideOption();
        mListener.onNewPost();
    }

    private void newImage (){
        hideOption();
        mListener.onNewImagePost();
    }

    private void newRate (){
        hideOption();
        mListener.onnNewRate();
    }

    private void showOption (){
        csOption.setVisibility(VISIBLE);
        imgPost.animate().rotation(135f).start();
        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.slide_up_enter);
        llOption.startAnimation(anim);
    }

    private void hideOption (){
        csOption.setVisibility(GONE);
        imgPost.animate().rotation(0f).start();
    }
}
