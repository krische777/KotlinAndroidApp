package com.example.debijenkorf5

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_product_list.*


class ListProductsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        setSupportActionBar(toolbar2)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        val query: String? = intent.getStringExtra("query")

        val url =
            "https://ceres-catalog.debijenkorf.nl/catalog/navigation/search?text=$query"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val data = response.getJSONObject("data")
//                val products = data.getJSONArray("products")
                Log.i("Fetched successfully", data.toString())

                progressBar = findViewById(R.id.progressBar)
                recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {

                    layoutManager = GridLayoutManager(context, 2)
                    adapter = ProductsAdapter(data, context)
                    progressBar.visibility = View.GONE


                }

                val searchText = data.getJSONObject("searchText").getString("original")

                val title = findViewById<TextView>(R.id.title)
                title.text = searchText

            },
            Response.ErrorListener { error ->
                Log.i("Error while fetching", error.toString())
            }
        )
        LocalRequestQueue.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

}

