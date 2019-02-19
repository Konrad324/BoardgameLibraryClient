package com.konradmikolaj.boardgamelibraryclient

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle


//1
class ConfigurationActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configuration)
        val userId = intent.getStringExtra(INTENT_USER_ID)
                ?: throw IllegalStateException("field $INTENT_USER_ID missing in Intent")

    }

    companion object {

        private val INTENT_USER_ID = "user_id"

        fun newIntent(context: Context, value: String): Intent {
            val intent = Intent(context, ConfigurationActivity::class.java)
            intent.putExtra(INTENT_USER_ID, value)
            return intent
        }
    }
}
