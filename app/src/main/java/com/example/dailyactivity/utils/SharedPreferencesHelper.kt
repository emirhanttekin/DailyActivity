package com.example.dailyactivity.utils

import android.content.Context

object SharedPreferencesHelper {

    private const val USER_PREFS = "user_prefs"
    private const val USER_NAME = "user_name"
    private const val USER_EMAIL = "user_email"
    private const val PREFERENCES_FILE_KEY = "com.example.dailyactivity.PREFERENCE_FILE_KEY"
    private const val USER_ID_KEY = "USER_ID_KEY"
    private const val USER_PHOTO = "user_photo"

    fun setUserName(context: Context, userName: String) {
        val sharedPrefs = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putString(USER_NAME, userName)
        editor.apply()
    }

    fun getUserName(context: Context): String? {
        val sharedPrefs = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE)
        return sharedPrefs.getString(USER_NAME, null)
    }

    fun setUserEmail(context: Context, userEmail: String) {
        val sharedPrefs = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putString(USER_EMAIL, userEmail)
        editor.apply()
    }

    fun getUserEmail(context: Context): String? {
        val sharedPrefs = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE)
        return sharedPrefs.getString(USER_EMAIL, null)
    }

    fun setUserId(context: Context, userId: Int) {
        val sharedPref = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt(USER_ID_KEY, userId)
        editor.apply()
    }

    fun getUserId(context: Context): Int {
        val sharedPref = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        return sharedPref.getInt(USER_ID_KEY, -1)
    }



    fun getUserPhoto(context: Context): String? {
        val sharedPref = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        return sharedPref.getString(USER_PHOTO, null)
    }
}
