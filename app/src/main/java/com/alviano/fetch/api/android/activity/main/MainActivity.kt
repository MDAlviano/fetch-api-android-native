package com.alviano.fetch.api.android.activity.main

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alviano.fetch.api.android.R
import com.alviano.fetch.api.android.activity.gridview.GridViewActivity
import com.alviano.fetch.api.android.activity.signin.SignInActivity
import com.alviano.fetch.api.android.data.Person
import com.alviano.fetch.api.android.networking.NetworkChecker
import com.alviano.fetch.api.android.networking.PostApi
import com.alviano.fetch.api.android.networking.RemoteApi
import com.android.volley.Request
import com.android.volley.RequestQueue
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
    private lateinit var recyclerView: RecyclerView
    private lateinit var toLoginBtn: Button
    private lateinit var toGrid: Button

    private val networkChecker by lazy {
        NetworkChecker(getSystemService(ConnectivityManager::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        RemoteApi().getFact()

        CoroutineScope(Dispatchers.IO).launch {
            PostApi().postApi()
        }

        resultView = findViewById(R.id.resultsView)
        buttonFetch = findViewById(R.id.buttonFetch)
        recyclerView = findViewById(R.id.recyclerView)
        toLoginBtn = findViewById(R.id.toLogin)
        toGrid = findViewById(R.id.toGrid)

        // parsing data
        buttonFetch.setOnClickListener {
            fetchData()
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }

        toLoginBtn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        toGrid.setOnClickListener {
            startActivity(Intent(this, GridViewActivity::class.java))
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

            val data = ArrayList<Person>()

            for (i in 0 until jsonArray.length()) {
                val jsonObject2: JSONObject = jsonArray.getJSONObject(i)
                val idVar = jsonObject2.getString("id")
                val emailVar = jsonObject2.getString("email")
                val firstNameVar = jsonObject2.getString("first_name")
                val lastNameVar = jsonObject2.getString("last_name")

                data.add(Person(idVar, emailVar, firstNameVar, lastNameVar))
            }

            recyclerView = findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this)
            val adapter = MyAdapters(data)
            recyclerView.adapter = adapter

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}