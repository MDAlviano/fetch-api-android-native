package com.alviano.fetch.api.android

import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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
import org.json.JSONArray
import org.json.JSONObject

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
            fetchData()
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }

    }

    private fun fetchData() {
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url: String = "https://reqres.in/api/users"

        val stringRequest: StringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                parseJson(response)
            },
            { error ->
                resultView.text = "That didn't work"
            })
        queue.add(stringRequest)
    }

    private fun parseJson(response: String) {
        try {
            val jsonObject: JSONObject = JSONObject(response)
            val jsonArray: JSONArray = jsonObject.getJSONArray("data")
            for (i in 0 until jsonArray.length()){
                val jsonObject2: JSONObject = jsonArray.getJSONObject(i)
                val idVar = jsonObject2.getString("id")
                val emailVar = jsonObject2.getString("email")
                val firstNameVar = jsonObject2.getString("first_name")
                val lastNameVar = jsonObject2.getString("last_name")

                resultView.append("Id: $idVar\nEmail: $emailVar\nFirstName: $firstNameVar\nLastName: $lastNameVar\n\n")

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}