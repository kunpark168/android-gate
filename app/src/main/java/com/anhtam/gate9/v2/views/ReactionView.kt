package com.anhtam.gate9.v2.views

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.anhtam.gate9.R
import com.anhtam.gate9.session.SessionManager
import com.anhtam.gate9.vo.Reaction
import kotlinx.android.synthetic.main.reaction_view.view.*

class ReactionView @JvmOverloads constructor(context: Context?,
                                                 attrs: AttributeSet? = null,
                                                 defStyle: Int = 0
): LinearLayout(context, attrs, defStyle){

    private var previousState: Reaction = Reaction.None
    private var mListener: ((Reaction)->Unit)? = null
    private var mSessionManager: SessionManager? = null
    private var mLike: Int = 0
    private var mDislike: Int = 0
    private var mLove: Int = 0

    init {
        View.inflate(context, R.layout.reaction_view, this)
        initEvents()
    }

    private fun initEvents(){
        csLike?.setOnClickListener {
            react(Reaction.Like)
        }
        csLove?.setOnClickListener {
            react(Reaction.Love)
        }
        csDisLike?.setOnClickListener {
            react(Reaction.Dislike)
        }
    }

    fun onReactionChange(sessionManager: SessionManager, listener: (Reaction)->Unit){
        mListener = listener
        mSessionManager = sessionManager
    }

    private fun react(value: Reaction){
        if(mSessionManager?.checkLogin() == false) return
        when(previousState){
            Reaction.None ->{
                // set new reaction
                when(value){
                    Reaction.Like -> {
                        imgLike?.setColorFilter(ContextCompat.getColor(context, R.color.color_main_blue), PorterDuff.Mode.MULTIPLY)
                        mLike++
                    }
                    Reaction.Dislike ->{
                        imgDislike?.setColorFilter(ContextCompat.getColor(context, R.color.color_main_blue), PorterDuff.Mode.MULTIPLY)
                        mDislike++
                    }
                    Reaction.Love -> {
                        imgFavorite?.setColorFilter(ContextCompat.getColor(context, R.color.color_main_blue), PorterDuff.Mode.MULTIPLY)
                        mLove++
                    }
                }
                previousState = value
            }
            value -> {
                // clear reaction
                when(previousState){
                    Reaction.Like -> {
                        imgLike?.setColorFilter(ContextCompat.getColor(context, R.color.color_react_grey), PorterDuff.Mode.MULTIPLY)
                        mLike--
                    }
                    Reaction.Dislike ->{
                        imgDislike?.setColorFilter(ContextCompat.getColor(context, R.color.color_react_grey), PorterDuff.Mode.MULTIPLY)
                        mDislike--
                    }
                    Reaction.Love -> {
                        imgFavorite?.setColorFilter(ContextCompat.getColor(context, R.color.color_react_grey), PorterDuff.Mode.MULTIPLY)
                        mLove--
                    }
                }
                previousState = Reaction.None
            }
            else -> {
                // clear reaction
                when(previousState){
                    Reaction.Like -> {
                        imgLike?.setColorFilter(ContextCompat.getColor(context, R.color.color_react_grey), PorterDuff.Mode.MULTIPLY)
                        mLike--
                    }
                    Reaction.Dislike ->{
                        imgDislike?.setColorFilter(ContextCompat.getColor(context, R.color.color_react_grey), PorterDuff.Mode.MULTIPLY)
                        mDislike--
                    }
                    Reaction.Love -> {
                        imgFavorite?.setColorFilter(ContextCompat.getColor(context, R.color.color_react_grey), PorterDuff.Mode.MULTIPLY)
                        mLove--
                    }
                }
                // set new reaction
                when(value){
                    Reaction.Like -> {
                        imgLike?.setColorFilter(ContextCompat.getColor(context, R.color.color_main_blue), PorterDuff.Mode.MULTIPLY)
                        mLike++
                    }
                    Reaction.Dislike ->{
                        imgDislike?.setColorFilter(ContextCompat.getColor(context, R.color.color_main_blue), PorterDuff.Mode.MULTIPLY)
                        mDislike++
                    }
                    Reaction.Love -> {
                        imgFavorite?.setColorFilter(ContextCompat.getColor(context, R.color.color_main_blue), PorterDuff.Mode.MULTIPLY)
                        mLove++
                    }
                }
                previousState = value
            }
        }
        changeLabel()
        mListener?.invoke(previousState)
    }

    private fun changeLabel(){
        tvFavorite?.text = if(previousState == Reaction.Love) mLove.toString() else context.getString(R.string.favorite_label)
        tvLike?.text = if(previousState == Reaction.Like) mLike.toString() else context.getString(R.string.like_label)
        tvDislike?.text = if(previousState == Reaction.Dislike) mDislike.toString() else context.getString(R.string.dislike_label)
    }

    fun initialize(like: Int, dislike: Int, love: Int, reaction: Reaction){
        // set new reaction
        mLike = like
        mDislike = dislike
        mLove = love
        previousState = reaction
        when(reaction){
            Reaction.Like -> {
                imgLike?.setColorFilter(ContextCompat.getColor(context, R.color.color_main_blue), PorterDuff.Mode.MULTIPLY)
            }
            Reaction.Dislike ->{
                imgDislike?.setColorFilter(ContextCompat.getColor(context, R.color.color_main_blue), PorterDuff.Mode.MULTIPLY)
            }
            Reaction.Love -> {
                imgFavorite?.setColorFilter(ContextCompat.getColor(context, R.color.color_main_blue), PorterDuff.Mode.MULTIPLY)
            }
            else ->{

            }
        }
        changeLabel()
    }

}