package com.humam.mobile.finalprojectkotlin.view.teamdetail

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.humam.mobile.finalprojectkotlin.R
import com.humam.mobile.finalprojectkotlin.adapter.ViewPagerAdapter
import com.humam.mobile.finalprojectkotlin.helper.FavoriteTeamDB
import com.humam.mobile.finalprojectkotlin.model.TeamsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity

class TeamDetail : AppCompatActivity() {

    companion object {
        private const val EXTRA_PARAM = "EXTRA_PARAM"

        fun start(context: Context?, team: TeamsItem) {
            context?.startActivity<TeamDetail>(EXTRA_PARAM to team)
        }
    }

    private lateinit var team: TeamsItem

    private var menuFavorites: Menu? = null
    private var isFavorite: Boolean = false

    private val favoritedTeamsDb = FavoriteTeamDB()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        setupData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        menuFavorites = menu
        setFavorite()

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()

                true
            }

            R.id.mn_favorites -> {
                if (isFavorite) {
                    favoritedTeamsDb.removeFavorites(ctx, team)
                } else {
                    favoritedTeamsDb.addFavorites(ctx, team)
                }

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupData() {
//        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadData()

        isFavorite = favoritedTeamsDb.isFavorite(ctx, team)
        view_pager.adapter = ViewPagerAdapter(supportFragmentManager,
                mapOf(
                        getString(R.string.title_overview) to TeamsOverviewFragment.newInstance(team.strDescriptionEN.toString()),
                        getString(R.string.title_players) to TeamsPlayersFragment.newInstance(team.strTeam.toString())
                )
        )
        tab_layout.setupWithViewPager(view_pager)
    }

    private fun loadData() {
        team = intent.getParcelableExtra(EXTRA_PARAM)

        Picasso.get()
                .load(team.strTeamBadge)
                .into(iv_team)

        tv_name.text = team.strTeam
        tv_year.text = team.intFormedYear
        tv_stadium.text = team.strStadium
    }

    private fun setFavorite() {
        if (isFavorite) {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_start_full)
        } else {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_border)
        }
    }
}
