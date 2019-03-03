package com.konradmikolaj.boardgamelibraryclient

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import kotlinx.android.synthetic.main.account.*

class AccountActivity : Activity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                startActivity(MainActivity.newIntent(this))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_boardgames -> {
                startActivity(BoardgamesActivity.newIntent(this))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_account -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_account

        val userId = intent.getStringExtra(INTENT_USER_ID)
                ?: throw IllegalStateException("field $INTENT_USER_ID missing in Intent")
    }

    companion object {

        private val INTENT_USER_ID = "user_id"

        fun newIntent(context: Context, value: String): Intent {
            val intent = Intent(context, AccountActivity::class.java)
            intent.putExtra(INTENT_USER_ID, value)
            return intent
        }
    }
}
