package com.example.debijenkorf5

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


//       var searchField : SearchView = findViewById(R.id.searchField);
////        var searchField = findViewById(R.id.searchField) as EditText
//        val query = searchField.query.toString()
//        Log.d("data from query", query)

//        var editText = findViewById(R.id.textInput) as EditText
//        val textNew= findViewById(R.id.textInput) as EditText
        //        Log.d("data from inputfield",)


        val button = findViewById<Button>(R.id.buttonSearch)
        button.setOnClickListener {
            val textNew = findViewById<EditText>(R.id.textInput)
//        val textNew = findViewById<View>(R.id.textInput) as EditText
            val str = textNew.text.toString()
            Log.i("str", "Search bar text $str")
            if (str.isNotEmpty()) {


                val url =
                    "https://ceres-catalog.debijenkorf.nl/catalog/navigation/search?text=$str"

                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    Response.Listener { response ->
                        var data = response.getJSONObject("data")
                        var products = data.getJSONArray("products").toString()
                        Log.i("Fetched successfully", products)
                        val intent = Intent(this, ListProductsActivity::class.java)
//                        startActivity(intent)
                        intent.putExtra(
                            "message",
                            products
                        )
                        startActivityForResult(intent, 1)
                    },
                    Response.ErrorListener { error ->
                        Log.i("Error while fetching", error.toString())
                    }
                )
                LocalRequestQueue.getInstance(this).addToRequestQueue(jsonObjectRequest)
            }

        }


//        buttonSearch.setOnClickListener {
//
//            if(str.isNotEmpty()) {
//                val myIntent = Intent(this@MainActivity, ListProductsActivity::class.java)
//                                intent.putExtra("productList","$str")
//                                this@MainActivity.startActivity(myIntent)
////
////                val httpAsync = "http://https://ceres-catalog.debijenkorf.nl/catalog/navigation/search?text=${str}"
////                    .httpGet()
////                    .responseString { request, response, result ->
////                        when (result) {
////                            is Result.Failure -> {
////                                val ex = result.getException()
////                                println(ex)
////                            }
////                            is Result.Success -> {
////                                val data = result.get()
////                                println(data)
////
////                                Log.d("data from query", data)
////
////                                val myIntent = Intent(this@MainActivity, ListProductsActivity::class.java)
////                                intent.putExtra("productList","$data")
////                                this@MainActivity.startActivity(myIntent)
////                            }
////                        }
////                    }
////
////                httpAsync.join()
////
//////                val myIntent = Intent(this@MainActivity, ListProductsActivity::class.java)
//////                intent.putExtra("Username","$data")
//////                this@MainActivity.startActivity(myIntent)
//            }
//        }

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
