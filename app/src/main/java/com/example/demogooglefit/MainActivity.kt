package com.example.demogooglefit

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.demogooglefit.databinding.ActivityMainBinding
import com.google.android.gms.common.SignInButton
import okhttp3.HttpUrl


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val CLIENT_ID ="251057727253-9g15qvhuqk9hjjod01v1oin591fr3ka6.apps.googleusercontent.com"
    private val API_SCOPE ="https://www.googleapis.com/auth/fitness.activity.read" +
            "https://www.googleapis.com/auth/fitness.blood_glucose.read" +
            "https://www.googleapis.com/auth/fitness.blood_pressure.read" +
            "https://www.googleapis.com/auth/fitness.body.read" +
            "https://www.googleapis.com/auth/fitness.body_temperature.read" +
            "https://www.googleapis.com/auth/fitness.heart_rate.read" +
            "https://www.googleapis.com/auth/fitness.location.read" +
            "https://www.googleapis.com/auth/fitness.nutrition.read" +
            "https://www.googleapis.com/auth/fitness.oxygen_saturation.read" +
            "https://www.googleapis.com/auth/fitness.reproductive_health.read" +
            "https://www.googleapis.com/auth/fitness.sleep.read"

    private val REDIRECT_URI ="com.example.demogooglefit:/oauth2redirect"
    private val CODE ="code"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signInButton.setSize(SignInButton.SIZE_STANDARD)


        val webUrl = HttpUrl.parse("https://accounts.google.com/o/oauth2/v2/auth")//
            .newBuilder() //
            .addQueryParameter("client_id", CLIENT_ID)
            .addQueryParameter("scope", API_SCOPE)
            .addQueryParameter(
                "redirect_uri",REDIRECT_URI)
            .addQueryParameter("response_type", CODE)
            .build()

        binding.signInButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(webUrl.toString())
            startActivity(intent)
        }
    }
}