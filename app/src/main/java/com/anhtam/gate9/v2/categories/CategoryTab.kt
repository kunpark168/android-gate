package com.anhtam.gate9.v2.categories

enum class CategoryTab constructor(var tab: Int) {
    VIDEO(0), THEME(1), SPECIAL(2), EVENT(3);
    companion object{
        fun getCategoryTab(tab: Int): CategoryTab{
            for(categoryTab in values()){
                if (categoryTab.tab == tab){
                    categoryTab
                }
            }
            return VIDEO
        }
    }
}