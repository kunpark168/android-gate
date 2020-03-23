package com.anhtam.gate9.v2.bxh

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhtam.gate9.R
import com.anhtam.gate9.share.view.CustomLoadMoreView
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.vo.model.Category
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.bxh_screen.*
import of.bum.network.helper.Resource
import javax.inject.Inject
import javax.inject.Named

class BXHScreen(
        private val mRoleId: Int
) : DaggerNavigationFragment(R.layout.bxh_screen) {

    private val viewModel: BXHViewModel by viewModels { vmFactory }
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
        viewModel.setId(mRoleId)
        viewModel.loadData(refresh = true)
        val user = mSessionManager.cachedUser.value?.data ?: return
        tvName?.text = user.mName
        tvId?.text = user.mId?.toString()
        Glide.with(this)
                .load(user.mAvatar?.toImage())
                .apply(avatarOptions)
                .into(imgAvatar)
        // follow
        tvPoint?.text = user.mPoint?.toString() ?: "0"
        tvRanking?.text = user.mRanking
    }


    override fun menuRes() = R.menu.menu_search_avatar_more

    private fun observerRanking(){
        viewModel.data.observe(viewLifecycleOwner, Observer {
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
                                    source.addAll(data.subList(3, data.size).map { user -> RankingEntity(user, Ranking.OTHERS.ranking) })
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
        mAdapter.setLoadMoreView(CustomLoadMoreView())
        mAdapter.mRoleId = if (mRoleId == 5) Category.Publisher else Category.Member
        mAdapter.setOnLoadMoreListener({
            viewModel.loadData()
        }, rvCharts)
        rvCharts.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvCharts.isNestedScrollingEnabled = false
        rvCharts.adapter = mAdapter
    }

    companion object{
        fun newInstance(roleId: Int) = BXHScreen(roleId)
    }
}
