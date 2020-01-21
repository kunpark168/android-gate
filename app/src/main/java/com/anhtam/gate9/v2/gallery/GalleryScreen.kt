package com.anhtam.gate9.v2.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.utils.autoCleared
import com.anhtam.gate9.utils.toImage
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.gallery_screen.*
import javax.inject.Inject
import javax.inject.Named

class GalleryScreen private constructor(
        val mImages: List<String>,
        val mUser: User) : DaggerNavigationFragment(){

    companion object{
        fun newInstance(images: List<String>, user: User): GalleryScreen{
            return GalleryScreen(images, user)
        }
    }

    @Inject @field:Named("banner") lateinit var bannerOptions: RequestOptions
    @Inject @field:Named("avatar") lateinit var avatarOptions: RequestOptions
    private var mAdapter by autoCleared<GalleryAdapter>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.gallery_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun statusColor() = R.color.black

    private fun init(){
        mAdapter = GalleryAdapter()
        viewPager.adapter = mAdapter
        viewPager?.setPageTransformer(true, DepthPageTransformer())
        Glide.with(this@GalleryScreen)
                .apply { RequestOptions.circleCropTransform().apply { avatarOptions } }
                .load(mUser.mAvatar?.toImage())
                .fitCenter()
                .into(imgAvatar)
        tabLayout?.setupWithViewPager(viewPager)
        tvName.text = mUser.mName
    }

    inner class GalleryAdapter : PagerAdapter(){

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(context).inflate(R.layout.gallery_item_layout, container, false)
            val imageView = view.findViewById<ImageView>(R.id.image)
            Glide.with(this@GalleryScreen)
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

private const val MIN_SCALE = 0.75f

class DepthPageTransformer : ViewPager.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        view.apply {
            val pageWidth = width
            when {
                position < -1 -> { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    alpha = 0f
                }
                position <= 0 -> { // [-1,0]
                    // Use the default slide transition when moving to the left page
                    alpha = 1f
                    translationX = 0f
                    scaleX = 1f
                    scaleY = 1f
                }
                position <= 1 -> { // (0,1]
                    // Fade the page out.
                    alpha = 1 - position

                    // Counteract the default slide transition
                    translationX = pageWidth * -position

                    // Scale the page down (between MIN_SCALE and 1)
                    val scaleFactor = (MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position)))
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                }
                else -> { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    alpha = 0f
                }
            }
        }
    }
}