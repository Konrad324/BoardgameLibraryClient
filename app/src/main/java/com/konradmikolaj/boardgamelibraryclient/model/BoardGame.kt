package com.konradmikolaj.boardgamelibraryclient.model

import java.io.Serializable


class BoardGame(val title: String = "",
                val description: String = "",
                val localization: String = "") : Serializable {

    companion object {
        fun of(title: String, description: String, localization: String): BoardGame {
            return BoardGame(title, description, localization)
        }

        fun nonExisiting(): BoardGame{
            return BoardGame("Non existing", "This boardgame not exist. Probably something goes wrong!","Hell")
        }

        fun empty(): BoardGame {
            return BoardGame()
        }
    }
}

