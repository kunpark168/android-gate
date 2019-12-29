package com.anhtam.gate9.v2.newfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhtam.domain.Banner
import com.anhtam.domain.Game
import com.anhtam.domain.v2.PostEntity
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.GroupBannerAdapter
import com.anhtam.gate9.adapter.v2.CommentAdapter
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.storage.StorageManager
import com.anhtam.gate9.v2.search.SearchScreen
import com.anhtam.gate9.utils.autoCleared
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.InfoService
import com.anhtam.gate9.v2.categories.CategoryTab
import com.anhtam.gate9.v2.categories.FeatureScreen
import com.anhtam.gate9.v2.mxh_game.MXHGameScreen
import com.anhtam.gate9.v2.notification.NotificationFragment
import com.anhtam.gate9.v2.main.member.MemberHomeFragment
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.messenger.ChannelFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.new_feed_screen.*
import kotlinx.android.synthetic.main.new_feed_screen.imgBanner
import kotlinx.android.synthetic.main.new_feed_screen.tvEvent
import kotlinx.android.synthetic.main.new_feed_screen.tvGame
import kotlinx.android.synthetic.main.new_feed_screen.tvSpecial
import kotlinx.android.synthetic.main.new_feed_screen.tvTheme
import kotlinx.android.synthetic.main.new_feed_screen.tvVideo
import kotlinx.android.synthetic.main.toolbar_new_feed.*
import of.bum.network.helper.Resource
import timber.log.Timber
import javax.inject.Inject

class NewFeedScreen : DaggerNavigationFragment() {

    fun update() {
        Timber.d("War")
        showProgress()
        loadData()
    }

    companion object{
        fun newInstance() = NewFeedScreen()
    }

    private var mGroup4Adapter by autoCleared<GroupBannerAdapter>()
    private var mCommentAdapter by autoCleared<CommentAdapter>()
    private var mUserId: Int = 0
    private val mViewModel: NewFeedViewModel by viewModels { vmFactory }
    private val mPostViewModel: com.anhtam.gate9.v2.discussion.common.newfeed.NewFeedViewModel by viewModels { vmFactory }
    @Inject lateinit var mInfoService: InfoService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.window?.statusBarColor = ContextCompat.getColor(context!!, R.color.color_main_blue)
        return inflater.inflate(R.layout.new_feed_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        loadData()
        initCommentRecyclerView()
        initGamesRecyclerView()
        initEventListener()
        observer()
    }

