package com.example.debijenkorf5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_list_products.*

class ListProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_products)
        setSupportActionBar(toolbar)

//        val listOfProducts=intent.getStringExtra("productList")

    }

}
