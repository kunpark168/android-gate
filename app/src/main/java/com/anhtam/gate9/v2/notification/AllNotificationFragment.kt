package com.anhtam.gate9.v2.notification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.NotificationAdapter
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.post_detail.DetailPostScreen
import kotlinx.android.synthetic.main.fragment_all_notification.*
import of.bum.network.helper.Resource
import javax.inject.Inject

class AllNotificationFragment : DaggerNavigationFragment(R.layout.fragment_all_notification) {

    private val viewModel: NotificationViewModel by viewModels { vmFactory }
    @Inject lateinit var mAdapter: NotificationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        initView()
        observerNotification()
    }

    private fun observerNotification() {
        viewModel.data.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    hideProgress()
                    val notifications = it.data
                    if (notifications.isNullOrEmpty()) {
                        mAdapter.loadMoreEnd()
                    } else {
                        if (viewModel.mPage == 0) {
                            mAdapter.setNewData(notifications)
                        } else {
                            mAdapter.addData(notifications)
                        }
                        mAdapter.loadMoreComplete()
                    }
                }
                is Resource.Error -> {
                    hideProgress()
                    mAdapter.loadMoreFail()
                }
                else -> {}
            }
        })
    }

    private fun loadData() {
        showProgress()
        viewModel.loadData(refresh = true)
    }

    private fun initView() {
        rvNotification?.adapter = mAdapter
        rvNotification?.layoutManager = LinearLayoutManager(context)
        rvNotification?.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        mAdapter.setOnLoadMoreListener({
            viewModel.loadData()
        }, rvNotification)
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        mAdapter.setOnItemClickListener { _, _, position ->
            val id = mAdapter.data[position].commentId ?: return@setOnItemClickListener
            navigation?.addFragment(DetailPostScreen.newInstance(id.toLong(), DetailPostScreen.Detail.POST))
        }
        mAdapter.setOnItemChildClickListener { _, view, position ->
            if(view.id == R.id.turnOffTextView) mAdapter.remove(position)
        }
    }

    companion object {
        fun newInstance(): AllNotificationFragment {
            return AllNotificationFragment()
        }
    }
}
