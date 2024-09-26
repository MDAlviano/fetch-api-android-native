package com.alviano.fetch.api.android.networking

import android.util.Log
import com.alviano.fetch.api.android.AppConstants
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class RemoteApi {

    val BASE_URL = "https://api.api-ninjas.com/v1/facts"

    val TAG = "RemoteApi"

    fun getFact(){
        Thread(Runnable {

            val connection = URL(BASE_URL).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.setRequestProperty("X-Api-Key", AppConstants.API_KEY)
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            connection.doInput = true

            try {
                val reader = InputStreamReader(connection.inputStream)

                reader.use { input ->
                    val response = StringBuilder()
                    val bufferedReader = BufferedReader(input)

                    bufferedReader.forEachLine {
                        response.append(it.trim())
                    }

                    Log.d(TAG, "In_Success ${response.toString()}")
                }
            } catch (e: Exception) {
                Log.d(TAG, "In_Error ${e.localizedMessage}")
            }

            connection.disconnect()

        }).start()
    }

}