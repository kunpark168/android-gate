package com.anhtam.gate9.v2.charts

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.restful.BackgroundTasks
import com.anhtam.gate9.v2.discussion.user.UserDiscussionScreen
import com.anhtam.gate9.vo.model.Category
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import javax.inject.Inject
import javax.inject.Named

class ChartsAdapter @Inject constructor(
        val navigation: Navigation,
        @Named("avatar") val avatarOptions: RequestOptions
) : BaseMultiItemQuickAdapter<RankingEntity, BaseViewHolder>(ArrayList()){

    init {
        addItemType(Ranking.CHAMPIONS.ranking, R.layout.item_top_1_ranking_layout)
        addItemType(Ranking.RUNNER_UP.ranking, R.layout.item_top_2_ranking_layout)
        addItemType(Ranking.SECOND_RUNNER_UP.ranking, R.layout.item_top_3_raking_layout)
        addItemType(Ranking.OTHERS.ranking, R.layout.item_charts_user)

        setOnItemChildClickListener { _, view, position ->
            val idUser = data[position].user.mId ?: return@setOnItemChildClickListener
            val role = when(data[position].user.mRoleId){
                "5" -> Category.Publisher
                else -> Category.Member
            }
            when(view.id){
                R.id.imgAvatar, R.id.tvName -> {
                    navigation.addFragment(UserDiscussionScreen.newInstance(idUser, role))
                }
                R.id.tvFollow -> {
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

    override fun convert(helper: BaseViewHolder?, item: RankingEntity?) {
        val holder = helper ?: return
        val user = item?.user ?: return
        holder.setText(R.id.tvId, user.mId?.toString())
        holder.setText(R.id.tvName, user.mName)
        val tvPoint = holder.getView<TextView>(R.id.tvFollow)
        tvPoint?.apply {
            text = user.mPoint?.toString()
        }
        val tvFollow = holder.getView<TextView>(R.id.tvFollow)
        when(user.mIsFollowing){
            true -> following(tvFollow)
            else -> unFollowing(tvFollow)
        }

        val imgAvatar = holder.getView<ImageView>(R.id.imgAvatar)
        imgAvatar?.run {
            Glide.with(mContext)
                    .load(user.mAvatar)
                    .apply(avatarOptions)
                    .into(this)
        }

        holder.addOnClickListener(R.id.imgAvatar)
                .addOnClickListener(R.id.tvName)
                .addOnClickListener(R.id.tvFollow)
    }

    private fun unFollowing(tv: TextView?){
        // un follow
        tv?.background = ContextCompat.getDrawable(mContext, R.drawable.bg_follow)
        tv?.text = mContext.resources.getString(R.string.follow_plus)
        tv?.setTextColor(ContextCompat.getColor(mContext, R.color.color_follow))
    }

    private fun following(tv: TextView?){
        tv?.background = ContextCompat.getDrawable(mContext, R.drawable.bg_following)
        tv?.text = mContext.resources.getString(R.string.following)
        tv?.setTextColor(ContextCompat.getColor(mContext, R.color.color_following))
    }

}

class RankingEntity(val user: User, val ranking: Int): MultiItemEntity{
    override fun getItemType() = ranking
}
enum class Ranking(val ranking: Int){
    CHAMPIONS(1), RUNNER_UP(2), SECOND_RUNNER_UP(3), OTHERS(0)
}