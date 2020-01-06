package com.anhtam.gate9.vo

class IllegalReturn(
        _message: String = DEFAULT_MESSAGE
) : Exception(_message){
    companion object{
        const val DEFAULT_MESSAGE = "Server return wrong format"
    }
}