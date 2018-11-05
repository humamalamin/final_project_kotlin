package com.humam.mobile.finalprojectkotlin.view.team

import com.google.gson.Gson
import com.humam.mobile.finalprojectkotlin.model.LeagueResponse
import com.humam.mobile.finalprojectkotlin.model.TeamResponse
import com.humam.mobile.finalprojectkotlin.network.ApiRepository
import com.humam.mobile.finalprojectkotlin.network.TheSportDBAPI
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TeamPresenter(private val view: TeamView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson) {

    fun getLeagueAll() {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBAPI.getLeagueAll()),
                    LeagueResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showLeagueList(data)
            }
        }
    }

    fun getTeamAll(leagueName: String = "") {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBAPI.getTeamAll(leagueName)),
                    TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()

                try {
                    view.showTeamList(data.teams)
                } catch (e: Exception) {
                    view.showEmptyData()
                }
            }
        }
    }

    fun getTeamSearch(teamName: String = "") {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBAPI.getTeamSearch(teamName)),
                    TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()

                try {
                    view.showTeamList(data.teams)
                } catch (e: Exception) {
                    view.showEmptyData()
                }
            }
        }
    }
}