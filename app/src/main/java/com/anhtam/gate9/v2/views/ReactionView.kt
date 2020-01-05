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
import kotlinx.android.synthetic.main.splash_screen.view.*

class ReactionView @JvmOverloads constructor(context: Context?,
                                                 attrs: AttributeSet? = null,
                                                 defStyle: Int = 0
): LinearLayout(context, attrs, defStyle){

    private var previousState: Reaction = Reaction.None
    private var mListener: ((Reaction)->Unit)? = null
    private var mSessionManager: SessionManager? = null

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
                    }
                    Reaction.Dislike ->{
                        imgDislike?.setColorFilter(ContextCompat.getColor(context, R.color.color_main_blue), PorterDuff.Mode.MULTIPLY)
                    }
                    Reaction.Love -> {
                        imgFavorite?.setColorFilter(ContextCompat.getColor(context, R.color.color_main_blue), PorterDuff.Mode.MULTIPLY)
                    }
                }
                previousState = value
            }
            value -> {
                // clear reaction
                when(previousState){
                    Reaction.Like -> {
                        imgLike?.setColorFilter(ContextCompat.getColor(context, R.color.color_react_grey), PorterDuff.Mode.MULTIPLY)
                    }
                    Reaction.Dislike ->{
                        imgDislike?.setColorFilter(ContextCompat.getColor(context, R.color.color_react_grey), PorterDuff.Mode.MULTIPLY)
                    }
                    Reaction.Love -> {
                        imgFavorite?.setColorFilter(ContextCompat.getColor(context, R.color.color_react_grey), PorterDuff.Mode.MULTIPLY)
                    }
                }
                previousState = Reaction.None
            }
            else -> {
                // clear reaction
                when(previousState){
                    Reaction.Like -> {
                        imgLike?.setColorFilter(ContextCompat.getColor(context, R.color.color_react_grey), PorterDuff.Mode.MULTIPLY)
                    }
                    Reaction.Dislike ->{
                        imgDislike?.setColorFilter(ContextCompat.getColor(context, R.color.color_react_grey), PorterDuff.Mode.MULTIPLY)
                    }
                    Reaction.Love -> {
                        imgFavorite?.setColorFilter(ContextCompat.getColor(context, R.color.color_react_grey), PorterDuff.Mode.MULTIPLY)
                    }
                }
                // set new reaction
                when(value){
                    Reaction.Like -> {
                        imgLike?.setColorFilter(ContextCompat.getColor(context, R.color.color_main_blue), PorterDuff.Mode.MULTIPLY)
                    }
                    Reaction.Dislike ->{
                        imgDislike?.setColorFilter(ContextCompat.getColor(context, R.color.color_main_blue), PorterDuff.Mode.MULTIPLY)
                    }
                    Reaction.Love -> {
                        imgFavorite?.setColorFilter(ContextCompat.getColor(context, R.color.color_main_blue), PorterDuff.Mode.MULTIPLY)
                    }
                }
                previousState = value
            }
        }
        mListener?.invoke(previousState)
    }

    fun initReact(reaction: Reaction){
        // set new reaction
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
    }

}