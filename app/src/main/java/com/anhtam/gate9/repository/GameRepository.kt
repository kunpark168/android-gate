package com.anhtam.gate9.repository

import androidx.lifecycle.LiveData
import com.anhtam.domain.Banner
import com.anhtam.domain.Game
import com.anhtam.domain.v2.Gamev1
import of.bum.network.FetchBoundResource
import of.bum.network.helper.ApiResponse
import of.bum.network.helper.Resource
import of.bum.network.helper.RestResponse
import of.bum.network.service.BannerService
import of.bum.network.service.GameService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor(
        private val gameService: GameService,
        private val bannerService: BannerService
) {

    fun getGameDetail(gameId: String): LiveData<Resource<Game>> {
        return object: FetchBoundResource<Game>() {
            override fun createCall() = gameService.getDetailGame(gameId)

        }.asLiveData()
    }

    fun getAllGame(page: Int): LiveData<Resource<List<Game>>> {
        return object: FetchBoundResource<List<Game>>() {
            override fun createCall() = gameService.getAllGame(page)

        }.asLiveData()
    }

    fun getMainBanner(): LiveData<Resource<Banner>> {
        return object: FetchBoundResource<Banner>() {
            override fun createCall(): LiveData<ApiResponse<RestResponse<Banner>>> {
                return bannerService.getBanner()
            }
        }.asLiveData()
    }

    fun getGroupBannerGames(): LiveData<Resource<List<Gamev1>>> {
        return object: FetchBoundResource<List<Gamev1>>() {
            override fun createCall(): LiveData<ApiResponse<RestResponse<List<Gamev1>>>> {
                return gameService.getBannerGames()
            }
        }.asLiveData()
    }
}