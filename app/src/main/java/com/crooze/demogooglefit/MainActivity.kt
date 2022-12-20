package com.crooze.demogooglefit

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.example.demogooglefit.OauthServer
import com.example.demogooglefit.OauthToken
import com.example.demogooglefit.RetrofitBuild
import com.example.demogooglefit.WebView
import com.example.demogooglefit.databinding.ActivityMainBinding
import com.google.android.gms.common.SignInButton
import okhttp3.HttpUrl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var sharedPreferences: SharedPreferences

    private val CLIENT_ID =
        "251057727253-9g15qvhuqk9hjjod01v1oin591fr3ka6.apps.googleusercontent.com"
    private val API_SCOPE = "https://www.googleapis.com/auth/fitness.activity.read"
    private val REDIRECT_URI = "com.crooze.demogooglefit:/oauth2redirect"
    private val REDIRECT_URI_ROOT = "com.crooze.demogooglefit"
    private val CODE = "code"
    private val AUTHORIZATION_CODE = "authorization_code"
    private val ERROR_CODE: String = "error"
    lateinit var code: String
    private var error: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signInButton.setSize(SignInButton.SIZE_STANDARD)

        val data = intent.data
        if (data != null && !TextUtils.isEmpty(data.scheme)) {
            if (REDIRECT_URI_ROOT == data.scheme) {
                code = data.getQueryParameter(CODE)!!
//                error = data.getQueryParameter(ERROR_CODE)!!
                if (!TextUtils.isEmpty(code)) {
                    getTokenFormUrl()
                    finish()
                }
                if (!TextUtils.isEmpty(error)) {
                    //a problem occurs, the user reject our granting request or something like that
                    Toast.makeText(this, "FAILED", Toast.LENGTH_LONG)
                        .show()
                    finish()
                }
            }
        }
        val authorizeUrl = HttpUrl.parse("https://accounts.google.com/o/oauth2/v2/auth") //
            ?.newBuilder() //
            ?.addQueryParameter("client_id", CLIENT_ID)
            ?.addQueryParameter("scope", API_SCOPE)
            ?.addQueryParameter("redirect_uri", REDIRECT_URI)
            ?.addQueryParameter("response_type", CODE)
            ?.build()

        binding.signInButton.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(authorizeUrl?.url().toString())
            i.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            Log.d("intent", "success")
            startActivityForResult(i, 121)
        }

        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == 121) {
                Toast.makeText(this, "called", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getTokenFormUrl() {
        val oAuthServer: OauthServer = RetrofitBuild.apiService!!
        Log.d("RequestBody", "$code --- $CLIENT_ID --- $REDIRECT_URI --- $AUTHORIZATION_CODE ")
        val getRequestTokenFormCall: Call<OauthToken> =
            oAuthServer.requestTokenForm(
                code,
                CLIENT_ID,
                REDIRECT_URI,
                AUTHORIZATION_CODE
            )

        getRequestTokenFormCall?.enqueue(object : Callback<OauthToken?> {
            override fun onResponse(call: Call<OauthToken?>, response: Response<OauthToken?>) {
                Log.d("ON_RESPONSE", "${response.body()}")
                response.body()
                Constant.setAccessTokens(
                    baseContext,
                    response.body()!!.access_token,
                    response.body()!!.refresh_token
                )

                startLogActivity(true)
            }
            override fun onFailure(call: Call<OauthToken?>, t: Throwable) {
                Log.d("ON_Failed", "${t.message}")
            }
        })
    }

    private fun startLogActivity(b: Boolean) {
        val i = Intent(this, WebView::class.java)
        startActivity(i)
        if (b) {
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        finish()
    }
}
