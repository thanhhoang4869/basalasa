package com.example.basalasa.utils

import android.content.Context

class Cache {
    companion object {
        private const val PREF_NAME = "B_TOKEN"
        private const val PREF_MODE = Context.MODE_PRIVATE
        private const val KEY = "TOKEN"

        fun getToken(context: Context): String? {
            val preferences = context.getSharedPreferences(PREF_NAME, PREF_MODE)
            return preferences.getString(KEY, null)
        }

        fun saveToken(context: Context, token: String): Boolean {
            val preferences = context.getSharedPreferences(PREF_NAME, PREF_MODE)
            return preferences.edit().putString(KEY, token).commit()
        }

        fun clear(context: Context): Boolean {
            val preferences = context.getSharedPreferences(PREF_NAME, PREF_MODE)
            return preferences.edit().clear().commit()
        }
    }
}