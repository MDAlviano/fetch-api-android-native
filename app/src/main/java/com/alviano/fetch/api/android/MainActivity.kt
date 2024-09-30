package com.alviano.fetch.api.android

import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.alviano.fetch.api.android.networking.NetworkChecker
import com.alviano.fetch.api.android.networking.PostApi
import com.alviano.fetch.api.android.networking.RemoteApi
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var resultView: TextView
    private lateinit var buttonFetch: Button

    private val networkChecker by lazy {
        NetworkChecker(getSystemService(ConnectivityManager::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        RemoteApi().getFact()

        CoroutineScope(Dispatchers.IO).launch {
            PostApi().postApi()
        }

        resultView = findViewById(R.id.results)
        buttonFetch = findViewById(R.id.buttonFetch)

        // parsing data
        buttonFetch.setOnClickListener {
            parsingData()
        }

    }

    private fun parsingData() {
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url: String = "https://reqres.in/api/users"


    }
}