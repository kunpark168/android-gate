package com.anhtam.gate9.v2.game_detail.thao_luan


import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.anhtam.domain.v2.Post
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.PostAdapter
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.restful.BackgroundTasks
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.share.view.MoreDialog
import com.anhtam.gate9.storage.StorageManager
import com.anhtam.gate9.utils.convertInt
import com.anhtam.gate9.v2.BackgroundViewModel
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.createpost.CreatePostScreen
import com.anhtam.gate9.v2.game_detail.DetailGameFragment
import com.anhtam.gate9.v2.game_detail.DetailGameViewModel
import com.anhtam.gate9.v2.nph_detail.DetailNPHFragment
import com.anhtam.gate9.v2.post_detail.DetailPostScreen
import com.anhtam.gate9.v2.report.post.ReportPostActivity
import com.anhtam.gate9.v2.shared.views.AbstractVisibleFragment
import com.anhtam.gate9.v2.user_detail.DetailUserFragment
import com.anhtam.gate9.vo.Reaction
import kotlinx.android.synthetic.main.shared_only_recycler_view_layout.*
import of.bum.network.helper.Resource
import javax.inject.Inject

class ThaoLuanFragment : AbstractVisibleFragment(R.layout.shared_only_recycler_view_layout), MoreDialog.IMore{
    private val mTopViewModel: BackgroundViewModel by viewModels({requireActivity()},{vmFactory})
    private var mCurrentPost = -1

