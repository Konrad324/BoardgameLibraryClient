package com.konradmikolaj.boardgamelibraryclient

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import kotlinx.android.synthetic.main.boardgames.*


class BoardgamesActivity : Activity() {

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
                val intent = AccountActivity.newIntent(this, "dupa")
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
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, BoardgamesActivity::class.java)
        }
    }
}
