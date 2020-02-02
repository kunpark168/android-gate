package com.anhtam.gate9.v2.charts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.chart_screen.*
import of.bum.network.helper.Resource
import javax.inject.Inject
import javax.inject.Named

class ChartScreen(
        private val mUser: User
) : DaggerNavigationFragment(R.layout.chart_screen) {

    private val viewModel: ChartViewModel by viewModels { vmFactory }
    @Inject lateinit var mAdapter: ChartsAdapter
    @field:Named("avatar") @Inject lateinit var avatarOptions: RequestOptions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        initUserView()
        initRecyclerView()
        observerRanking()
    }

    private fun initUserView(){
        when (mUser.mRoleId){
            Config.NPH_ROLEID.toString() -> viewModel.getNPHRanking(true)
            else -> viewModel.getUserRanking(true)
        }
        val user = mSessionManager.cachedUser.value?.data ?: return
        tvName?.text = user.mName
        tvId?.text = user.mId?.toString()
        Glide.with(this)
                .load(user.mAvatar)
                .apply(avatarOptions)
                .into(imgAvatar)
        // follow
        tvPoint?.text = user.mPoint?.toString()
        tvRanking?.text = user.mRanking
    }


    override fun menuRes() = R.menu.menu_chat_search_more

    private fun observerRanking(){
        viewModel.users.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    val data = it.data

                    if (data.isNullOrEmpty()) {
                        mAdapter.loadMoreEnd()
                    } else {
                        if (viewModel.mPage == 0) {
                            //
                            val source = mutableListOf<RankingEntity>()
                            when(data.size){
                                1 -> {
                                    source.add(RankingEntity(data.first(), Ranking.CHAMPIONS.ranking))
                                }
                                2 -> {
                                    source.add(RankingEntity(data.first(), Ranking.CHAMPIONS.ranking))
                                    source.add(RankingEntity(data[1], Ranking.RUNNER_UP.ranking))
                                }
                                3 -> {
                                    source.add(RankingEntity(data.first(), Ranking.CHAMPIONS.ranking))
                                    source.add(RankingEntity(data[1], Ranking.RUNNER_UP.ranking))
                                    source.add(RankingEntity(data[2], Ranking.SECOND_RUNNER_UP.ranking))
                                }
                                else -> {
                                    source.add(RankingEntity(data.first(), Ranking.CHAMPIONS.ranking))
                                    source.add(RankingEntity(data[1], Ranking.RUNNER_UP.ranking))
                                    source.add(RankingEntity(data[2], Ranking.SECOND_RUNNER_UP.ranking))
                                    source.addAll(data.subList(2, data.size - 1).map { user -> RankingEntity(user, Ranking.OTHERS.ranking) })
                                }
                            }
                            mAdapter.setNewData(source)
                        } else {
                            mAdapter.addData(data.map { user -> RankingEntity(user, Ranking.OTHERS.ranking) })
                        }
                        mAdapter.loadMoreComplete()
                    }
                }
                is Resource.Loading -> {

                }
                else -> {
                    mAdapter.loadMoreFail()
                }
            }
        })
    }

    private fun initRecyclerView(){
        mAdapter.setOnLoadMoreListener({
            viewModel.getUserRanking()
        }, rvCharts)
        rvCharts.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvCharts.isNestedScrollingEnabled = false
        rvCharts.adapter = mAdapter
    }

    companion object{
        fun newInstance(user: User) = ChartScreen(user)
    }
}
