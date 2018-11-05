package com.humam.mobile.finalprojectkotlin.view.team

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.SearchView
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.gson.Gson
import com.humam.mobile.finalprojectkotlin.R
import com.humam.mobile.finalprojectkotlin.Utils.gone
import com.humam.mobile.finalprojectkotlin.Utils.invisible
import com.humam.mobile.finalprojectkotlin.Utils.loadFirstText
import com.humam.mobile.finalprojectkotlin.Utils.visible
import com.humam.mobile.finalprojectkotlin.adapter.TeamAdapter
import com.humam.mobile.finalprojectkotlin.model.LeaguesItem
import com.humam.mobile.finalprojectkotlin.model.LeagueResponse
import com.humam.mobile.finalprojectkotlin.model.TeamsItem
import com.humam.mobile.finalprojectkotlin.network.ApiRepository
import com.humam.mobile.finalprojectkotlin.view.teamdetail.TeamDetail
import kotlinx.android.synthetic.main.fragment_team.*

class TeamsFragment: Fragment(), TeamView {

    private lateinit var presenter: TeamPresenter

    private lateinit var leagues: LeaguesItem

    private lateinit var teams: MutableList<TeamsItem>
    private lateinit var listAdapter: TeamAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.menu_search_view, menu)

        var searchmenu = menu?.findItem(R.id.mn_search_view)
        val searchview = searchmenu?.actionView as SearchView

        listenSearchView(searchview)
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

    override fun showLeagueList(data: LeagueResponse) {
        spinner.adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, data.leagues)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagues = spinner.selectedItem as LeaguesItem

                presenter.getTeamAll(leagues.strLeague.toString())
            }
        }
    }

    override fun showTeamList(data: MutableList<TeamsItem>) {
        teams.clear()
        teams.addAll(data)
        listAdapter.notifyDataSetChanged()
        recycler_view.scrollToPosition(0)
    }


    private fun setupData(){
        setHasOptionsMenu(true)

        with(activity as AppCompatActivity) {
            setSupportActionBar(toolbar)
        }

        presenter = TeamPresenter(this, ApiRepository(), Gson())

        spinner.loadFirstText(context!!)

        teams = mutableListOf()
        listAdapter = TeamAdapter(teams) {
            TeamDetail.start(context, it)
        }

        with(recycler_view) {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        presenter.getLeagueAll()
    }

    private fun listenSearchView(searchview: SearchView){
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.getTeamSearch(query.toString())

                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.toString().isEmpty()) {
                    spinner_container.visible()
                    presenter.getTeamAll(spinner.selectedItem.toString())
                } else spinner_container.gone()

                return true
            }
        })
    }
}