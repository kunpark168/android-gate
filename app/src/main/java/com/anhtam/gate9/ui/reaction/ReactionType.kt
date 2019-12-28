package com.anhtam.gate9.ui.reaction

enum class ReactionType(val type: Int){
    SHARE(0), LOVE(1), LIKE(2), DISLIKE(3), COMMENT(4);
    companion object{
        fun getType(type: Int): ReactionType{
            for(reaction in values()){
                if(reaction.type == type)
                    return reaction
            }
            return SHARE
        }
    }
}