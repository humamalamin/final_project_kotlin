package com.humam.mobile.finalprojectkotlin.model

import com.google.gson.annotations.SerializedName

data class EventSearchResponse (
        @field:SerializedName("event")
        val events: MutableList<EventsItem>
)