package com.konradmikolaj.boardgamelibraryclient

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.konradmikolaj.boardgamelibraryclient.model.BoardGame

class GameDetailActivity : Activity() {

    private val databaseConnector: DatabaseConnector = DatabaseConnector(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.boardgame_edit)

        val boardgame = intent.getSerializableExtra(INTENT_BOARDGAME) as BoardGame
        setBoardgameDataOnView(boardgame)

        setCheckBoxListener()
        setRemoveButtonBehavior()

        if (boardgame.title.isEmpty()) {
            setSaveButtonBehavior()
        } else {
            setUpdateButtonBehavior()
        }
    }

    fun setBoardgameDataOnView(boardGame: BoardGame){
        findViewById<EditText>(R.id.editGameDetailTitle).setText(boardGame.title)
        findViewById<EditText>(R.id.editGameDetailDescription).setText(boardGame.description)
        findViewById<EditText>(R.id.editGameDetailLocalization).setText(boardGame.localization)
    }

    fun setCheckBoxListener() {
        findViewById<CheckBox>(R.id.checkBoxDeleteGame).setOnCheckedChangeListener { buttonView, isChecked ->
            findViewById<Button>(R.id.buttonDeleteGame).isEnabled = isChecked
        }
    }

    fun setSaveButtonBehavior() {
        findViewById<Button>(R.id.buttonSaveGame).setOnClickListener { view ->
            saveButtonBehaviour()
        }
    }

    fun setUpdateButtonBehavior() {
        findViewById<Button>(R.id.buttonSaveGame).setOnClickListener { view ->
            updateButtonBehaviour()
        }
    }

    fun setRemoveButtonBehavior() {
        findViewById<Button>(R.id.buttonDeleteGame).setOnClickListener { view ->
            removeButtonBehaviour()
        }
    }

    fun saveButtonBehaviour() {
        val title = findViewById<EditText>(R.id.editGameDetailTitle).text.toString()
        val description = findViewById<EditText>(R.id.editGameDetailDescription).text.toString()
        val localization = findViewById<EditText>(R.id.editGameDetailLocalization).text.toString()
        val boardGame = BoardGame.of(title, description, localization)

        databaseConnector.saveGame(boardGame)

        startActivity(BoardgamesActivity.newIntent(this))
    }

    fun updateButtonBehaviour() {
        val title = findViewById<EditText>(R.id.editGameDetailTitle).text.toString()
        val description = findViewById<EditText>(R.id.editGameDetailDescription).text.toString()
        val localization = findViewById<EditText>(R.id.editGameDetailLocalization).text.toString()
        val boardGame = BoardGame.of(title, description, localization)

        databaseConnector.updateGame(boardGame)

        startActivity(BoardgamesActivity.newIntent(this))
    }

    fun removeButtonBehaviour() {
        val title = findViewById<EditText>(R.id.editGameDetailTitle).text.toString()
        val description = findViewById<EditText>(R.id.editGameDetailDescription).text.toString()
        val localization = findViewById<EditText>(R.id.editGameDetailLocalization).text.toString()
        val boardGame = BoardGame.of(title, description, localization)

        databaseConnector.removeGame(boardGame)

        startActivity(BoardgamesActivity.newIntent(this))
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
