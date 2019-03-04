package com.konradmikolaj.boardgamelibraryclient

import android.app.Activity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import org.jetbrains.anko.uiThread
import java.net.URL


class DatabaseConnector(private val activity: Activity) {

    fun getAllUsers() {
        doAsync {
            val result = URL(SERVER_DOMAIN + GET_ALL_USERS).readText()
            uiThread {
                activity.longToast(result)
            }
        }
    }

    companion object {
        val SERVER_DOMAIN = "http://192.168.0.12:8080/"     //cannot simple add 'localhost' cause it is used by android emulation. This is ip of this computer

        val SAVE_GAME = "saveGame"
        val UPDATE_GAME = "updateGame"
        val REMOVE_GAME = "removeGame"
        val GET_USER_GAMES = "userGames"

        val CREATE_USER = "createUser"
        val REMOVE_USER = "removeUser"
        val CHECK_PERMISSION = "checkPermission"
        val GET_ALL_USERS = "getAllUsers"
    }
}