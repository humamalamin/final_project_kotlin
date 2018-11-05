package com.humam.mobile.finalprojectkotlin.view.teamdetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.humam.mobile.finalprojectkotlin.R
import com.humam.mobile.finalprojectkotlin.Utils.invisible
import com.humam.mobile.finalprojectkotlin.Utils.visible
import com.humam.mobile.finalprojectkotlin.adapter.PlayerAdapter
import com.humam.mobile.finalprojectkotlin.model.PlayerItem
import com.humam.mobile.finalprojectkotlin.network.ApiRepository
import com.humam.mobile.finalprojectkotlin.view.playerdetail.PlayerDetail
import kotlinx.android.synthetic.main.fragment_team_players.*
import org.jetbrains.anko.bundleOf

class TeamsPlayersFragment: Fragment(), TeamPlayersView {

    companion object {
        private const val EXTRA_PARAM = "EXTRA_PARAM"

        fun newInstance(args: String): TeamsPlayersFragment {
            val fragment = TeamsPlayersFragment()
            fragment.arguments = bundleOf(EXTRA_PARAM to args)

            return fragment
        }
    }

    private lateinit var presenter: TeamPlayerPresenter

    private lateinit var players: MutableList<PlayerItem>
    private lateinit var listAdapter: PlayerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_players, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()
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

    override fun showPlayerList(data: MutableList<PlayerItem>) {
        players.clear()
        players.addAll(data)
        listAdapter.notifyDataSetChanged()
        recycler_view.scrollToPosition(0)
    }

    private fun setupData(){
        presenter = TeamPlayerPresenter(this, ApiRepository(), Gson())

        players = mutableListOf()
        listAdapter = PlayerAdapter(players) {
            PlayerDetail.start(context, it)
        }

        with(recycler_view) {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        presenter.getAllPlayer(arguments?.getString(EXTRA_PARAM).toString())
    }

}