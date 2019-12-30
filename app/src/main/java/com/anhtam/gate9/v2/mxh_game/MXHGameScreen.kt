package com.anhtam.gate9.v2.mxh_game

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.SharePageAdapter
import com.anhtam.gate9.storage.StorageManager
import com.anhtam.gate9.utils.autoCleared
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.mxh_game_screen.*

class MXHGameScreen : DaggerNavigationFragment(){

    companion object {
        fun newInstance() = MXHGameScreen()
    }

    private var mAdapter: SharePageAdapter by autoCleared()
    private val mTabs by lazy { arrayOf(tabAll, tabLove, tabDownload, tabFollowing)}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.window?.statusBarColor = ContextCompat.getColor(context!!, R.color.color_main_blue)
        return inflater.inflate(R.layout.mxh_game_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun menuRes() = R.menu.menu_search_avatar

    private fun init(){
        setUpViewPager()
        initEvents()
        observer()
    }

    private fun observer(){

    }

    private fun initEvents() {
        for(index in mTabs.toList().indices){
            mTabs[index]?.setOnClickListener {
                enableTab(index)
                vpGame?.currentItem = index
                if(index > 0){
                    // check auth
                    if(checkLogin()){
                        //continue
                    } else {
                        navigation?.addFragment(LoginScreen.newInstance())
                        return@setOnClickListener
                    }
                }
            }
        }
    }

    private fun checkLogin() : Boolean {
        val accessToken = StorageManager.getAccessToken()
        return accessToken.isNotEmpty()
    }

    private fun setUpViewPager() {
        val fragments = ArrayList<MXHGameTabFragment>()
        fragments.add(MXHGameTabFragment.newInstance(MXHGameTab.ALL))
        fragments.add(MXHGameTabFragment.newInstance(MXHGameTab.LOVE))
        fragments.add(MXHGameTabFragment.newInstance(MXHGameTab.DOWNLOAD))
        fragments.add(MXHGameTabFragment.newInstance(MXHGameTab.FOLLOWING))
        mAdapter = SharePageAdapter(childFragmentManager,fragments)
        vpGame?.adapter = mAdapter
        vpGame?.offscreenPageLimit = 0
        vpGame?.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                enableTab(position)
            }
        })
    }

    private fun enableTab(position: Int) {
        for(index in mTabs.toList().indices) {
            mTabs[index]?.setVisibilityIndicator(position == index)
        }
    }

    enum class MXHGameTab(val data: Int){
        ALL(1), LOVE(2), DOWNLOAD(3), FOLLOWING(4)
//        fun toInt() = this.data
    }
}