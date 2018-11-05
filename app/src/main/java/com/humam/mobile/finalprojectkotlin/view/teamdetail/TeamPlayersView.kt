package com.humam.mobile.finalprojectkotlin.view.teamdetail

import com.humam.mobile.finalprojectkotlin.model.PlayerItem

interface TeamPlayersView {
    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
    fun showPlayerList(data: MutableList<PlayerItem>)
}