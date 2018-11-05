package com.humam.mobile.finalprojectkotlin.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import com.humam.mobile.finalprojectkotlin.R
import com.humam.mobile.finalprojectkotlin.view.favorite.FragmentFavorite
import com.humam.mobile.finalprojectkotlin.view.matches.MatchesFragment
import com.humam.mobile.finalprojectkotlin.view.team.TeamsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupData()
    }

    private fun setupData(){
        setFragment(MatchesFragment())
        listenBottomNavigationView();
    }

    private fun listenBottomNavigationView(){
        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId){
                bottom_navigation_view.selectedItemId -> return@setOnNavigationItemSelectedListener false

                R.id.bnv_matches -> {
                    setFragment(MatchesFragment())
                    return@setOnNavigationItemSelectedListener  true
                }

                R.id.bnv_teams -> {
                    setFragment(TeamsFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.bnv_favorites -> {
                    setFragment(FragmentFavorite())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }

    private fun setFragment(fragment:Fragment){
        with(supportFragmentManager.beginTransaction()){
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            replace(R.id.frame_layout, fragment)
            commit()
        }
    }
}
