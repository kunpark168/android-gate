package com.anhtam.gate9.v2.messenger.inbox

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.anhtam.domain.v2.protocol.User
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.chat.ChannelAdapter
import com.anhtam.gate9.navigation.FragmentResultListener
import com.anhtam.gate9.v2.lib.then
import com.anhtam.gate9.v2.main.DaggerNavigationFragment
import com.anhtam.gate9.v2.messenger.ChannelViewModel
import kotlinx.android.synthetic.main.channel_fragment.*
import of.bum.network.helper.Resource
import java.util.*
import javax.inject.Inject

class ChannelLetterFragment(private val mUser: User) : DaggerNavigationFragment(R.layout.channel_fragment), FragmentResultListener {

    companion object {
        fun newInstance(user: User) = ChannelLetterFragment(user)
    }

    private val viewModel: ChannelViewModel by viewModels { vmFactory }
    @Inject
    lateinit var mAdapter: ChannelAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLetterByUser(mUser)
        initRv()
        events()
        observerLetters()
    }

    override fun menuRes() = R.menu.menu_search_avatar

    private fun initRv() {
        mAdapter.setOnLoadMoreListener({
            if (viewModel.mIsFilter) viewModel.loadMore() else viewModel.loadData()
        }, rv)
        rv?.adapter = mAdapter
        rv?.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
    }

    override fun onResume() {
        super.onResume()
        shimmer?.startShimmerAnimation()
    }

    override fun onPause() {
        shimmer?.stopShimmerAnimation()
        super.onPause()
    }

    private fun events() {
        tvStartDate?.setOnClickListener {
            val unwrappedContext = context ?: return@setOnClickListener
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, 1)
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
            val currentMonth = calendar.get(Calendar.MONTH) + 1
            val currentYear = calendar.get(Calendar.YEAR)
            DatePickerDialog(unwrappedContext, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                tvStartDate?.text = "$year-$month-$dayOfMonth"
                showShimmer()
                val endDate = if (tvEndDate?.text.isNullOrEmpty()) "$currentYear-$currentMonth-$currentDay" else tvEndDate?.text?.toString()
                viewModel.filterDate(searchEditText?.text?.toString(), tvStartDate?.text?.toString(), endDate)
            }, currentYear, currentMonth, currentDay).show()
        }
        tvEndDate?.setOnClickListener {
            val unwrappedContext = context ?: return@setOnClickListener
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, 1)
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
            val currentMonth = calendar.get(Calendar.MONTH) + 1
            val currentYear = calendar.get(Calendar.YEAR)
            DatePickerDialog(unwrappedContext, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                tvEndDate?.text = "$year-$month-$dayOfMonth"
                showShimmer()
                val endDate = if (tvEndDate?.text.isNullOrEmpty()) "$currentYear-$currentMonth-$currentDay" else tvEndDate?.text?.toString()
                viewModel.filterDate(searchEditText?.text?.toString(), tvStartDate?.text?.toString(), endDate)
            }, currentYear, currentMonth, currentDay).show()
        }
        createFloatingButton?.setOnClickListener {
            navigation?.addFragment(CreateLetterFragment.newInstance(mUser))
        }
        searchButton?.setOnClickListener {
            showShimmer()
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, 1)
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
            val currentMonth = calendar.get(Calendar.MONTH) + 1
            val currentYear = calendar.get(Calendar.YEAR)
            val endDate = if (tvEndDate?.text.isNullOrEmpty()) "$currentYear-$currentMonth-$currentDay" else tvEndDate?.text?.toString()
            viewModel.filterDate(searchEditText?.text?.toString(), tvStartDate?.text?.toString(), endDate)
        }
    }

    private fun showShimmer() {
        shimmer?.visibility = View.VISIBLE
        rv?.visibility = View.GONE
        shimmer?.startShimmerAnimation()
        mAdapter.setNewData(null)
    }

    private fun observerLetters() {
        viewModel.data.observe(viewLifecycleOwner, Observer {
            (it is Resource.Success).then {
                shimmer?.stopShimmerAnimation()
                shimmer?.visibility = View.GONE
                rv?.visibility = View.VISIBLE
                mAdapter.setNewData(it.data)
            }
            (it is Resource.Error).then {
                shimmer?.stopShimmerAnimation()
                shimmer?.visibility = View.GONE
                rv?.visibility = View.VISIBLE
            }
        })
    }

    override fun onFragmentResult(args: Bundle) {
        viewModel.getLetterByUser(mUser)
    }
}