package com.anhtam.gate9.v2.shared.muilti_gallery

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.anhtam.gate9.R
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import kotlinx.android.synthetic.main.multi_choose_image_fragment.*

class MultiChooseImageScreen: DaggerNavigationFragment(){

    private var mState: State = State.Image

    companion object{
        fun newInstance() = MultiChooseImageScreen()
    }

    private fun onGalleryFolderChanged(position: Int){
        mState = State.Image
        dropIcon.rotation = 0.0f
        val folder = mViewModel.getFolders().value?.get(position) ?: return
        mViewModel.getImageByFolder(folder)
        childFragmentManager.beginTransaction()
                .replace(R.id.container, GalleryImagesFragment.newInstance())
                .commit()
    }

    private val mViewModel: GalleryViewModel by viewModels { vmFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.multi_choose_image_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        loadData()
        initView()
        observer()
        initEvents()
    }

    private fun initView(){
        mState = State.Image
        dropIcon.rotation = 0.0f
        childFragmentManager.beginTransaction()
                .replace(R.id.container, GalleryImagesFragment.newInstance())
                .commit()
    }

    private fun initEvents(){
        closeIcon?.setOnClickListener {
            navigation?.back()
        }

        filterFolderLayout?.setOnClickListener {
            when(mState){
                State.Folder -> {
                    // rotate
                    mState = State.Image
                    dropIcon.rotation = 0.0f
                    childFragmentManager.beginTransaction()
                            .replace(R.id.container, GalleryImagesFragment.newInstance())
                            .commit()

                }
                State.Image -> {
                    //
                    mState = State.Folder
                    dropIcon.rotation = 180.0f
                    childFragmentManager.beginTransaction()
                            .replace(R.id.container, GalleryFolderFragment.newInstance {
                                onGalleryFolderChanged(it)
                            })
                            .commit()
                }
            }
        }
    }

    private fun observer(){
        mViewModel.getFolders().observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) return@Observer//
            mViewModel.getImageByFolder(it.first())
        })
    }

    private fun loadData(){
        mViewModel.getAllImages()
    }

    private enum class State{
        Folder, Image
    }

}