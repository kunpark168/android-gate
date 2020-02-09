package com.anhtam.gate9.v2.messenger.chat

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.chat.Message
import com.anhtam.gate9.adapter.chat.MessengerAdapter
import com.anhtam.gate9.v2.search.DataSource
import com.anhtam.gate9.v2.lib.loadImage
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.chat_fragment.*
import of.bum.network.helper.Resource

class ChatFragment : DaggerNavigationFragment(R.layout.chat_fragment) {

    private var mAdapter: MessengerAdapter? = null
    private val viewModel: ChatViewModel by viewModels { vmFactory }
    private var userId: String? = null

    companion object{
        fun newInstance(userId: String?) : ChatFragment {
            val fragment = ChatFragment()
            fragment.userId = userId
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        viewModel.userId.value = userId
        events()
    }

    override fun statusColor() = R.color.color_main_blue

    private fun events() {
        iconBack?.setOnClickListener {
            navigation?.back()
        }
        tvBack?.setOnClickListener {
            navigation?.back()
        }
    }

    private fun initRv(url: String?) {
        mAdapter = MessengerAdapter(this, url)
        rvChatBox?.adapter = mAdapter
        val chatSource = DataSource.getSampleChat()
        mAdapter?.setNewData(chatSource)
        edtMessage?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendMessage()
                true
            }
            else {
                false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        shimmer?.startShimmerAnimation()
    }

    override fun onPause() {
        shimmer?.stopShimmerAnimation()
        super.onPause()
    }

    private fun sendMessage() {
        val message = edtMessage?.text
        mAdapter?.setNewData(listOf(Message(0, message?.toString(), "19:00")))
    }

    private fun observer() {
//        viewModel.userInfo.observe(viewLifecycleOwner, Observer {
//            when(it) {
//                is Resource.Success -> {
//                    shimmer?.stopShimmerAnimation()
//                    shimmer?.visibility = View.GONE
//                    shimmerRoot?.visibility = View.VISIBLE
//                    val user = it.data?.user ?: return@Observer
//                    imgAvatar?.loadImage(this, user.avatar_path)
//                    tvName?.text = user.name
//                    tvId?.text = user.user_id
//                    initRv(user.avatar_path)
//                }
//            }
//        })
    }
}