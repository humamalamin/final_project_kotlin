package com.humam.mobile.finalprojectkotlin.view.matchdetail

import com.google.gson.Gson
import com.humam.mobile.finalprojectkotlin.model.TeamResponse
import com.humam.mobile.finalprojectkotlin.network.ApiRepository
import com.humam.mobile.finalprojectkotlin.network.TheSportDBAPI
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailPresenter(private val view: MatchDetailView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson) {

    fun getTeamDetail(idHomeTeam: String, idAwayTeam: String) {
        view.showLoading()

        doAsync {
            val dataHomeTeam = gson.fromJson(apiRepository
                    .doRequest(TheSportDBAPI.getTeamDetail(idHomeTeam)),
                    TeamResponse::class.java
            )

            val dataAwayTeam = gson.fromJson(apiRepository
                    .doRequest(TheSportDBAPI.getTeamDetail(idAwayTeam)),
                    TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showTeamDetail(dataHomeTeam.teams, dataAwayTeam.teams)
            }
        }
    }
}