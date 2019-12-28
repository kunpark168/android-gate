package com.anhtam.gate9.share.view.donate

enum class DonateType(val type: Int){
    P1(1), P5(5), P10(10), P50(50), P100(100), P520(520);
    companion object{
        fun gettype(type: Int): DonateType{
            for(typeDonate in values()){
                if(typeDonate.type == type)
                    return typeDonate
            }
            return P1
        }
    }
}