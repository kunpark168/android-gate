package com.anhtam.gate9.v2.main

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.anhtam.gate9.R
import com.anhtam.gate9.navigation.toobar.IdLayoutFactory
import com.anhtam.gate9.navigation.toobar.NavigationLayoutFactory

abstract class NavigationScreen : DaggerNavigationFragment() {

    @LayoutRes abstract fun layoutOrigin(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return NavigationLayoutFactory(true, IdLayoutFactory(layoutOrigin())).produceLayout(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_chat_search_more, menu)
    }
}