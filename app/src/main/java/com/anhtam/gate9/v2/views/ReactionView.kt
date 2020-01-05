package com.anhtam.gate9.v2.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
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
        when(previousState){
            Reaction.None ->{
                // set new reaction
                previousState = value
            }
            value -> {
                // clear reaction
                previousState = Reaction.None
            }
            else -> {
                // clear reaction
                // set new reaction
                previousState = value
            }
        }
        mListener?.invoke(previousState)
    }
}