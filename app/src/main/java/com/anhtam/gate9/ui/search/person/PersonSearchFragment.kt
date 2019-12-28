package com.anhtam.gate9.ui.search.person


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.anhtam.gate9.R

/**
 * A simple [Fragment] subclass.
 */
class PersonSearchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person_search, container, false)
    }

    companion object {
        fun newInstance(): PersonSearchFragment {
            return PersonSearchFragment()
        }
    }

}
