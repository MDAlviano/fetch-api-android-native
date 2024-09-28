package com.alviano.fetch.api.android.networking

import android.util.Log
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL

class PostApi {

    val url: String = "http://10.0.2.2:5000"

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
                }
            }

            val responseCode = connection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = StringBuilder()

                BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
                    reader.forEachLine { line ->
                        response.append(line)
                    }
                }
                Log.d(TAG, "Response: $response")
            } else {
                Log.d(TAG, "Error: HTTP Response code - $responseCode")
            }
            connection.disconnect()
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            e.printStackTrace()
        }
    }

}