package com.anhtam.gate9.v2.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.anhtam.domain.Banner
import com.anhtam.domain.Game
import com.anhtam.gate9.repository.GameRepository
import com.anhtam.gate9.repository.PostRepository
import com.anhtam.gate9.utils.setValueDiff
import of.bum.network.helper.Resource
import javax.inject.Inject

class CategoryViewmodel @Inject constructor(private val gameRepository: GameRepository,
                                            private val postRepository: PostRepository) : ViewModel() {

    private val mBanner: MediatorLiveData<Resource<Banner>> = MediatorLiveData()
    val banner: LiveData<Resource<Banner>>
        get() = mBanner

    private val mNewGame = MediatorLiveData<Resource<List<Game>>>()
    val newGame: LiveData<Resource<List<Game>>>
        get() = mNewGame

    private val mPosts: MediatorLiveData<Resource<List<com.anhtam.domain.Post>>> = MediatorLiveData()
    val posts: LiveData<Resource<List<com.anhtam.domain.Post>>>
        get() = mPosts

    private var mUserAvatar: String?= null
    val userAvatar: String?
    get() = mUserAvatar

    private var mUserId: String?= null
    val userId: String?
        get() = mUserId

    fun requestData(){
//        mUserAvatar = mSessionManager.cachedUser.value?.data?.mAvatar
//        mUserId = mSessionManager.cachedUser.value?.data?.mUserId ?: 0

        val newDataBanner = gameRepository.getMainBanner()
        mBanner.addSource(newDataBanner) {
            it?.let { mBanner.setValueDiff(it) }
        }

        val newDataGane = gameRepository.getGroupBannerGames()
        mNewGame.addSource(newDataGane) {
            it?.let { mNewGame.setValueDiff(it) }
        }

        val newData = postRepository.getAllPosts(1)
        mPosts.addSource(newData) {
            it?.let { mPosts.setValueDiff(it) }
        }
    }

}