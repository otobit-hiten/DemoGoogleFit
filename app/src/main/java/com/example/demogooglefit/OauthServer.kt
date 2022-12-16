package com.example.demogooglefit

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface OauthServer {
    @FormUrlEncoded
    @POST("oauth2/v2/token")
    fun oauthToken(
        @Field("code") code:String,
        @Field("client_id") client_id: String,
        @Field("redirect_uri") redirect_uri :String,
        @Field("grant_type") grant_type:String
    ) :Call<OauthToken>

}