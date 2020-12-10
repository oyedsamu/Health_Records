package com.decadevs.healthrecords.adapters

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter


class IntroSliderAdapter (
    list : ArrayList<Fragment>,
    fm : FragmentManager,
    lifecycle : Lifecycle ) : FragmentStateAdapter (fm, lifecycle) {

    private val fragmentList : ArrayList<Fragment> = list

    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int) = fragmentList[position]


}