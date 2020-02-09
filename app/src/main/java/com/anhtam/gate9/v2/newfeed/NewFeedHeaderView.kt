package com.anhtam.gate9.v2.newfeed

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.anhtam.domain.v2.Banner
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.GroupBannerAdapter
import com.anhtam.gate9.navigation.Navigation
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.categories.CategoryTab
import com.anhtam.gate9.v2.categories.FeatureScreen
import com.anhtam.gate9.v2.mxh_game.MXHGameScreen
import com.anhtam.gate9.v2.search.SearchScreen
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.new_feed_header_view.view.*

class NewFeedHeaderView(context: Context?, attr: AttributeSet? = null, defStyle: Int = 0) : LinearLayout(context, attr, defStyle){

    init {
        View.inflate(context, R.layout.new_feed_header_view, this)
        initEvents()
    }

    private var mNavigation: Navigation? = null
    private var mOptions: RequestOptions? = null
    private var mAdapter: SliderAdapter? = null
    private var mGroup4Adapter: GroupBannerAdapter? = null

    fun initialize(navigation: Navigation?, options: RequestOptions?){
        mNavigation = navigation
        mOptions = options
        initView()
    }

    fun bindingGroupGames(data: List<Game>?) {
        if (data == null) return
        val games: List<Game> = if (data.size < 4) {
            data
        } else {
            data.subList(0, 4)
        }
        rv4Banners?.visibility = View.VISIBLE
        mGroup4Adapter?.setNewData(games)
    }

    fun bindingBanner(data: List<Banner>?) {
        mAdapter?.setData(data?.map { it.url ?: "" } ?: mutableListOf())
    }

    private fun initView(){
        initBanner()
        initRvGroup()
    }

    private fun initEvents(){
        tvVideo?.setOnClickListener {
            mNavigation?.addFragment(FeatureScreen.newInstance(CategoryTab.VIDEO.tab))
        }
        tvTheme?.setOnClickListener {
            mNavigation?.addFragment(FeatureScreen.newInstance(CategoryTab.THEME.tab))
        }
        tvSpecial?.setOnClickListener {
            mNavigation?.addFragment(FeatureScreen.newInstance(CategoryTab.SPECIAL.tab))
        }
        tvEvent?.setOnClickListener {
            mNavigation?.addFragment(FeatureScreen.newInstance(CategoryTab.EVENT.tab))
        }
        tvGame?.setOnClickListener {
            mNavigation?.addFragment(MXHGameScreen.newInstance())
        }}

    private fun initBanner(){
        val unwrapContext = context ?: return
        mAdapter = SliderAdapter(unwrapContext, mOptions)
        slider.adapter = mAdapter
    }

    private fun initRvGroup() {
        mGroup4Adapter = GroupBannerAdapter(mNavigation, mOptions!!)
        rv4Banners?.layoutManager = GridLayoutManager(context, 2)
        rv4Banners?.adapter = mGroup4Adapter
    }
}

class SliderAdapter(
        private val mContext: Context,
        private val options: RequestOptions?) : PagerAdapter(){

    private var mImages = mutableListOf<String>()

    fun setData(data: List<String>){
        mImages.clear()
        mImages.addAll(data)
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(mContext).inflate(R.layout.slider_item_layout, container, false)
        val imageView = view.findViewById<ImageView>(R.id.image)
        Glide.with(mContext)
                .apply { options }
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