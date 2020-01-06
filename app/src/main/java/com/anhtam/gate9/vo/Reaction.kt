package com.anhtam.gate9.vo

import java.lang.IllegalArgumentException

sealed class Reaction {
    object Love: Reaction()
    object Like: Reaction()
    object Dislike: Reaction()
    object None: Reaction()

    companion object{
        fun react(value: Int): Reaction{
            return when(value) {
                0 -> None
                1 -> Like
                2 -> Dislike
                3 -> Love
                else -> {
                    throw IllegalArgumentException()
                }
            }
        }

        fun value(reaction: Reaction): Int{
            return when(reaction){
                None -> 0
                Like -> 1
                Dislike -> 2
                Love -> 3
            }
        }
    }
}