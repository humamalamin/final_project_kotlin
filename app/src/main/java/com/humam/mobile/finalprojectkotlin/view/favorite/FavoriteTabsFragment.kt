package com.humam.mobile.finalprojectkotlin.view.favorite

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.humam.mobile.finalprojectkotlin.R
import com.humam.mobile.finalprojectkotlin.Utils.TypeFavorite
import com.humam.mobile.finalprojectkotlin.Utils.invisible
import com.humam.mobile.finalprojectkotlin.Utils.visible
import com.humam.mobile.finalprojectkotlin.adapter.MatchAdapter
import com.humam.mobile.finalprojectkotlin.adapter.TeamAdapter
import com.humam.mobile.finalprojectkotlin.model.EventsItem
import com.humam.mobile.finalprojectkotlin.model.TeamsItem
import com.humam.mobile.finalprojectkotlin.view.matchdetail.MatchDetail
import com.humam.mobile.finalprojectkotlin.view.teamdetail.TeamDetail
import kotlinx.android.synthetic.main.fragment_favorite_matches.*
import org.jetbrains.anko.bundleOf

class FavoriteTabsFragment: Fragment(), FavoriteTabsView {

    companion object {
        private const val TYPE_FAVORITES = "TYPE_FAVORITES"

        fun newInstance(fragmentType: TypeFavorite): FavoriteTabsFragment {
            val fragment = FavoriteTabsFragment()
            fragment.arguments = bundleOf(TYPE_FAVORITES to fragmentType)

            return fragment
        }
    }

    private lateinit var fragmentType: TypeFavorite

    private lateinit var presenter: FavoriteTabsPresenter

    private lateinit var events: MutableList<EventsItem>
    private lateinit var eventsAdapter: MatchAdapter

    private lateinit var teams: MutableList<TeamsItem>
    private lateinit var teamsAdapter: TeamAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite_matches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()
    }

    override fun onResume() {
        super.onResume()

        if (fragmentType == TypeFavorite.MATCHES) presenter.getFavoriteEvent()
        else presenter.getFavoriteTeam()
    }

    override fun showLoading() {
        progress_bar.visible()
        recycler_view.invisible()
        tv_empty.invisible()
    }

    override fun hideLoading() {
        progress_bar.invisible()
        recycler_view.visible()
        tv_empty.invisible()
    }

    override fun showEmptyData() {
        progress_bar.invisible()
        recycler_view.invisible()
        tv_empty.visible()
    }

    override fun showEventList(data: MutableList<EventsItem>) {
        events.clear()
        events.addAll(data)
        eventsAdapter.notifyDataSetChanged()
        recycler_view.scrollToPosition(0)
    }

    override fun showTeamList(data: MutableList<TeamsItem>) {
        teams.clear()
        teams.addAll(data)
        teamsAdapter.notifyDataSetChanged()
        recycler_view.scrollToPosition(0)
    }

    private fun setupData(){
        fragmentType = arguments?.get(TYPE_FAVORITES) as TypeFavorite
        presenter = FavoriteTabsPresenter(context, this)

        when (fragmentType) {
            TypeFavorite.MATCHES -> {
                events = mutableListOf()
                eventsAdapter = MatchAdapter(events) {
                    MatchDetail.start(context, it)
                }
            }

            TypeFavorite.TEAMS -> {
                teams = mutableListOf()
                teamsAdapter = TeamAdapter(teams) {
                    TeamDetail.start(context, it)
                }
            }
        }

        with(recycler_view) {
            adapter = if (fragmentType == TypeFavorite.MATCHES) eventsAdapter else teamsAdapter
            layoutManager = LinearLayoutManager(context)
            if (fragmentType == TypeFavorite.TEAMS) addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        if (fragmentType == TypeFavorite.MATCHES) presenter.getFavoriteEvent()
        else presenter.getFavoriteTeam()
    }
}