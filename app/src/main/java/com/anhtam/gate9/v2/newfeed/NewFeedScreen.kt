package com.anhtam.gate9.v2.newfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.anhtam.domain.Banner
import com.anhtam.domain.Game
import com.anhtam.domain.v2.PostEntity
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.GroupBannerAdapter
import com.anhtam.gate9.adapter.v2.CommentAdapter
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.storage.StorageManager
import com.anhtam.gate9.v2.search.SearchScreen
import com.anhtam.gate9.utils.autoCleared
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.categories.CategoryTab
import com.anhtam.gate9.v2.categories.FeatureScreen
import com.anhtam.gate9.v2.main.ContainerFragment
import com.anhtam.gate9.v2.mxh_game.MXHGameScreen
import com.anhtam.gate9.v2.notification.NotificationFragment
import com.anhtam.gate9.v2.main.member.MemberHomeFragment
import com.anhtam.gate9.v2.messenger.ChannelFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.new_feed_screen.*
import kotlinx.android.synthetic.main.toolbar_new_feed.*
import of.bum.network.helper.Resource
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class NewFeedScreen : ContainerFragment() {

    fun update() {
        Timber.d("War")
        showProgress()
        loadData()
    }

    companion object{
        fun newInstance() = NewFeedScreen()
    }

    private var mGroup4Adapter by autoCleared<GroupBannerAdapter>()
    private var mUserId: Int = 0
    private val mViewModel: NewFeedViewModel by viewModels ({requireNotNull(activity)}, {vmFactory })
    private val mPostViewModel: com.anhtam.gate9.v2.discussion.common.newfeed.NewFeedViewModel by viewModels { vmFactory }
    private var mAdapter by autoCleared<SliderAdapter>()

    @Inject lateinit var mCommentAdapter : CommentAdapter
    @Inject @field:Named("avatar") lateinit var avatarOptions: RequestOptions
    @Inject @field:Named("banner") lateinit var bannerOptions: RequestOptions

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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

        mAdapter = SliderAdapter()
        slider.adapter = mAdapter

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
                        .load(avatar.toImage())
                        .apply {avatarOptions}
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
        mViewModel.data.observe(viewLifecycleOwner, Observer {
            Timber.d("Status $it")
            when(it) {
                is Resource.Success -> {
                    hideProgress()
                    bindingBanner(it.data?.mBanner)
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

    private fun bindingBanner(data: List<Banner>?) {
        mAdapter.setData(data?.map { it.url ?: "" } ?: mutableListOf())
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


    inner class SliderAdapter : PagerAdapter(){

        private var mImages = mutableListOf<String>()

        fun setData(data: List<String>){
            mImages.clear()
            mImages.addAll(data)
            notifyDataSetChanged()
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(context).inflate(R.layout.slider_item_layout, container, false)
            val imageView = view.findViewById<ImageView>(R.id.image)
            Glide.with(this@NewFeedScreen)
                    .apply { bannerOptions }
                    .load(mImages[position].toImage())
                    .fitCenter()
                    .into(imageView)
            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any) = view === `object`

        override fun getCount() = mImages.size

    }
}