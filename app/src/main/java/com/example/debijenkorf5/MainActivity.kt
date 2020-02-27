package com.example.debijenkorf5

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        val button = findViewById<Button>(R.id.buttonSearch)

        button.setOnClickListener {
            val textNew = findViewById<SearchView>(R.id.textInput)
            val query = textNew.query.toString()
            Log.i("str", "Search bar text $query")

            if (query.isNotEmpty()) {
                button.isEnabled = true
                button.isClickable = true
                val intent = Intent(this, ListProductsActivity::class.java)
                intent.putExtra(
                    "query",
                    query
                )
                startActivityForResult(intent, 1)
            } else {
                button.isEnabled = false
                button.isClickable = false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
