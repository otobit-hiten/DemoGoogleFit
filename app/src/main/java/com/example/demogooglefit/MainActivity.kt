package com.example.demogooglefit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demogooglefit.databinding.ActivityMainBinding
import com.example.loginwithotp.Status
import com.google.android.gms.common.SignInButton
import okhttp3.HttpUrl


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: TokenViewModel
    private val CLIENT_ID ="251057727253-9g15qvhuqk9hjjod01v1oin591fr3ka6.apps.googleusercontent.com"
    private val API_SCOPE = "https://www.googleapis.com/auth/fitness.activity.read"
//    private val API_SCOPE:Array<String> = arrayOf("https://www.googleapis.com/auth/fitness.activity.read",
//        "https://www.googleapis.com/auth/fitness.blood_glucose.read",
//        "https://www.googleapis.com/auth/fitness.blood_pressure.read",
//        "https://www.googleapis.com/auth/fitness.body.read",
//        "https://www.googleapis.com/auth/fitness.body_temperature.read",
//        "https://www.googleapis.com/auth/fitness.heart_rate.read",
//        "https://www.googleapis.com/auth/fitness.location.read",
//        "https://www.googleapis.com/auth/fitness.nutrition.read",
//        "https://www.googleapis.com/auth/fitness.oxygen_saturation.read",
//        "https://www.googleapis.com/auth/fitness.reproductive_health.read",
//        "https://www.googleapis.com/auth/fitness.sleep.read")
    private val REDIRECT_URI ="com.example.demogooglefit:/oauth2redirect"
    private val REDIRECT_URI_ROOT ="com.example.demogooglefit"
    private val CODE ="code"

//    private val REFRESH_TOKEN = "refresh_token"
    private val AUTHORIZATION_CODE = "authorization_code"
    private val ERROR_CODE: String = "error"


    private var code:String = ""
    private var error:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signInButton.setSize(SignInButton.SIZE_STANDARD)


        val uri = intent.data
        Log.d("DATAA","$uri")
        if (uri != null && !TextUtils.isEmpty(uri.scheme)) {
            Log.d("DATAA","$uri")
            if (REDIRECT_URI_ROOT == (uri.scheme)) {
                Log.d("redirect","$REDIRECT_URI")
                code = uri.getQueryParameter(CODE).toString()
                error = uri.getQueryParameter(ERROR_CODE).toString()
                if (!TextUtils.isEmpty(code)) {
                    Log.d("ERROR","$code")
                    Toast.makeText(applicationContext, "$code", Toast.LENGTH_SHORT).show()
                    getTokenFormUrl()
                }
                if(!TextUtils.isEmpty(error)){
                    Log.d("ERROR","$error")
                    Toast.makeText(applicationContext, "$error", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

        val webUrl = HttpUrl.parse("https://accounts.google.com/o/oauth2/v2/auth")//
            ?.newBuilder() //
            ?.addQueryParameter("client_id", CLIENT_ID)
            ?.addQueryParameter("scope", API_SCOPE)
            ?.addQueryParameter(
                "redirect_uri",REDIRECT_URI)
            ?.addQueryParameter("response_type", CODE)
            ?.build()

        binding.signInButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(webUrl.toString())

            if (webUrl != null) {
                intent.setData(Uri.parse(webUrl.url().toString()))
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun getTokenFormUrl() {
        viewModel.token(code,CLIENT_ID,AUTHORIZATION_CODE,REDIRECT_URI).observe(this){
                when(it.status){
                    Status.SUCCESS -> {
                        val tok = it.data
                        if(tok!=null){
                            Toast.makeText(applicationContext, "$tok", Toast.LENGTH_SHORT).show()
                            binding.tokken.text = tok.toString()
                            startMainActivity(true)
                        }
                        Log.d("Success", "${it.data} -- ${it.message}")
                    }
                    Status.LOADING ->{
                        Toast.makeText(applicationContext, "Error:", Toast.LENGTH_SHORT).show()
                    }
                    Status.ERROR -> {
                        Toast.makeText(applicationContext, "Loading:", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun startMainActivity(bool: Boolean) {
        val intents =  Intent(this, MainActivity::class.java);
        if(bool){
            intents.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        startActivity(intents);
        finish();

    }

}