package com.icebeal.gitxclone.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.icebeal.gitxclone.ui.fragment.FollowFragment

class SectionPagerAdapter(activity:AppCompatActivity) : FragmentStateAdapter(activity) {

    var username:String = ""

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        return FollowFragment.newInstance(position, username)

    }

}