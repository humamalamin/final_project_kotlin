package com.humam.mobile.finalprojectkotlin.view.matches

import com.humam.mobile.finalprojectkotlin.model.EventsItem
import com.humam.mobile.finalprojectkotlin.model.LeagueResponse

interface MatchEventsView {
    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
    fun showLeagueList(data: LeagueResponse)
    fun showEventList(data: MutableList<EventsItem>)
}