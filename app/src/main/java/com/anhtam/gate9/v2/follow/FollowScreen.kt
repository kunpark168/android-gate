package com.anhtam.gate9.v2.follow


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.SharePageAdapter
import com.anhtam.gate9.storage.StorageManager
import com.anhtam.gate9.utils.autoCleared
import com.anhtam.gate9.v2.auth.login.LoginScreen
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.follow_screen.*

class FollowScreen : DaggerNavigationFragment() {


    companion object{
        fun newInstance() = FollowScreen()
    }
    private var mAdapter: SharePageAdapter by autoCleared()
    private val mTabs by lazy { arrayOf(tabAll, tabGameThu, tabNPH, tabGame)}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.follow_screen, container, false)
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
                followingViewPager?.currentItem = index
//                if(index > 0){
//                    // check auth
//                    if(checkLogin()){
//                        //continue
//                    } else {
//                        navigation?.addFragment(LoginScreen.newInstance(false))
//                        return@setOnClickListener
//                    }
//                } TODO Auth
            }
        }
    }

    private fun checkLogin() : Boolean {
        val accessToken = StorageManager.getAccessToken()
        return accessToken.isNotEmpty()
    }

    private fun setUpViewPager() {
        val fragments = ArrayList<Fragment>()
        fragments.add(TatCaFollowingScreen.newInstance(1))
        fragments.add(TatCaFollowingScreen.newInstance(2))
        fragments.add(TatCaFollowingScreen.newInstance(3))
        fragments.add(TatCaFollowingScreen.newInstance(4))
        mAdapter = SharePageAdapter(childFragmentManager,fragments)
        followingViewPager?.adapter = mAdapter
        followingViewPager?.offscreenPageLimit = 4
        followingViewPager?.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
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
}
