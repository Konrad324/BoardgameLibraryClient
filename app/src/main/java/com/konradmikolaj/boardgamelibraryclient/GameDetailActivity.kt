package com.konradmikolaj.boardgamelibraryclient

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import com.konradmikolaj.boardgamelibraryclient.model.BoardGame

class GameDetailActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.boardgame_edit)

        val boardgame = intent.getSerializableExtra(INTENT_BOARDGAME) as BoardGame
        setBoardgameDataOnView(boardgame)
    }

    fun setBoardgameDataOnView(boardGame: BoardGame){
        findViewById<EditText>(R.id.editGameDetailTitle).setText(boardGame.title)
        findViewById<EditText>(R.id.editGameDetailDescription).setText(boardGame.description)
        findViewById<EditText>(R.id.editGameDetailLocalization).setText(boardGame.localization)
    }

    companion object {

        private val INTENT_BOARDGAME = "boardgame_intent"

        fun newIntent(context: Context, value: BoardGame): Intent {
            val intent = Intent(context, GameDetailActivity::class.java)
            intent.putExtra(INTENT_BOARDGAME, value)
            return intent
        }
    }
}
