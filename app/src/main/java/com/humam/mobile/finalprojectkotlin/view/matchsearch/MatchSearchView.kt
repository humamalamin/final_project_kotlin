package com.humam.mobile.finalprojectkotlin.view.matchsearch

import com.humam.mobile.finalprojectkotlin.model.EventsItem

interface MatchSearchView {

    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
    fun showEventList(data: MutableList<EventsItem>)
}