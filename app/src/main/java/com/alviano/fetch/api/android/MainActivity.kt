package com.alviano.fetch.api.android

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alviano.fetch.api.android.networking.NetworkChecker
import com.alviano.fetch.api.android.networking.PostApi
import com.alviano.fetch.api.android.networking.RemoteApi
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val networkChecker by lazy {
        NetworkChecker(getSystemService(ConnectivityManager::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        RemoteApi().getFact()

        PostApi().postApi()
    }
}