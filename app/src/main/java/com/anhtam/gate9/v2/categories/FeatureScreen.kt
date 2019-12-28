package com.anhtam.gate9.v2.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import com.anhtam.gate9.R
import com.anhtam.gate9.ui.discussion.user.UserDiscussionActivity
import com.anhtam.gate9.adapter.SharePageAdapter
import com.anhtam.gate9.utils.debounceClick
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.viewmodel.ViewModelProviderFactory
import com.anhtam.gate9.vo.model.Category
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.feature_screen.*
import java.util.*
import javax.inject.Inject

class FeatureScreen : DaggerNavigationFragment() {

    private var tab: Int = 0
    @Inject
    lateinit var factory: ViewModelProviderFactory
    private lateinit var mAdapter: SharePageAdapter
    private val categoryViewModel: CategoryViewmodel by viewModels { vmFactory }
    private var idUser: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.feature_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        getData()
        initControls()
        displayUser()
    }

    companion object {
        fun newInstance(tab: Int): FeatureScreen {
            val screen = FeatureScreen()
            screen.tab = tab
            return screen
        }
    }

    private fun initViewPager() {
        val fragments = ArrayList<Fragment>()
        fragments.add(FeatureChildrenFragment.newInstance(CategoryTab.VIDEO.tab))
        fragments.add(FeatureChildrenFragment.newInstance(CategoryTab.THEME.tab))
        fragments.add(FeatureChildrenFragment.newInstance(CategoryTab.SPECIAL.tab))
        fragments.add(FeatureChildrenFragment.newInstance(CategoryTab.EVENT.tab))
        mAdapter = SharePageAdapter(childFragmentManager, fragments)
        vpCategory.adapter = mAdapter
        vpCategory.offscreenPageLimit = 4

        vpCategory.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                onTabSelected(position)
            }

        })
    }

    private fun getData() {
        vpCategory.currentItem = tab
        setTitleTab(tab)
        categoryViewModel.requestData()
        idUser = categoryViewModel.userId
    }

    private fun setTitleTab(tab: Int) {
        when (tab) {
            CategoryTab.VIDEO.tab -> tvTtileTab.text = resources.getString(R.string.video)
            CategoryTab.THEME.tab -> tvTtileTab.text = resources.getString(R.string.theme)
            CategoryTab.SPECIAL.tab -> tvTtileTab.text = resources.getString(R.string.special)
            CategoryTab.EVENT.tab -> tvTtileTab.text = resources.getString(R.string.event)
        }
    }

    private fun onTabSelected(tab: Int) {
        vLineVideo.visibility = if (tab == CategoryTab.VIDEO.tab) View.VISIBLE else View.INVISIBLE
        vLineTheme.visibility = if (tab == CategoryTab.THEME.tab) View.VISIBLE else View.INVISIBLE
        vLineSpecial.visibility = if (tab == CategoryTab.SPECIAL.tab) View.VISIBLE else View.INVISIBLE
        vLineEvent.visibility = if (tab == CategoryTab.EVENT.tab) View.VISIBLE else View.INVISIBLE

        setTitleTab(tab)
    }

    private fun initControls() {
//        llVideo.debounceClick { vpCategory.currentItem = TypeGameTab.ALL.tab }
//        llTheme.debounceClick { vpCategory.currentItem = TypeGameTab.FAVOURITE.tab }
//        llSpecial.debounceClick { vpCategory.currentItem = TypeGameTab.DOWNLOADED.tab }
//        llEvent.debounceClick { vpCategory.currentItem = TypeGameTab.FOLLOWING.tab } TODO v2
        tvBackCategory.debounceClick { navigation?.back() }

        imgAvatar.debounceClick {
            navigation?.addFragment(UserDiscussionActivity.newInstance(idUser?.toInt()?:0, Category.Member))
        }
    }

    private fun displayUser() {
        Glide.with(this)
                .load(categoryViewModel.userAvatar)
                .into(imgAvatar)
    }


}
