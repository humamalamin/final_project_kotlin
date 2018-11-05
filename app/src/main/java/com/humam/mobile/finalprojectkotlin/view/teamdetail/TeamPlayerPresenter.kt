package com.humam.mobile.finalprojectkotlin.view.teamdetail

import com.google.gson.Gson
import com.humam.mobile.finalprojectkotlin.model.PlayerResponse
import com.humam.mobile.finalprojectkotlin.network.ApiRepository
import com.humam.mobile.finalprojectkotlin.network.TheSportDBAPI
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TeamPlayerPresenter(private val view: TeamPlayersView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson) {

    fun getAllPlayer(teamName: String = ""){
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBAPI.getPlayerAll(teamName)),
                    PlayerResponse::class.java
            )

            uiThread {
                view.hideLoading()

                try{
                    view.showPlayerList(data.player)
                }catch (e: Exception){
                    view.showEmptyData()
                }
            }
        }
    }
}