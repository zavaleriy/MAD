package com.example.mad.managers

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val USER_PREFERENCES_NAME = "user_preferences"
val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)

class UserManager(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        val USER_EMAIL_KEY = stringPreferencesKey("USER_EMAIL")
        val USER_NICK_KEY = stringPreferencesKey("USER_NICK")
        val USER_AVATAR_KEY = stringPreferencesKey("USER_AVATAR")
        val USER_TOKEN_KEY = stringPreferencesKey("USER_TOKEN")
    }

    suspend fun storeUser(email: String, nick: String, avatar: String, token: String) {
        dataStore.edit {
            it[USER_EMAIL_KEY] = email
            it[USER_NICK_KEY] = nick
            it[USER_AVATAR_KEY] = avatar
            it[USER_TOKEN_KEY] = token
        }
    }

    val userEmailFlow: Flow<String> = dataStore.data.map {
        it[USER_EMAIL_KEY] ?: ""
    }

    val userNickFlow: Flow<String> = dataStore.data.map {
        it[USER_NICK_KEY] ?: ""
    }

    val userAvatarFlow: Flow<String> = dataStore.data.map {
        it[USER_AVATAR_KEY] ?: ""
    }

    val userTokenFlow: Flow<String> = dataStore.data.map {
        it[USER_TOKEN_KEY] ?: ""
    }

    suspend fun hasData(): Boolean {
        return userTokenFlow.first().isNotEmpty()
    }

    suspend fun Logout() {
        dataStore.edit {
            it[USER_NICK_KEY] = ""
            it[USER_AVATAR_KEY] = ""
            it[USER_TOKEN_KEY] = ""
        }
    }

}