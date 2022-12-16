package com.example.demogooglefit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuild {

    const val BASE_URL ="https://www.googleapis.com/"

    fun getInstance():Retrofit{
        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    val apiService :OauthServer? = getInstance().create(OauthServer::class.java)

}