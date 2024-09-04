package com.mit.shop

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()

    fun saveUserId(userId: Int) {
        editor.putInt("user_id", userId).apply()
    }

    fun getUserId(): Int {
        return prefs.getInt("u" +
                "ser_id", -1)
    }

    fun saveAuthToken(token: String) {
        editor.putString("auth_token", token).apply()
    }

    fun getAuthToken(): String? {
        return prefs.getString("auth_token", null)
    }

    fun clearSession() {
        editor.clear().apply()
    }
}
