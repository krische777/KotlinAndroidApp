package com.example.debijenkorf5

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

//       var searchField : SearchView = findViewById(R.id.searchField);
////        var searchField = findViewById(R.id.searchField) as EditText
//        val query = searchField.query.toString()
//
//        Log.d("data from query", query)


//        var editText = findViewById(R.id.textInput) as EditText
//

        val textNew: TextView = findViewById(R.id.textInput) as TextView
        val str: String = textNew.text.toString()

        Log.d("data from inputfield", str)


        var query ="jurk"

        buttonSearch.setOnClickListener {

            if(str.isNotEmpty()) {

                val httpAsync = "http://https://ceres-catalog.debijenkorf.nl/catalog/navigation/search?text=${str}"
                    .httpGet()
                    .responseString { request, response, result ->
                        when (result) {
                            is Result.Failure -> {
                                val ex = result.getException()
                                println(ex)
                            }
                            is Result.Success -> {
                                val data = result.get()
                                println(data)

                                Log.d("data from query", data)

                                val myIntent = Intent(this@MainActivity, ListProductsActivity::class.java)
                                intent.putExtra("productList","$data")
                                this@MainActivity.startActivity(myIntent)
                            }
                        }
                    }

                httpAsync.join()

//                val myIntent = Intent(this@MainActivity, ListProductsActivity::class.java)
//                intent.putExtra("Username","$data")
//                this@MainActivity.startActivity(myIntent)
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
