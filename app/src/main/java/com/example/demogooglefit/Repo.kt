package com.example.demogooglefit

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.loginwithotp.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repo {

    fun tokenRepo(code:String,client_id:String,grant_type:String,redirect_uri:String): MutableLiveData<Resource<OauthToken>>{
        val mutableLiveData: MutableLiveData<Resource<OauthToken>> = MutableLiveData()

        mutableLiveData.postValue(Resource.loading("",null))
        val call : Call<OauthToken>? = RetrofitBuild.apiService?.oauthToken(code,client_id,redirect_uri,grant_type)

        call?.enqueue(object : Callback<OauthToken?> {
            override fun onResponse(call: Call<OauthToken?>, response: Response<OauthToken?>) {
                val token: OauthToken? = response.body()
                if(token != null){
                    mutableLiveData.postValue(Resource.success(token))
                    Log.d("REPOSUCCESS","$token")
                }
            }
            override fun onFailure(call: Call<OauthToken?>, t: Throwable) {
                mutableLiveData.postValue( Resource.error("", null))            }
        })
        return mutableLiveData
    }
}