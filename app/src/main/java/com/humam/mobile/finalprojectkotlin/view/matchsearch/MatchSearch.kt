package com.humam.mobile.finalprojectkotlin.view.matchsearch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import com.google.gson.Gson
import com.humam.mobile.finalprojectkotlin.R
import com.humam.mobile.finalprojectkotlin.Utils.invisible
import com.humam.mobile.finalprojectkotlin.Utils.visible
import com.humam.mobile.finalprojectkotlin.adapter.MatchAdapter
import com.humam.mobile.finalprojectkotlin.model.EventsItem
import com.humam.mobile.finalprojectkotlin.network.ApiRepository
import com.humam.mobile.finalprojectkotlin.view.matchdetail.MatchDetail

import kotlinx.android.synthetic.main.activity_match_search.*

class MatchSearch : AppCompatActivity(), MatchSearchView {

    private lateinit var presenter: MatchSearchPresenter

    private lateinit var events: MutableList<EventsItem>
    private lateinit var listAdapter: MatchAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_search)

        setupData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_view, menu)

        val searchMenu = menu?.findItem(R.id.mn_search_view)
        val searchView = searchMenu?.actionView as SearchView

        searchView.isIconified = false

        listenSearchView(searchView)

        return super.onCreateOptionsMenu(menu)
    }

    override fun showLoading() {
        progress_bar.visible()
        recycle_view.invisible()
        tv_empty.invisible()
    }

    override fun hideLoading() {
        progress_bar.invisible()
        recycle_view.visible()
        tv_empty.invisible()
    }

    override fun showEmptyData() {
        progress_bar.invisible()
        recycle_view.invisible()
        tv_empty.visible()
    }

    override fun showEventList(data: MutableList<EventsItem>) {
        events.clear()
        events.addAll(data)
        listAdapter.notifyDataSetChanged()
        recycle_view.scrollToPosition(0)
    }

    private fun setupData(){
        presenter = MatchSearchPresenter(this, ApiRepository(), Gson())

        events = mutableListOf()
        listAdapter = MatchAdapter(events) {
            MatchDetail.start(this, it)
        }

        with(recycle_view) {
            adapter = listAdapter
            layoutManager = android.support.v7.widget.LinearLayoutManager(context)
        }

        presenter.getEventsSearch()
    }

    private fun listenSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.getEventsSearch(query.toString())

                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.toString().isEmpty()) presenter.getEventsSearch(query.toString())

                return true
            }
        })
    }
}
