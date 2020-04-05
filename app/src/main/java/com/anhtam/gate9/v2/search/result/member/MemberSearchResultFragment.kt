package com.anhtam.gate9.v2.search.result.member

import androidx.fragment.app.viewModels
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.adapter.v2.MemberAdapter
import com.anhtam.gate9.v2.search.AbstractTabSearchFragment

class MemberSearchResultFragment : AbstractTabSearchFragment<User, MemberAdapter, MemberSearchViewModel>() {
    override val mViewModel: MemberSearchViewModel by viewModels { vmFactory }
    override fun setKey(key: String) {
        mViewModel.mKey = key
    }
}