    override fun delete() {
        if (mCurrentPost == -1) return
        val id = mAdapter.data[mCurrentPost]?.commentId ?: return
        showProgress()
        mTopViewModel.delete(id).observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    mAdapter.remove(mCurrentPost)
                    mCurrentPost = -1
                    hideProgress()
                }
                is Resource.Error -> {
                    hideProgress()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    mCurrentPost = -1
                }
            }
        })
    }

    override fun edit() {
        if (mCurrentPost == -1) return
        navigation?.addFragment(CreatePostScreen.newInstance(post = mAdapter.data[mCurrentPost]))
    }

    override fun report() {
        if (mCurrentPost == -1) return
        navigation?.addFragment(ReportPostActivity.newInstance(mAdapter.data[mCurrentPost]?.commentId ?: return))
    }

    private val mDetailViewModel: DetailGameViewModel by viewModels({requireParentFragment()},{vmFactory})
    private val mViewModel: ThaoLuanViewModel by viewModels { vmFactory }
    @Inject lateinit var mAdapter: PostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
        observer()
    }

    private fun initView(){
        initRecyclerView()
    }

    private fun initRecyclerView(){
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        mAdapter.setOnLoadMoreListener({
            mViewModel.loadData(false)
        }, recyclerView)

        mAdapter.setOnItemChildClickListener { _, view, position ->
            val post = mAdapter.data[position]
            when(view.id){
                R.id.readMoreTextView, R.id.contentTextView, R.id.commentImageView -> {
                    navigateToPostDetail(post){
                        changeReaction(it, position)
                    }
                }
                R.id.userNameTextView, R.id.avatarImageView -> {
                    val user = post.user ?: post.createdUser ?: return@setOnItemChildClickListener
                    navigateToMemberDiscussion(user)
                }
                R.id.gameImageView, R.id.titleGameTextView -> {
                    val game = post.game ?: return@setOnItemChildClickListener
                    navigateToGameDiscussion(game)
                }
                R.id.moreImageView -> {
                    mCurrentPost = position
                    val unwrapContext = context ?: return@setOnItemChildClickListener
                    val user = post.user ?: post.createdUser ?: return@setOnItemChildClickListener
                    val isOwner = user.mId == mSessionManager.cachedUser.value?.data?.mId
                    val mMoreDialog = MoreDialog.newInstance(unwrapContext, isOwner)
                    mMoreDialog.setMoreListener(this)
                    mMoreDialog.show()
                }
                R.id.followGameTextView -> {
                    val followView = view as? TextView
                    if (checkLogin()) {
                        BackgroundTasks.postFollowGame(post.game?.gameId ?: return@setOnItemChildClickListener)
                        if(followView?.text == context?.getString(R.string.follow)) {
                            setFollowing(followView)
                            //sending request
                        } else {
                            setFollow(followView)
                            //sending request
                        }
                    } else {
                        navigation?.addFragment(LoginScreen.newInstance(false))
                    }
                }
            }
        }
        context?.let {
            val dividerItemDecoration = DividerItemDecoration(it, LinearLayout.VERTICAL)
            val drawableDivider = ContextCompat.getDrawable(it, R.drawable.divider_item_decorator) ?: return
            dividerItemDecoration.setDrawable(drawableDivider)
            recyclerView?.addItemDecoration(dividerItemDecoration)

        }
        recyclerView?.adapter = mAdapter
        recyclerView?.layoutManager = LinearLayoutManager(context)
    }

    private fun initEvent(){
        swipeRefreshLayout?.setOnRefreshListener {
            swipeRefreshLayout?.isRefreshing = false
            showProgress()
            mViewModel.loadData(refresh = true)
        }
    }

    private fun observer(){
        observerGame()
        observerData()
    }

    private fun observerGame(){
        mDetailViewModel.mGame.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    val mId = it.data?.gameId ?: return@Observer
                    mViewModel.initialize(mId)
                    showProgress()
                    mViewModel.loadData(refresh = true)
                }
                else -> {

                }
            }
        })
    }

    private fun observerData(){
        mViewModel.data.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success ->{
                    hideProgress()
                    val data = it.data?.mList
                    if (data.isNullOrEmpty()) {
                        mAdapter.loadMoreEnd()
                    } else {
                        if (mViewModel.mPage == 0) {
                            mAdapter.setNewData(data)
                        } else {
                            mAdapter.addData(data)
                        }
                        mAdapter.loadMoreComplete()
                    }
                }
                is Resource.Error -> {
                    hideProgress()
                }
                else ->{}
            }
        })
    }

    companion object{
        fun newInstance() = ThaoLuanFragment()
    }





    private fun checkLogin() : Boolean {
        val accessToken = StorageManager.getAccessToken()
        return accessToken.isNotEmpty()
    }

    private fun navigateToMemberDiscussion(user: User) {
        val roleId = user.mRoleId ?: return
        val id = user.mId ?: return
        if (roleId != 5){
            navigation?.addFragment(DetailUserFragment.newInstance(id))
        } else {
            navigation?.addFragment(DetailNPHFragment.newInstance(id))
        }
    }

    private fun navigateToPostDetail(post: Post, listener: (Reaction)->Unit) {
        val id = post.commentId ?: return
        navigation?.addFragment(DetailPostScreen.newInstance(id, DetailPostScreen.Detail.POST, listener), tag = Config.DETAIL_POST_FRAGMENT_TAG)
    }

    private fun navigateToGameDiscussion(game: Game){
        val id = game.gameId ?: return
        navigation?.addFragment(DetailGameFragment.newInstance(id))
    }



    private fun changeReaction(react: Reaction, position: Int){
        val data = mAdapter.data
        val post = data[position]
        val preReact = Reaction.react(data[position].like?.convertInt() ?: 0)
        post.like = Reaction.value(react).toString()
        // count
        when(preReact){
            Reaction.Love -> {
                post.totalLove = (post.totalLove ?: 0) - 1
            }
            Reaction.Like -> {
                post.totalLike = (post.totalLike ?: 0) - 1
            }
            Reaction.Dislike -> {
                post.totalDislike = (post.totalDislike ?: 0) - 1
            }
        }
        when(react){
            Reaction.Love -> {
                post.totalLove = (post.totalLove ?: 0) + 1
            }
            Reaction.Like -> {
                post.totalLike = (post.totalLike ?: 0) + 1
            }
            Reaction.Dislike -> {
                post.totalDislike = (post.totalDislike ?: 0) + 1
            }
        }
        mAdapter.notifyDataSetChanged()
    }



    private fun setFollow(tvFollowGame: TextView?) {
        val unwrapContext = context ?: return
        tvFollowGame?.text = getString(R.string.follow)
        tvFollowGame?.setBackgroundResource(R.drawable.bg_follow)
        tvFollowGame?.setTextColor(ContextCompat.getColor(unwrapContext, R.color.text_color_red_light))
    }

    private fun setFollowing(tvFollowGame: TextView?) {
        val unwrapContext = context ?: return
        tvFollowGame?.text = getString(R.string.following)
        tvFollowGame?.setBackgroundResource(R.drawable.bg_following)
        tvFollowGame?.setTextColor(ContextCompat.getColor(unwrapContext, R.color.text_color_blue))
    }
}