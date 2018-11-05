package com.humam.mobile.finalprojectkotlin.view.favorite

import android.content.Context
import com.humam.mobile.finalprojectkotlin.helper.database
import com.humam.mobile.finalprojectkotlin.model.EventsItem
import com.humam.mobile.finalprojectkotlin.model.TeamsItem
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteTabsPresenter(private val context: Context?,
                            private val view: FavoriteTabsView) {

    fun getFavoriteEvent(){
        view.showLoading()

        val data: MutableList<EventsItem> = mutableListOf()

        context?.database?.use {
            val favorites = select(EventsItem.TABLE_EVENTS)
                    .parseList(classParser<EventsItem>())

            data.addAll(favorites)
        }

        view.hideLoading()

        if (data.size > 0) {
            view.showEventList(data)
        } else {
            view.showEmptyData()
        }
    }

    fun getFavoriteTeam(){
        view.showLoading()

        val data: MutableList<TeamsItem> = mutableListOf()

        context?.database?.use {
            val favorites = select(TeamsItem.TABLE_TEAMS)
                    .parseList(classParser<TeamsItem>())

            data.addAll(favorites)
        }

        view.hideLoading()

        if (data.size > 0) {
            view.showTeamList(data)
        } else {
            view.showEmptyData()
        }
    }
}