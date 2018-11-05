package com.humam.mobile.finalprojectkotlin.view.favorite

import com.humam.mobile.finalprojectkotlin.model.EventsItem
import com.humam.mobile.finalprojectkotlin.model.TeamsItem

interface FavoriteTabsView {

    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
    fun showEventList(data: MutableList<EventsItem>)
    fun showTeamList(data: MutableList<TeamsItem>)
}