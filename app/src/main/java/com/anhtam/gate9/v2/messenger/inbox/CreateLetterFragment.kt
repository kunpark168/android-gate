package com.anhtam.gate9.v2.messenger.inbox

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.repository.SocialRepository
import com.anhtam.gate9.v2.shared.AbstractGalleryFragment
import kotlinx.android.synthetic.main.inbox_fragment.*
import of.bum.network.helper.Resource
import javax.inject.Inject

class CreateLetterFragment(
        private val mUser: User
) : AbstractGalleryFragment(R.layout.inbox_fragment) {

    companion object{
        fun newInstance(user: User) = CreateLetterFragment(user)
    }

    @Inject lateinit var mRepo: SocialRepository

    override fun onSelectedImage(urls: List<String>) {
        super.onSelectedImage(urls)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        tvCancel?.setOnClickListener {
            navigation?.back()
        }
        tvPost?.setOnClickListener {
            hideKeyboard()
            createMessage()
        }
        selectImageTab?.setOnClickListener {
            chooseImage()
        }
    }

    private fun initView() {
        nameTextView?.text = "${mUser.mId}/ ${mUser.mName}"

    }

    private fun createMessage() {
        val title = titleEditText?.text?.toString()
        val content = contentEditText?.text?.toString()
        if (title.isNullOrEmpty() || content.isNullOrEmpty()) return
        showProgress()
        mRepo.createLetter(mUser.mId ?: return, title, content).observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    hideProgress()
                    val refreshBundle = Bundle()
                    navigation?.returnResult(refreshBundle)
                }
                is Resource.Error -> {
                    hideProgress()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {

                }
            }
        })
    }
}