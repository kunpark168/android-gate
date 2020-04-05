package com.anhtam.gate9.v2.discussion.common.newfeed

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anhtam.domain.v2.Post
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.v2.PostAdapter
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.restful.BackgroundTasks
import com.anhtam.gate9.share.view.MoreDialog
import com.anhtam.gate9.storage.StorageManager
import com.anhtam.gate9.utils.convertInt
import com.anhtam.gate9.v2.BackgroundViewModel
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.createpost.CreatePostScreen
import com.anhtam.gate9.v2.discussion.common.CommonDiscussionFragment
import com.anhtam.gate9.v2.game_detail.DetailGameFragment
import com.anhtam.gate9.v2.nph_detail.DetailNPHFragment
import com.anhtam.gate9.v2.post_detail.DetailPostScreen
import com.anhtam.gate9.v2.report.post.ReportPostActivity
import com.anhtam.gate9.v2.user_detail.DetailUserFragment
import com.anhtam.gate9.vo.Reaction
import of.bum.network.helper.Resource

class NewFeedFragment : CommonDiscussionFragment<Post, PostAdapter, NewFeedViewModel>(), MoreDialog.IMore {

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

    override val mViewModel: NewFeedViewModel by viewModels { vmFactory }

    override val colorTextTab = R.color.colorTabDiscussion
    override var mLazyLoad = false
    override val tabTitle: List<Int>
        get() = arrayListOf(R.string.number_all, R.string.number_post, R.string.number_comment)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter.setOnItemChildClickListener { _, childView, position ->
            val post = mAdapter.data[position]
            when(childView.id){
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
