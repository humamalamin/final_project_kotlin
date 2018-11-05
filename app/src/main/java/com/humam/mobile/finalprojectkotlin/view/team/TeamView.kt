package com.humam.mobile.finalprojectkotlin.view.team

import com.humam.mobile.finalprojectkotlin.model.LeagueResponse
import com.humam.mobile.finalprojectkotlin.model.TeamsItem

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
    fun showLeagueList(data: LeagueResponse)
    fun showTeamList(data: MutableList<TeamsItem>)
}