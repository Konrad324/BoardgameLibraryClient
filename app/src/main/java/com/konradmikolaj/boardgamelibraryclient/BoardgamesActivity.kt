package com.konradmikolaj.boardgamelibraryclient

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.widget.LinearLayoutManager
import android.widget.Button
import com.konradmikolaj.boardgamelibraryclient.model.BoardGame
import kotlinx.android.synthetic.main.boardgames.*


class BoardgamesActivity : Activity() {

    private val boardgames: ArrayList<BoardGame> = ArrayList()

    private val databaseConnector: DatabaseConnector = DatabaseConnector(this)

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                startActivity(MainActivity.newIntent(this))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_boardgames -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_account -> {
                val intent = AccountActivity.newIntent(this)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.boardgames)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_boardgames

        databaseConnector.fetchGames(this)

        findViewById<Button>(R.id.buttonAddNewGame).setOnClickListener { view ->
            startActivity(GameDetailActivity.newIntent(this, BoardGame.empty()))
        }
    }

    fun refreshBoardgames(boardgames: ArrayList<BoardGame>) {
        boardgamesList.layoutManager = LinearLayoutManager(this)
        boardgamesList.adapter = BoardgameAdapter(boardgames, this)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, BoardgamesActivity::class.java)
        }
    }

    fun addSampleBoardgames(){
        boardgames.add(BoardGame.of("Osadnicy z Catanu", "Osadnicy z Catanu (Settlers of Catan) to bardzo popularna na całym świecie gra rodzinno-ekonomiczna o bardzo dużej \"miodności\" grania. Teraz prezentujemy jej polską edycję!","Półka"))
        boardgames.add(BoardGame.of("Przykladowa Gra", "opis1","Półka"))
        boardgames.add(BoardGame.of("Magia i Miecz", "opis1","Półka"))
        boardgames.add(BoardGame.of("Splendor", "opis1","Półka"))
        boardgames.add(BoardGame.of("Talisman", "opis1","Półka"))
        boardgames.add(BoardGame.of("Domek", "opis1","Półka"))
        boardgames.add(BoardGame.of("Napad bandziorów na krzak pomidorów", "opis1","Półka"))
    }
}
