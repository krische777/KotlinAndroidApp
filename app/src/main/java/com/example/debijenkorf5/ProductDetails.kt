package com.example.debijenkorf5

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_details.*

class ProductDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        setSupportActionBar(toolbar3)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        val productCode: String = intent.getStringExtra("productCode")
        val currentVariantCode: String = intent.getStringExtra("currentVariantCode")


        val url =
            "https://ceres-catalog.debijenkorf.nl/catalog/product/show?productCode=$productCode&productVariantCode=$currentVariantCode"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val detailsData = response.getJSONObject("data")
                Log.i("detailsData OK", detailsData.toString())


                val detailsImage = findViewById<ImageView>(R.id.detailsImage)
                val singleProduct = detailsData.getJSONObject("product")
                val firstImageUrl =
                    singleProduct.getJSONObject("currentVariantProduct").getJSONArray("images")
                        .getJSONObject(0).getString("url")

                val detailsName = findViewById<TextView>(R.id.detailsName)
                val detailsNameQuery = singleProduct.getString("name")
                detailsName.text = detailsNameQuery

                val detailsDescription = findViewById<TextView>(R.id.detailsDescription)
                val detailsDescriptionQuery = singleProduct?.getString("description") ?: ""

                detailsDescription.text = detailsDescriptionQuery

                val detailsBrandName = findViewById<TextView>(R.id.detailsBrandName)
                val detailsBrandNameQuery = singleProduct.getJSONObject("brand").getString("name")
                detailsBrandName.text = detailsBrandNameQuery

                val deliveryTime = findViewById<TextView>(R.id.deliveryTime)
                val deliveryTimeQuery =
                    singleProduct.getJSONObject("currentVariantProduct").getString("deliveryTime")
                deliveryTime.text = "Delivery time: $deliveryTimeQuery"

                Log.i("IMAGE", firstImageUrl.toString())

                Picasso.get().load("https:$firstImageUrl").into(detailsImage)


            },
            Response.ErrorListener { error ->
                Log.i("Error while fetching", error.toString())
            }
        )
        LocalRequestQueue.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

}
