package com.konradmikolaj.boardgamelibraryclient

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.account.*

class AccountActivity : Activity() {

    private val databaseConnector: DatabaseConnector = DatabaseConnector(this)

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

        findViewById<EditText>(R.id.editPassword).setText(databaseConnector.getPassValue())
        findViewById<EditText>(R.id.editLogin).setText(databaseConnector.getLoginValue())

        findViewById<Button>(R.id.buttonCheckCreateAccount).setOnClickListener { view ->
            handleButtonClick()
        }
    }

    fun handleButtonClick() {
        val login = findViewById<EditText>(R.id.editLogin).text.toString()
        val pass = findViewById<EditText>(R.id.editPassword).text.toString()

        val sharedPref = this?.getSharedPreferences("boardgameApp", Context.MODE_PRIVATE) ?: return
        val edit = sharedPref.edit()
            edit.putString("LOGIN", login)
            edit.putString("PASS", pass)
            edit.apply()

        databaseConnector.handleUser()
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, AccountActivity::class.java)
        }
    }
}
