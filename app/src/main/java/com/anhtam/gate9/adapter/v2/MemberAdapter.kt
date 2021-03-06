package com.anhtam.gate9.adapter.v2

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.restful.BackgroundTasks
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.nph_detail.DetailNPHFragment
import com.anhtam.gate9.v2.user_detail.DetailUserFragment
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
            val idUser = data[position].mId ?: return@setOnItemChildClickListener
            val role = when(data[position].mRoleId){
                5 -> Category.Publisher
                else -> Category.Member
            }
            when(view.id){
                R.id.nameTextView, R.id.avatarImageView -> {
                    val user = data[position] ?: return@setOnItemChildClickListener
                    val roleId = user.mRoleId ?: return@setOnItemChildClickListener
                    val id = user.mId ?: return@setOnItemChildClickListener
                    if (roleId != 5){
                        navigation.addFragment(DetailUserFragment.newInstance(id))
                    } else {
                        navigation.addFragment(DetailNPHFragment.newInstance(id))
                    }
                }
                R.id.followButton -> {
                    BackgroundTasks.postUserFollow(idUser, role.id)
                    val tvFollow = view as? TextView
                    if (tvFollow?.text == mContext.getString(R.string.following)){
                        // un follow
                        unFollowing(tvFollow)
                    } else {
                        // follow
                        following(tvFollow)
                    }
                }
            }
        }
    }

    private fun unFollowing(tv: TextView){
        // un follow
        tv.background = ContextCompat.getDrawable(mContext, R.drawable.bg_follow)
        tv.text = mContext.resources.getString(R.string.follow_plus)
        tv.setTextColor(ContextCompat.getColor(mContext, R.color.color_follow))
    }

    private fun following(tv: TextView?){
        tv?.background = ContextCompat.getDrawable(mContext, R.drawable.bg_following)
        tv?.text = mContext.resources.getString(R.string.following)
        tv?.setTextColor(ContextCompat.getColor(mContext, R.color.color_following))
    }

    override fun convert(helper: BaseViewHolder?, item: User?) {
        val user = item ?: return
        val view = helper?.itemView ?: return
        view.nameTextView?.text = user.mName ?: ""
        view.userIdTextView?.text = user.mId?.toString() ?: ""
        val avatar = if (user.mAvatarPath.isNullOrEmpty()) user.mAvatar else user.mAvatarPath
        Glide.with(mContext)
                .load(avatar?.toImage())
                .apply(avatarRequestOptions)
                .into(view.avatarImageView)
        helper.addOnClickListener(R.id.nameTextView)
                .addOnClickListener(R.id.avatarImageView)
                .addOnClickListener(R.id.followButton)
        when(user.mIsFollowing){
            true -> following(view.followButton)
            else -> unFollowing(view.followButton)
        }
    }
}