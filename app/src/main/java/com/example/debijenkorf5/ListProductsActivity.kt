package com.example.debijenkorf5

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest


class ListProductsActivity : Activity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_list_products)
        //setSupportActionBar(toolbar)

        val query: String = intent.getStringExtra("query")
        val url =
            "https://ceres-catalog.debijenkorf.nl/catalog/navigation/search?text=$query"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val data = response.getJSONObject("data")
                val products = data.getJSONArray("products")
                Log.i("Fetched successfully", products.toString())

                progressBar = findViewById(R.id.progressBar)
                recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
                    // use this setting to improve performance if you know that changes
                    // in content do not change the layout size of the RecyclerView
                    setHasFixedSize(true)
                    layoutManager = GridLayoutManager(context, 2)
                    adapter = ProductsAdapter(products)
                    progressBar.visibility = View.GONE


                }

            },
            Response.ErrorListener { error ->
                Log.i("Error while fetching", error.toString())
            }
        )
        LocalRequestQueue.getInstance(this).addToRequestQueue(jsonObjectRequest)



    }
}

