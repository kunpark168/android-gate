package com.anhtam.gate9.v2.search.social


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anhtam.gate9.R

/**
 * A simple [Fragment] subclass.
 */
class SocialSearchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_social_search, container, false)
    }
    companion object {
        fun newInstance() : SocialSearchFragment {
            return SocialSearchFragment()
        }
    }
}
