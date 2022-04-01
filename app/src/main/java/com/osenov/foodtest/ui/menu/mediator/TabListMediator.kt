package com.osenov.foodtest.ui.menu.mediator

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout

class TabListMediator(
    private val mRecyclerView: RecyclerView,
    private val mTabLayout: TabLayout,
    private val appBar: AppBarLayout,
    private var mIndices: List<Int>
) {

    private var mRecyclerState = RecyclerView.SCROLL_STATE_IDLE
    private var mTabClickFlag = false

    private val smoothScroller: RecyclerView.SmoothScroller =
        object : LinearSmoothScroller(mRecyclerView.context) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }

    private var tabViewCompositeClickListener: TabViewCompositeClickListener =
        TabViewCompositeClickListener(mTabLayout)

    fun attach() {
        tabViewCompositeClickListener.addListener { _, _ -> mTabClickFlag = true }
        tabViewCompositeClickListener.build()
        mTabLayout.addOnTabSelectedListener(onTabSelectedListener)
        mRecyclerView.addOnScrollListener(onScrollListener)
    }

    fun detach() {
        mRecyclerView.clearOnScrollListeners()
        for (i in 0 until mTabLayout.tabCount) {
            mTabLayout.getTabAt(i)?.view?.setOnClickListener(null)
        }
        for (i in tabViewCompositeClickListener.getListeners().indices) {
            tabViewCompositeClickListener.getListeners().toMutableList().removeAt(i)
        }
        mTabLayout.removeOnTabSelectedListener(onTabSelectedListener)
        mRecyclerView.removeOnScrollListener(onScrollListener)
    }

    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {

            if (!mTabClickFlag) return

            if (tab.position == 0) {
                appBar.setExpanded(true)
            } else {
                appBar.setExpanded(false)
            }
            smoothScroller.targetPosition = mIndices[tab.position]
            mRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {}
        override fun onTabReselected(tab: TabLayout.Tab) {}
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            mRecyclerState = newState
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                mTabClickFlag = false
                setTabPosition()
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (mTabClickFlag) return

            if (mRecyclerState == RecyclerView.SCROLL_STATE_DRAGGING
                || mRecyclerState == RecyclerView.SCROLL_STATE_SETTLING
            ) {
                setTabPosition()
            }
        }
    }

    private fun setTabPosition() {
        val itemPosition = getRecyclerPosition()

        for (i in mIndices.indices) {
            if (i + 1 < mIndices.size && itemPosition >= mIndices[i] && itemPosition < mIndices[i + 1]) {
                selectTab(i)
            } else if (i + 1 == mIndices.size && itemPosition >= mIndices[i]) {
                selectTab(i)
            }
        }
    }

    private fun selectTab(position: Int) {
        if (mTabLayout.getTabAt(position)?.isSelected == false) {
            mTabLayout.getTabAt(position)?.select()
        }
    }

    private fun getRecyclerPosition() = (mRecyclerView.layoutManager as LinearLayoutManager)
        .findFirstCompletelyVisibleItemPosition()
}