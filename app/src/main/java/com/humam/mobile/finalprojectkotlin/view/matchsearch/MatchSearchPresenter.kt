package com.humam.mobile.finalprojectkotlin.view.matchsearch

import com.google.gson.Gson
import com.humam.mobile.finalprojectkotlin.model.EventSearchResponse
import com.humam.mobile.finalprojectkotlin.network.ApiRepository
import com.humam.mobile.finalprojectkotlin.network.TheSportDBAPI
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchSearchPresenter(private val view: MatchSearchView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson) {

    fun getEventsSearch(eventName: String = "") {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBAPI.getEventsSearch(eventName)),
                    EventSearchResponse::class.java
            )

            uiThread {
                view.hideLoading()

                try {
                    view.showEventList(data.events)
                } catch (e: Exception) {
                    view.showEmptyData()
                }
            }
        }
    }
}