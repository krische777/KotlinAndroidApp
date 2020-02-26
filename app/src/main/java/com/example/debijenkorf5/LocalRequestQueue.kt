package com.example.debijenkorf5

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class LocalRequestQueue constructor(context: Context) {
    companion object {
//        val GSON = Gson()
        @Volatile
        private var INSTANCE: LocalRequestQueue? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocalRequestQueue(context).also {
                    INSTANCE = it
                }
            }
    }

   private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
}