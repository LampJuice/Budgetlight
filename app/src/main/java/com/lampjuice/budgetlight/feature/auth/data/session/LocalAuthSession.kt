package com.lampjuice.budgetlight.feature.auth.data.session

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.lampjuice.budgetlight.feature.auth.domain.session.AuthSession
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.authDataStore by preferencesDataStore(
    name = "auth_session",
)

class LocalAuthSession @Inject constructor(
    @ApplicationContext private val context: Context,
) : AuthSession {

    private companion object {
        val USER_ID = longPreferencesKey("user_id")
        val TOKEN = stringPreferencesKey("access_token")
    }

    override val currentUserId: Flow<Long?> =
        context.authDataStore.data.map { preferences ->
            preferences[USER_ID]
        }
    override val token: Flow<String?> =
        context.authDataStore.data.map { preferences ->
            preferences[TOKEN]
        }

    override suspend fun setSession(userId: Long, token: String) {
        context.authDataStore.edit { preferences ->
            preferences[USER_ID] = userId
            preferences[TOKEN] = token
        }
    }

    override suspend fun clear() {
        context.authDataStore.edit { preferences ->
            preferences.remove(USER_ID)
        }
    }
}
