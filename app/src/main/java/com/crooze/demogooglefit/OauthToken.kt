package com.example.demogooglefit

import com.squareup.moshi.Json




//public class OauthToken {
    data class OauthToken(
    val access_token: String = "",
    val token_type: String = "",
    val expires_in : String = "",
    val refresh_token : String = "",
    val scope : String = ""
)
//    @Json(name = "access_token")
//    private var accessToken: String? = null
//
//    @Json(name = "token_type")
//    private val tokenType: String? = null
//
//    @Json(name = "expires_in")
//    private val expiresIn: Long = 0
//    private val expiredAfterMilli: Long = 0
//
//    @Json(name = "refresh_token")
//    private val refreshToken: String? = null
//}