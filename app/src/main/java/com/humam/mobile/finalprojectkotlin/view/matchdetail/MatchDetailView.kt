package com.humam.mobile.finalprojectkotlin.view.matchdetail

import com.humam.mobile.finalprojectkotlin.model.TeamsItem

interface MatchDetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(dataHomeTeam: MutableList<TeamsItem>, dataAwayTeam: MutableList<TeamsItem>)
}