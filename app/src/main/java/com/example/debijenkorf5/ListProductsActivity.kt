package com.example.debijenkorf5

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_list_products.*


class ListProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_products)
        setSupportActionBar(toolbar)

        val query: String = intent.getStringExtra("query")
        val url =
            "https://ceres-catalog.debijenkorf.nl/catalog/navigation/search?text=$query"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val data = response.getJSONObject("data")
                val products = data.getJSONArray("products")
                Log.i("Fetched successfully", products.toString())

            },
            Response.ErrorListener { error ->
                Log.i("Error while fetching", error.toString())
            }
        )
        LocalRequestQueue.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

}
