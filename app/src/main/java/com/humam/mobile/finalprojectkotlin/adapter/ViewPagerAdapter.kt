package com.humam.mobile.finalprojectkotlin.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class ViewPagerAdapter(supportFragment: FragmentManager, private val map: Map<String, Fragment>): FragmentStatePagerAdapter(supportFragment) {
    private val title = map.keys.toList()
    private val fragments = map.values.toList()

    override fun getItem(p0: Int): Fragment = fragments[p0]

    override fun getCount(): Int = map.size

    override fun getPageTitle(position: Int): CharSequence? = title[position]
}