package com.alviano.fetch.api.android

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alviano.fetch.api.android.databinding.ActivitySignInBinding
import org.json.JSONObject
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val username = binding.usernameTxt.text.toString()
            val password = binding.passwordTxt.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()){
                LoginTask(username, password).execute()
            } else {
                Toast.makeText(this, "Mohon isi field yang tersedia", Toast.LENGTH_SHORT).show()
            }
        }

        binding.toSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.backBtn.setOnClickListener {
            finish()
        }

    }

    private inner class LoginTask(private val username: String, private val password: String): AsyncTask<Void, Void, String?>(){

        override fun doInBackground(vararg p0: Void?): String? {
            try {
                val url = URL("https://dummyjson.com/auth/login")
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "POST"
                urlConnection.setRequestProperty("Content-Type", "application/json")
                urlConnection.doOutput = true

                // create json body
                val json = JSONObject().apply {
                    put("username", username)
                    put("password", password)
                    put("expiresInMins", 60)
                }

                // send json body
                val outputStream = BufferedOutputStream(urlConnection.outputStream)
                outputStream.write(json.toString().toByteArray())
                outputStream.flush()

                // get response
                val responseCode = urlConnection.responseCode
                return if (responseCode == HttpURLConnection.HTTP_OK){
                    val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val response = StringBuilder()
                    reader.forEachLine { response.append(it) }
                    response.toString()
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }

        override fun onPostExecute(result: String?) {
            if (result != null) {
                val jsonResponse = JSONObject(result)
                val accessToken = jsonResponse.optString("accessToken", "")
                if (accessToken.isNotEmpty()){
                    Toast.makeText(this@SignInActivity, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@SignInActivity, "Login Gagal!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@SignInActivity, "Login Gagal!", Toast.LENGTH_SHORT).show()
            }
        }

    }

}