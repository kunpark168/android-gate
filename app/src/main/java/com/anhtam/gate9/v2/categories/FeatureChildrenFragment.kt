package com.anhtam.gate9.v2.categories


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.GroupBannerAdapter
import com.anhtam.gate9.adapter.PostQuickAdapter
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.utils.loadingToAdapter
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_category.*
import of.bum.network.helper.Resource
import javax.inject.Inject

class FeatureChildrenFragment : DaggerNavigationFragment() {

    private var categoryViewmodel: CategoryViewmodel?= null
    @Inject lateinit var mAdapter: GroupBannerAdapter
    private lateinit var mAdapterPostQuick: PostQuickAdapter
    private var tab = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    companion object {
        fun newInstance(tab: Int): FeatureChildrenFragment{
            val fragment = FeatureChildrenFragment()
            fragment.tab = tab
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        activity?.let {
            categoryViewmodel = ViewModelProviders.of(it, vmFactory).get(CategoryViewmodel::class.java)
        }

        initNewGame()
        initPostList()
        getData()
    }

    private fun initNewGame(){
        rvNewGame.adapter = mAdapter
        rvNewGame.layoutManager = GridLayoutManager(context, 2)
    }

    private fun initPostList(){
        mAdapterPostQuick = PostQuickAdapter(Glide.with(this))
        rvPostCategory.adapter = mAdapterPostQuick
        rvPostCategory.layoutManager = LinearLayoutManager(context)

        rvPostCategory.isNestedScrollingEnabled = false

    }

    private fun getData(){
        categoryViewmodel?.banner?.observe(this, Observer {
            notifyBannerImage(it)
        })

        categoryViewmodel?.newGame?.observe(this, Observer {
            notifyGroupBannerData(it)
        })


        categoryViewmodel?.posts?.observe(this, Observer {
            it?.loadingToAdapter(mAdapterPostQuick, 1)
        })
    }

    private fun notifyBannerImage(data: Resource<com.anhtam.domain.Banner>) {
        val banner = data.data ?: return
        Glide.with(this)
                .load(Config.IMG_URL + banner.url)
                .centerCrop()
                .into(imgBanner)
    }

    fun notifyGroupBannerData(data: Resource<List<com.anhtam.domain.Game>>) {
        when(data) {
            is Resource.Success -> {
                val games = data.data
                if (!games.isNullOrEmpty()) {
                    mAdapter.setNewData(games)
                    mAdapter.loadMoreEnd(true)
                }
            }
            else -> {

            }
        }
    }


}
