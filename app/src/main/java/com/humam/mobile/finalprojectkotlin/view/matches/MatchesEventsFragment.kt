package com.humam.mobile.finalprojectkotlin.view.matches

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.gson.Gson
import com.humam.mobile.finalprojectkotlin.R
import com.humam.mobile.finalprojectkotlin.Utils.TypeMatch
import com.humam.mobile.finalprojectkotlin.Utils.invisible
import com.humam.mobile.finalprojectkotlin.Utils.loadFirstText
import com.humam.mobile.finalprojectkotlin.Utils.visible
import com.humam.mobile.finalprojectkotlin.adapter.MatchAdapter
import com.humam.mobile.finalprojectkotlin.model.EventsItem
import com.humam.mobile.finalprojectkotlin.model.LeagueResponse
import com.humam.mobile.finalprojectkotlin.model.LeaguesItem
import com.humam.mobile.finalprojectkotlin.network.ApiRepository
import com.humam.mobile.finalprojectkotlin.view.matchdetail.MatchDetail
import kotlinx.android.synthetic.main.fragment_match_events.*
import org.jetbrains.anko.bundleOf

class MatchesEventsFragment : Fragment(), MatchEventsView {

    companion object {
        private const val TYPE_MATCHES = "TYPE_MATCHES"

        fun newInstance(fragmentType: TypeMatch): MatchesEventsFragment {
            val fragment = MatchesEventsFragment()
            fragment.arguments = bundleOf(TYPE_MATCHES to fragmentType)

            return fragment
        }
    }

    private lateinit var fragmentType: TypeMatch

    private lateinit var presenter: MatchEventPresenter

    private lateinit var league: LeaguesItem

    private lateinit var events: MutableList<EventsItem>
    private lateinit var listAdapter: MatchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_match_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEnv()
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
                league = spinner.selectedItem as LeaguesItem

                when (fragmentType) {
                    TypeMatch.NEXT -> presenter.getEventsNext(league.idLeague.toString())
                    TypeMatch.LAST -> presenter.getEventsLast(league.idLeague.toString())
                }
            }
        }
    }

    override fun showEventList(data: MutableList<EventsItem>) {
        events.clear()
        events.addAll(data)
        listAdapter.notifyDataSetChanged()
        recycler_view.scrollToPosition(0)
    }

    private fun setupEnv() {
        fragmentType = arguments?.get(TYPE_MATCHES) as TypeMatch
        presenter = MatchEventPresenter(this, ApiRepository(), Gson())

        spinner.loadFirstText(context!!)

        events = mutableListOf()
        listAdapter = MatchAdapter(events) {
            MatchDetail.start(context, it)
        }

        with(recycler_view) {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }

        presenter.getLeagueAll()
    }

}