    private fun observer(){
        mPostViewModel._post.observe(viewLifecycleOwner, Observer {resource ->
            when(resource) {
                is Resource.Success -> {
                    val data = resource.data?.wrap
                    if (data.isNullOrEmpty()) {
                        mCommentAdapter.loadMoreEnd()
                    } else {
                        if (mPostViewModel.page == 0) {
                            mCommentAdapter.setNewData(data)
                        } else {
                            mCommentAdapter.addData(data)
                        }
                        mCommentAdapter.loadMoreComplete()
                    }
                }
                is Resource.Loading -> {

                }
                else -> {
                    mCommentAdapter.loadMoreFail()
                }
            }
        })

        mMainViewModel._user.observe(viewLifecycleOwner, Observer {
            val avatar = when(it) {
                is Resource.Loading -> ""
                is Resource.Error -> ""
                is Resource.Success -> it.data?.mAvatarPath
            }
            avatar?.run {
                Glide.with(this@NewFeedScreen)
                        .applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.img_avatar_holder)
                                .error(R.drawable.img_avatar_holder))
                        .load(avatar.toImage())
                        .apply {RequestOptions.circleCropTransform()
                                .placeholder(R.drawable.img_avatar_holder)
                                .error(R.drawable.img_avatar_holder) }
                        .into(imgAvatar)
            }
            mUserId = it.data?.mUserId ?: return@Observer
        })
    }

    private fun loadMore(){
        mPostViewModel.requestMore(mUserId)
    }

    private fun loadData() {
        Timber.d(StorageManager.getAccessToken())
        mViewModel.getListingPost().observe(viewLifecycleOwner, Observer {
            Timber.d("Status $it")
            when(it) {
                is Resource.Success -> {
                    hideProgress()
                    bindingBanner(it.data?.mBanner?.firstOrNull())
                    bindingGroupGames(it.data?.mGames)
                    bindingComment(it.data?.mListing)
                }
                is Resource.Error ->{
                    hideProgress()
                }
                else -> {

                }
            }
        })
    }

    private fun initCommentRecyclerView() {
        mCommentAdapter = CommentAdapter(navigation)
        mCommentAdapter.setLoadMoreView(CustomLoadMoreView())
        mCommentAdapter.setOnLoadMoreListener({
            loadMore()
        }, rvComment)
        rvComment?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvComment?.adapter = mCommentAdapter
        val unwrappedContext = context ?: return
        val dividerItemDecoration = DividerItemDecoration(unwrappedContext, LinearLayout.VERTICAL)
        val drawableDivider = ContextCompat.getDrawable(unwrappedContext, R.drawable.divider_item_decorator) ?: return
        dividerItemDecoration.setDrawable(drawableDivider)
        rvComment?.addItemDecoration(dividerItemDecoration)
    }

    private fun initGamesRecyclerView() {
        mGroup4Adapter = GroupBannerAdapter(navigation, Glide.with(this))
        rv4Banners?.layoutManager = GridLayoutManager(context, 2)
        rv4Banners?.adapter= mGroup4Adapter
    }

    private fun bindingBanner(data: Banner?) {
        if (data == null) return
        Glide.with(this)
                .applyDefaultRequestOptions(
                        RequestOptions()
                                .centerCrop()
                                .placeholder(R.drawable.img_holder_banner)
                                .error(R.drawable.img_holder_banner)
                ).load(Config.IMG_URL + data.url)
                .into(imgBanner)
    }

    private fun bindingComment(data: List<PostEntity>?) {
        if (data == null) return
        mCommentAdapter.setNewData(data)
    }

    private fun bindingGroupGames(data: List<Game>?) {
        if (data == null) return
        val games: List<Game> = if (data.size < 4) {
            data
        } else {
            data.subList(0, 4)
        }
        rv4Banners?.visibility = View.VISIBLE
        mGroup4Adapter.setNewData(games)
    }

    private fun initEventListener() {
        swipeRefreshLayout?.setOnRefreshListener {
            swipeRefreshLayout?.isRefreshing = false
//            showProgress()
//            loadData()
        }

        icNotification?.setOnClickListener {
            navigation?.addFragment(NotificationFragment.newInstance())
        }
        icChat?.setOnClickListener {
            navigation?.addFragment(ChannelFragment.newInstance())
        }
        icGroup?.setOnClickListener { navigation?.addFragment(MemberHomeFragment.newInstance()) }
        imgSearch?.setOnClickListener{
            navigation?.addFragment(SearchScreen.newInstance())
        }
        tvSearch?.setOnClickListener {
            navigation?.addFragment(SearchScreen.newInstance())
        }
        tvVideo?.setOnClickListener {
            navigation?.addFragment(FeatureScreen.newInstance(CategoryTab.VIDEO.tab))
        }
        tvTheme?.setOnClickListener {
            navigation?.addFragment(FeatureScreen.newInstance(CategoryTab.THEME.tab))
        }
        tvSpecial?.setOnClickListener {
            navigation?.addFragment(FeatureScreen.newInstance(CategoryTab.SPECIAL.tab))
        }
        tvEvent?.setOnClickListener {
            navigation?.addFragment(FeatureScreen.newInstance(CategoryTab.EVENT.tab))
        }
        tvGame?.setOnClickListener {
            navigation?.addFragment(MXHGameScreen.newInstance())
        }
        imgAvatar?.setOnClickListener {

        }

    }
}