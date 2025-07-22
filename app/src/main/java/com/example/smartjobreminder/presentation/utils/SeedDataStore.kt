package com.example.smartjobreminder.presentation.utils


import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class SeedDataStore(private val context: Context) {

    private val KEY_IS_SEEDED = booleanPreferencesKey("is_seeded")

    suspend fun isSeeded(): Boolean {
        return context.dataStore.data
            .map { preferences -> preferences[KEY_IS_SEEDED] ?: false }
            .first()
    }

    suspend fun setSeeded(value: Boolean) {
        context.dataStore.edit { settings ->
            settings[KEY_IS_SEEDED] = value
        }
    }
}
