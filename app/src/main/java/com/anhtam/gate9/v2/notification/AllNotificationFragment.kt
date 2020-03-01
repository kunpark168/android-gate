package com.anhtam.gate9.v2.notification


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.NotificationAdapter
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_all_notification.*

class AllNotificationFragment : DaggerNavigationFragment(R.layout.fragment_all_notification) {

    private val viewModel: NotificationViewModel by viewModels { vmFactory }
    private lateinit var mAdapter: NotificationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        mAdapter = NotificationAdapter(Glide.with(this))
        mAdapter.addList(viewModel.notifications())
        rvNotification?.adapter = mAdapter
        rvNotification?.layoutManager = LinearLayoutManager(context)
        rvNotification?.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

    companion object {
        fun newInstance(): AllNotificationFragment {
            return AllNotificationFragment()
        }
    }
}
