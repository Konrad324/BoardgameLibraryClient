package com.konradmikolaj.boardgamelibraryclient

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.konradmikolaj.boardgamelibraryclient.model.BoardGame
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.net.HttpURLConnection
import java.net.URL
import java.io.BufferedReader
import java.io.InputStreamReader


class DatabaseConnector(private val activity: Activity) {

    fun getLoginValue(): String {
        val default = "user_1"
        val sharedPref = activity?.getSharedPreferences("boardgameApp", Context.MODE_PRIVATE)  ?: return default
        return sharedPref.getString("LOGIN", null) ?: default
    }

    fun getPassValue(): String {
        val default = "wrong_pass"
        val sharedPref = activity?.getSharedPreferences("boardgameApp", Context.MODE_PRIVATE)  ?: return default
        return sharedPref.getString("PASS", null) ?: default
    }

    fun handleUser() {
        val login = getLoginValue()
        val pass = getPassValue()

        doAsync {
            val checkConnection = URL("$SERVER_DOMAIN$CHECK_PERMISSION?login=$login&pass=$pass").openConnection() as HttpURLConnection
            if (checkConnection.responseCode == 200) {
                uiThread {
                    activity.longToast("OK, user data correct.")
                }
            } else {
                val createConnection = URL("$SERVER_DOMAIN$CREATE_USER?login=$login&pass=$pass").openConnection() as HttpURLConnection
                createConnection.requestMethod = "POST"
                createConnection.connect()
                if (createConnection.responseCode == 200) {
                    uiThread {
                        activity.longToast("New user created!")
                    }
                } else {
                    uiThread {
                        activity.longToast("Wrong password/user already exist.")
                    }
                }
            }
        }
    }

    fun saveGame(boardGame: BoardGame) {
        val login = getLoginValue()
        val pass = getPassValue()

        doAsync {
            val connection = URL("$SERVER_DOMAIN$SAVE_GAME?login=$login&pass=$pass").openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type","application/json")
            connection.outputStream.write(Gson().toJson(boardGame).toByteArray())
            connection.outputStream.close()
            connection.connect()
            if (connection.responseCode == 200) {
                uiThread {
                    activity.longToast("Game saved.")
                }
            } else {
                uiThread {
                    activity.longToast("Cannot save game, response code ${connection.responseCode}")
                }
            }
        }
    }

    fun updateGame(boardGame: BoardGame) {
        val login = getLoginValue()
        val pass = getPassValue()

        doAsync {
            val connection = URL("$SERVER_DOMAIN$UPDATE_GAME?login=$login&pass=$pass").openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type","application/json")
            connection.outputStream.write(Gson().toJson(boardGame).toByteArray())
            connection.outputStream.close()
            connection.connect()
            if (connection.responseCode == 200) {
                uiThread {
                    activity.longToast("Game updated.")
                }
            } else {
                uiThread {
                    activity.longToast("Cannot update game, response code ${connection.responseCode}")
                }
            }
        }
    }

    fun removeGame(boardGame: BoardGame) {
        val login = getLoginValue()
        val pass = getPassValue()

        doAsync {
            val connection = URL("$SERVER_DOMAIN$REMOVE_GAME?login=$login&pass=$pass").openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type","application/json")
            connection.outputStream.write(Gson().toJson(boardGame).toByteArray())
            connection.outputStream.close()
            connection.connect()
            if (connection.responseCode == 200) {
                uiThread {
                    activity.longToast("Game removed.")
                }
            } else {
                uiThread {
                    activity.longToast("Cannot remove game, response code ${connection.responseCode}")
                }
            }
        }
    }

    fun fetchGames(boardgamesActivity: BoardgamesActivity) {
        val login = getLoginValue()
        val pass = getPassValue()

        doAsync {
            val connection = URL("$SERVER_DOMAIN$GET_USER_GAMES?login=$login&pass=$pass").openConnection() as HttpURLConnection
            connection.setRequestProperty("Content-Type","application/json")
            connection.connect()
            if (connection.responseCode != 200) {
                uiThread {
                    activity.toast("Games loaded.")
                }
            }
            val listType = object : TypeToken<ArrayList<BoardGame>>() { }.type
            val boardgames = Gson().fromJson<ArrayList<BoardGame>>(readBody(connection), listType)

            uiThread {
                boardgamesActivity.refreshBoardgames(boardgames)
            }
        }
    }

    fun readBody(conn: HttpURLConnection): String {
        val br = BufferedReader(InputStreamReader(conn.inputStream))
        val sb = StringBuilder()

        br.lines().forEach { line ->
            sb.append(line)
        }

        return sb.toString()
    }

    companion object {
        val SERVER_DOMAIN = "http://10.0.2.2:8080/"     //10.0.2.2 is android emulator gateway to localhost on PC

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