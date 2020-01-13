package com.anhtam.gate9.adapter.v2

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.discussion.user.UserDiscussionScreen
import com.anhtam.gate9.vo.model.Category
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.member_item_layout.view.*
import javax.inject.Inject
import javax.inject.Named

class MemberAdapter @Inject constructor(
        val navigation: Navigation,
        @Named("avatar")val  avatarRequestOptions: RequestOptions
)
    : BaseQuickAdapter<User, BaseViewHolder>(R.layout.member_item_layout, emptyList()){

    init {
        setOnItemChildClickListener { _, view, position ->
            when(view.id){
                R.id.nameTextView, R.id.avatarImageView -> {
                    val idUser = data[position].mId ?: return@setOnItemChildClickListener
                    navigation.addFragment(UserDiscussionScreen.newInstance(idUser, Category.Member))
                }
                R.id.followButton -> {
                    val tvFollow = view as? TextView
                    if (tvFollow?.text == mContext.getString(R.string.following)){
                        // un follow
                        tvFollow.background = ContextCompat.getDrawable(mContext, R.drawable.bg_follow)
                        tvFollow.text = mContext.resources.getString(R.string.follow_plus)
                        tvFollow.setTextColor(ContextCompat.getColor(mContext, R.color.color_follow))
                    } else {
                        // follow
                        tvFollow?.background = ContextCompat.getDrawable(mContext, R.drawable.bg_following)
                        tvFollow?.text = mContext.resources.getString(R.string.following)
                        tvFollow?.setTextColor(ContextCompat.getColor(mContext, R.color.color_following))
                    }
                }
            }
        }
    }

    override fun convert(helper: BaseViewHolder?, item: User?) {
        val user = item ?: return
        val view = helper?.itemView ?: return
        view.nameTextView?.text = user.mName ?: ""
        view.userIdTextView?.text = user.mId?.toString() ?: ""
        Glide.with(mContext)
                .load(user.mAvatar?.toImage())
                .apply(avatarRequestOptions)
                .into(view.avatarImageView)
        helper.addOnClickListener(R.id.nameTextView)
                .addOnClickListener(R.id.avatarImageView)
                .addOnClickListener(R.id.followButton)
    }
}