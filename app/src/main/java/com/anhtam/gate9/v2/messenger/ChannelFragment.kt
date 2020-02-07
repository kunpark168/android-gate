package com.anhtam.gate9.v2.messenger

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.chat.Channel
import com.anhtam.gate9.adapter.chat.ChannelAdapter
import com.anhtam.gate9.v2.lib.loadImage
import com.anhtam.gate9.v2.lib.then
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.messenger.chat.ChatFragment
import com.anhtam.gate9.v2.messenger.inbox.InboxFragment
import kotlinx.android.synthetic.main.channel_fragment.*
import of.bum.network.helper.Resource

class ChannelFragment : DaggerNavigationFragment(R.layout.channel_fragment) {

    companion object {
        fun newInstance() = ChannelFragment()
    }

    private val viewModel: ChannelViewModel by viewModels { vmFactory }
    private var mAdapter: ChannelAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.users.observe(viewLifecycleOwner, Observer {
            (it is Resource.Success).then {
                shimmer?.stopShimmerAnimation()
                shimmer?.visibility = View.GONE
                rv?.visibility = View.VISIBLE
                val channels = mutableListOf<Channel>()
                val users = it.data ?: return@then
                for (item in users.indices) {
                    val channel = Channel(users[item], "New message", "19:20", item % 2 == 0)
                    channels.add(channel)
                }
                mAdapter?.setNewData(channels)
            }
        })
        initRv()
        events()
    }

    override fun menuRes() = R.menu.menu_chat_search_more
    override fun statusColor() = R.color.color_main_orange

    private fun initRv() {
        mAdapter = ChannelAdapter(this)
        rv?.adapter = mAdapter
        rv?.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
    }

    override fun onResume() {
        super.onResume()
        shimmer?.startShimmerAnimation()
    }

    override fun onPause() {
        shimmer?.stopShimmerAnimation()
        super.onPause()
    }

    private fun events() {
        mAdapter?.setOnItemClickListener { _, _, position ->
            navigation?.addFragment(ChatFragment.newInstance(mAdapter?.data?.get(position)?.user?.mId?.toString()))
        }
        label?.setOnClickListener {
            navigation?.addFragment(InboxFragment.newInstance())
        }
        tvStartDate?.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val unwrappedContext = context ?: return@setOnClickListener
                DatePickerDialog(unwrappedContext).show()
            }
        }
        tvEndDate?.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val unwrappedContext = context ?: return@setOnClickListener
                DatePickerDialog(unwrappedContext).show()
            }
        }
    }
}