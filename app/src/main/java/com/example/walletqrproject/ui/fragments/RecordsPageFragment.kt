package com.example.walletqrproject.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.walletqrproject.R
import com.example.walletqrproject.ui.adapter.SectionPagerAdapters
import com.google.android.material.tabs.TabLayout

class RecordsPageFragment : Fragment() {

    lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_records_page, container, false)

        tabLayout = view.findViewById(R.id.tab_layout)
        viewPager = view.findViewById(R.id.view_pager)
        viewPager.offscreenPageLimit = 2

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {}
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun addFragments(viewPager: ViewPager) {
        val adapters = SectionPagerAdapters(childFragmentManager)
        adapters.addFragment(OnlyMoneyFragment(), "Деньги")
        adapters.addFragment(OnlyQrFragment(), "Qr Scanner")
        viewPager.adapter = adapters
    }

    private fun setUpViewPager(viewPager: ViewPager) {
        addFragments(viewPager)
    }
}