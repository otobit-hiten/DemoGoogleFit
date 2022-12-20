package com.example.demogooglefit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crooze.demogooglefit.Constant
import com.example.demogooglefit.databinding.ActivityWebViewBinding

class WebView : AppCompatActivity() {
    lateinit var binding: ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.acc.text = Constant.getAccessTokens(baseContext)

    }
}