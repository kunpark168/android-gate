package com.anhtam.gate9.v2.mxh_game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.gate9.R
import com.anhtam.gate9.restful.BackgroundTasks
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.utils.autoCleared
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.discussion.game.GameDiscussionScreen
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.squareup.phrase.Phrase
import kotlinx.android.synthetic.main.mxh_game_item_layout.view.*
import kotlinx.android.synthetic.main.mxh_game_tab_fragment.*
import kotlinx.android.synthetic.main.shared_play_banner_game_layout.view.*
import of.bum.network.helper.Resource
import javax.inject.Inject
import javax.inject.Named

class MXHGameTabFragment : DaggerNavigationFragment(R.layout.mxh_game_tab_fragment) {
    companion object{
        fun newInstance(type: MXHGameScreen.MXHGameTab): MXHGameTabFragment{
            val fragment = MXHGameTabFragment()
            fragment.mType = type
            return fragment
        }
    }
    private val mViewModel: MXHGameViewModel by viewModels { vmFactory }
    private var mType : MXHGameScreen.MXHGameTab = MXHGameScreen.MXHGameTab.ALL
    private var mAdapter by autoCleared<Adapter>()
    private var mLoading = false

    @field:Named("banner") @Inject lateinit var bannerOptions: RequestOptions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser && !mLoading) {
            mLoading = false
        }
    }

    private fun init() {
        showProgress()
        mViewModel.type = mType
        mViewModel.request(mType)
        initRecyclerView()
        initEvents()
        observer()
    }

    private fun initEvents(){
        swipeRefreshLayout?.setOnRefreshListener {
            swipeRefreshLayout?.isRefreshing = false
            mViewModel.request(mType)
        }
    }

    private fun initRecyclerView() {
        mAdapter = Adapter()
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        mAdapter.setOnLoadMoreListener({
            mViewModel.requestMore()
        }, rvGame)
        rvGame?.layoutManager = LinearLayoutManager(context)
        rvGame?.adapter = mAdapter
        val unwrappedContext = context ?: return
        val dividerItemDecoration = DividerItemDecoration(unwrappedContext, LinearLayout.VERTICAL)
        val drawableDivider = ContextCompat.getDrawable(unwrappedContext, R.drawable.divider_item_decorator) ?: return
        dividerItemDecoration.setDrawable(drawableDivider)
        rvGame?.addItemDecoration(dividerItemDecoration)
    }

    private fun observer(){
        mViewModel._game.observe(viewLifecycleOwner, Observer {resource ->
            when(resource) {
                is Resource.Success -> {
                    hideProgress()
                    val data = resource.data
                    if (data.isNullOrEmpty()) {
                        mAdapter.loadMoreEnd()
                    } else {
                        if (mViewModel.page == 0) {
                            mAdapter.setNewData(data)
                        } else {
                            mAdapter.addData(data)
                        }
                        mAdapter.loadMoreComplete()
                    }
                }
                is Resource.Loading -> {
                    hideProgress()
                }
                else -> {
                    mAdapter.loadMoreFail()
                }
            }
        })
    }

    inner class Adapter : BaseQuickAdapter<Game, BaseViewHolder>(R.layout.mxh_game_item_layout, arrayListOf()){

        init {
            setOnItemClickListener { _, _, position ->
                val id = data[position].gameId ?: return@setOnItemClickListener
                navigation?.addFragment(GameDiscussionScreen.newInstance(id))
            }
        }


        override fun convert(helper: BaseViewHolder?, item: Game?) {
            val view = helper?.itemView ?: return
            val data = item ?: return
            view.tvTitle.text = item.name
            Glide.with(mContext)
                    .load(data.imgCover?.toImage())
                    .apply(bannerOptions)
                    .into(view.imgBannerGame)
            Glide.with(mContext)
                    .load(data.avatar?.toImage())
                    .apply(bannerOptions)
                    .into(view.imgAvatarGame)
            val followDescription = mContext.getString(R.string.follower_amount_and_post_amount)
            val followGame = Phrase.from(followDescription)
                    .put("follower", data.follower?.toString() ?: "0")
                    .put("post", data.post?.toString() ?: "0")
                    .format()
            view.tvFollowAmount.text = followGame
            if (data.follow == true) {
                setFollowing(view.tvFollow)
            } else {
                // check follow here
                setFollow(view.tvFollow)
            }

            view.tvFollow.setOnClickListener {
                BackgroundTasks.postFollowGame(data.gameId ?: return@setOnClickListener)
                if(view.tvFollow?.text == mContext.getString(R.string.follow)) {
                    setFollowing(view.tvFollow)
                } else {
                    setFollow(view.tvFollow)
                }
            }

        }


        private fun setFollow(tvFollowGame: TextView) {
            tvFollowGame.text = mContext.getString(R.string.follow)
            tvFollowGame.setBackgroundResource(R.drawable.bg_follow)
            tvFollowGame.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_red_light))
        }

        private fun setFollowing(tvFollowGame: TextView) {
            tvFollowGame.text = mContext.getString(R.string.following)
            tvFollowGame.setBackgroundResource(R.drawable.bg_following)
            tvFollowGame.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_blue))
        }
    }

}