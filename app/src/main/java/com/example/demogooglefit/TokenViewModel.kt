package com.example.demogooglefit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginwithotp.Resource

class TokenViewModel : ViewModel() {

    private var repo = Repo()

    fun token(code:String,client_id:String,grant_type:String,redirect_uri:String) : MutableLiveData<Resource<OauthToken>>{
        return repo.tokenRepo(code,client_id, grant_type, redirect_uri)
    }
}