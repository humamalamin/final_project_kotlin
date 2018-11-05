package com.humam.mobile.finalprojectkotlin.view.favorite

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.humam.mobile.finalprojectkotlin.R
import com.humam.mobile.finalprojectkotlin.Utils.TypeFavorite
import com.humam.mobile.finalprojectkotlin.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*

class FragmentFavorite : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEnv()
    }

    private fun setupEnv() {
        with(activity as AppCompatActivity) {
            setSupportActionBar(toolbar)

            view_pager.adapter = ViewPagerAdapter(supportFragmentManager,
                    mapOf(
                            getString(R.string.title_matches).capitalize() to FavoriteTabsFragment.newInstance(TypeFavorite.MATCHES),
                            getString(R.string.title_teams).capitalize() to FavoriteTabsFragment.newInstance(TypeFavorite.TEAMS)
                    )
            )
            tab_layout.setupWithViewPager(view_pager)
        }
    }

}
