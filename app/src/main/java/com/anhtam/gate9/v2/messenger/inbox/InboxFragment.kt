package com.anhtam.gate9.v2.messenger.inbox

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.inbox_fragment.*

class InboxFragment : DaggerNavigationFragment() {

    companion object{
        fun newInstance() = InboxFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.inbox_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val unwrappedContext = context ?: return
            activity?.window?.statusBarColor = ContextCompat.getColor(unwrappedContext, R.color.colorIndicator)
        }
        tvCancel?.setOnClickListener {
            navigation?.back()
        }
        tvPost?.setOnClickListener {
            Toast.makeText(context, "Đã gửi", Toast.LENGTH_SHORT).show()
            navigation?.back()
        }
    }
}