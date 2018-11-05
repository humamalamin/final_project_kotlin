package com.humam.mobile.finalprojectkotlin.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.humam.mobile.finalprojectkotlin.model.EventsItem
import com.humam.mobile.finalprojectkotlin.model.TeamsItem
import org.jetbrains.anko.db.*

class FootballDbHelper(context: Context) : ManagedSQLiteOpenHelper(context, "Football.db", null, 1) {

    companion object {
        private var instance: FootballDbHelper? = null

        @Synchronized
        fun getInstance(context: Context): FootballDbHelper {
            if (instance == null) {
                instance = FootballDbHelper(context.applicationContext)
            }

            return instance as FootballDbHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(EventsItem.TABLE_EVENTS, true,
                EventsItem.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                EventsItem.ID_EVENT to TEXT,
                EventsItem.DATE to TEXT,
                EventsItem.TIME to TEXT,

                // home team
                EventsItem.HOME_ID to TEXT,
                EventsItem.HOME_TEAM to TEXT,
                EventsItem.HOME_SCORE to TEXT,
                EventsItem.HOME_FORMATION to TEXT,
                EventsItem.HOME_GOAL_DETAILS to TEXT,
                EventsItem.HOME_SHOTS to TEXT,
                EventsItem.HOME_LINEUP_GOALKEEPER to TEXT,
                EventsItem.HOME_LINEUP_DEFENSE to TEXT,
                EventsItem.HOME_LINEUP_MIDFIELD to TEXT,
                EventsItem.HOME_LINEUP_FORWARD to TEXT,
                EventsItem.HOME_LINEUP_SUBSTITUTES to TEXT,

                // away team
                EventsItem.AWAY_ID to TEXT,
                EventsItem.AWAY_TEAM to TEXT,
                EventsItem.AWAY_SCORE to TEXT,
                EventsItem.AWAY_FORMATION to TEXT,
                EventsItem.AWAY_GOAL_DETAILS to TEXT,
                EventsItem.AWAY_SHOTS to TEXT,
                EventsItem.AWAY_LINEUP_GOALKEEPER to TEXT,
                EventsItem.AWAY_LINEUP_DEFENSE to TEXT,
                EventsItem.AWAY_LINEUP_MIDFIELD to TEXT,
                EventsItem.AWAY_LINEUP_FORWARD to TEXT,
                EventsItem.AWAY_LINEUP_SUBSTITUTES to TEXT)

        db.createTable(TeamsItem.TABLE_TEAMS, true,
                TeamsItem.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                TeamsItem.ID_TEAM to TEXT,
                TeamsItem.TEAM_BADGE to TEXT,
                TeamsItem.TEAM to TEXT,
                TeamsItem.FORMED_YEAR to TEXT,
                TeamsItem.STADIUM to TEXT,
                TeamsItem.DESCRIPTION to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(EventsItem.TABLE_EVENTS, true)
        db.dropTable(TeamsItem.TABLE_TEAMS, true)
    }
}

val Context.database: FootballDbHelper
    get() = FootballDbHelper.getInstance(applicationContext)