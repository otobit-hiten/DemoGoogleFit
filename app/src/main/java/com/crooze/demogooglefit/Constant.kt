package com.crooze.demogooglefit

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class Constant {

    companion object{
        private const val SHARED_PREFERENCE ="SharedPreference"
        private val ACCESS_TOKEN = "access_token"
        private val REFRESH_TOKEN = "refresh_token"

        fun setAccessTokens(context: Context, access_token :String, refresh_token:String){
            val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE,MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(ACCESS_TOKEN, access_token)
            editor.putString(REFRESH_TOKEN,refresh_token )
            editor.apply()
        }

        fun getAccessTokens(context: Context) : String {
            val shared: SharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE)
            val token = shared.getString(ACCESS_TOKEN, "")
            return token!!
        }

        fun getRefreshTokens(context: Context) : String {
            val shared: SharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE)
            val token = shared.getString(REFRESH_TOKEN, "")
            return token!!
        }


    }
}