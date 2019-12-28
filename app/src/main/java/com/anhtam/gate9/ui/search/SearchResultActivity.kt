package com.anhtam.gate9.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anhtam.gate9.R

abstract class SearchResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
    }
}
