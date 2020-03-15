package com.anhtam.gate9.v2.chi_tiet_bai_viet

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.anhtam.domain.v2.Article
import com.anhtam.domain.v2.protocol.Game
import com.anhtam.domain.v2.protocol.User
import com.anhtam.domain.v2.wrap.WrapArticle
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.GroupBannerAdapter
import com.anhtam.gate9.adapter.v2.PhotoAdapter
import com.anhtam.gate9.adapter.v2.du_lieu.RelatedAdapter
import com.anhtam.gate9.restful.BackgroundTasks
import com.anhtam.gate9.utils.format
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.BackgroundViewModel
import com.anhtam.gate9.v2.game_detail.DetailGameFragment
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.newfeed.SliderAdapter
import com.anhtam.gate9.v2.nph_detail.DetailNPHFragment
import com.anhtam.gate9.v2.user_detail.DetailUserFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.phrase.Phrase
import of.bum.network.helper.Resource
import javax.inject.Inject
import javax.inject.Named

abstract class ChiTietBaiVietScreen(private val mId: Int,
                           private val mArticle: Article?,
                           @LayoutRes private val layoutRes: Int,
                           private val mTab: Int) : DaggerNavigationFragment(layoutRes) {

    private val mViewModel: ChiTietBaiVietViewModel by viewModels { vmFactory }
    @Inject @field:Named("banner") lateinit var bannerOptions: RequestOptions
    @Inject @field:Named("avatar") lateinit var avatarOptions: RequestOptions
    @Inject lateinit var mConcernAdapter: RelatedAdapter
    @Inject lateinit var mNewListAdapter: RelatedAdapter
    protected var mWrapArticle: WrapArticle? = null

    abstract fun updateUI()
    override fun menuRes() = R.menu.menu_chat_search_more

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        observer()
    }


    protected fun navigateToUser() {
        val user = mArticle?.mUser ?: return
        val roleId = user.mRoleId ?: return
        val id = user.mId ?: return
        if (roleId != 5){
            navigation?.addFragment(DetailUserFragment.newInstance(id))
        } else {
            navigation?.addFragment(DetailNPHFragment.newInstance(id))
        }
    }

    protected fun navigateToGame() {
        val id = mArticle?.mGame?.gameId ?: return
        navigation?.addFragment(DetailGameFragment.newInstance(id))
    }

    private fun loadData() {
        showProgress()
        mViewModel.setId(mId, mTab)
    }

    private fun observer() {
        mViewModel.mArticle.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgress()
                    mWrapArticle = it.data
                    updateUI()
                }
                is Resource.Error -> {
                    hideProgress()
                }
            }
        })
    }

    protected fun getHtmlData(bodyHTML: String?): String {
        val head = "<head><style>img{max-width: 100%; width:auto; height: auto;}iframe{max-width: 100%; width:auto; height: auto;}</style></head>"
        return "<html>$head<body>$bodyHTML</body></html>"
    }


    protected fun initGame(game: Game?) {
        val unwrapContext = context ?: return
        val csGame = view?.findViewById<ConstraintLayout>(R.id.csGame)
        val imgGame = view?.findViewById<ImageView>(R.id.imgGame)
        val imgNewGame = view?.findViewById<ImageView>(R.id.imgNewGame)
        val tvTitle = view?.findViewById<TextView>(R.id.tvTitle)
        val parameterTextView = view?.findViewById<TextView>(R.id.parameterTextView)
        val ratingTextView = view?.findViewById<TextView>(R.id.ratingTextView)
        val amountRateTextView = view?.findViewById<TextView>(R.id.amountRateTextView)
        val ratingBar = view?.findViewById<RatingBar>(R.id.ratingBar)
        if (game == null || game.gameId == 0) {
            csGame?.visibility = View.GONE
            imgGame?.visibility = View.GONE
        } else {
            csGame?.visibility = View.VISIBLE
            imgGame?.visibility = View.VISIBLE

            Glide.with(unwrapContext)
                    .load(game.avatar?.toImage())
                    .apply(bannerOptions)
                    .into(imgGame!!)

            Glide.with(unwrapContext)
                    .load(game.avatar?.toImage())
                    .apply(bannerOptions)
                    .into(imgNewGame!!)

            tvTitle?.text = game.name
            parameterTextView?.text = Phrase.from(getString(R.string.follower_amount_and_post_amount))
                    .put("follower", game.follower?.toString() ?: "0")
                    .put("post", game.post?.toString() ?: "0")
                    .format()
            ratingBar?.rating = game.mAvgRate?.toFloat() ?: 0.0f
            ratingTextView?.text = "${game.mAvgRate?.format(1) ?: "0"} /"
            amountRateTextView?.text = game.mNumRating?.toString() ?: "0"
        }
    }
}