package com.anhtam.gate9.v2.messenger.inbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.inbox_fragment.*

class InboxFragment : DaggerNavigationFragment(R.layout.inbox_fragment) {

    companion object{
        fun newInstance() = InboxFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvCancel?.setOnClickListener {
            navigation?.back()
        }
        tvPost?.setOnClickListener {
            Toast.makeText(context, "Đã gửi", Toast.LENGTH_SHORT).show()
            navigation?.back()
        }
    }
}