package com.alviano.fetch.api.android.networking

import android.util.Log
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL

class PostApi {

    val url: String = "https://reqres.in/api/users"

    val data_to_send: String = "{\"data\": \"Sample Data\"}"

    val TAG = "PostApi"

    fun postApi() {

        try {
            val uri = URI(url)
            val obj = uri.toURL()

            val connection = obj.openConnection() as HttpURLConnection

            connection.requestMethod = "POST"
            connection.doOutput = true

            connection.setRequestProperty("Content-Type", "application/json")

            connection.outputStream.use { os ->
                DataOutputStream(os).use { dos ->
                    dos.writeBytes(data_to_send)
                    dos.flush()
                    Log.d(TAG, "Data sent to server")
                }
            }

            val responseCode = connection.responseCode
            Log.d(TAG, "Response code: $responseCode")


            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                val response = StringBuilder()

                BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
                    reader.forEachLine { line ->
                        response.append(line)
                    }
                }
                Log.d(TAG, "Response: $response")
                Log.d(TAG, "URL: $data_to_send")
            } else {
                Log.d(TAG, "Error: HTTP Response code - $responseCode")
            }
            connection.disconnect()
        } catch (e: Exception) {
            Log.d(TAG, "Exception caught: ${e.message ?: "No message available"}")
            Log.d(TAG, "Exception class: ${e::class.java.name}")
            Log.d(TAG, "Exception details:", e)
            e.printStackTrace()
        }
    }

}