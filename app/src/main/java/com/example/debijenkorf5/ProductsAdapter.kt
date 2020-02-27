package com.example.debijenkorf5

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.json.JSONObject

class ProductsAdapter(private val data: JSONObject, private val context: Context) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    val products = data.getJSONArray("products")

    override fun onBindViewHolder(holder: ProductsAdapter.ViewHolder, position: Int) {
        val product = products.getJSONObject(position)
        val variantProduct = product.getJSONObject("currentVariantProduct")
        val firstImageUrl = "https:" + getFirstImageUrl(variantProduct)
        val price = variantProduct.getJSONObject("sellingPrice").getString("value")
        val size = variantProduct.getString("size")
        val color = variantProduct.getString("color")
        val brand = product.getJSONObject("brand")
        val brandName = brand.getString("name")

        Picasso.get().load(firstImageUrl).into(holder.image)

        Log.i("imageURL", " $firstImageUrl")

        holder.name.text = product.getString("name")
        holder.price.text = price
        holder.size.text = size
        holder.color.text = color
        holder.brandName.text = brandName

    }

    private fun getFirstImageUrl(variantProduct: JSONObject): String {
        val imagesArray = variantProduct.getJSONArray("images")
        var firstImageUrl = imagesArray.getJSONObject(0).getString("url")
        return firstImageUrl
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_row, parent, false)
        val holder = ViewHolder(view)
        view.setOnClickListener {
            val intent = Intent(parent.context, ProductDetails::class.java)
            intent.putExtra(
                "productCode",
                products.getJSONObject(holder.adapterPosition).getString("code")
            )
            intent.putExtra(
                "currentVariantCode",
                products.getJSONObject(holder.adapterPosition).getJSONObject("currentVariantProduct").getString(
                    "code"
                )
            )


            parent.context.startActivity(intent)
        }
        return holder
    }

    override fun getItemCount() = products.length()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val name: TextView = itemView.findViewById(R.id.name)
        val price: TextView = itemView.findViewById(R.id.price)
        val size: TextView = itemView.findViewById(R.id.size)
        val color: TextView = itemView.findViewById(R.id.color)
        val brandName: TextView = itemView.findViewById(R.id.brandname)
    }
}