package com.bharath.dailyquotesapp.feature_quotes.domain.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("DailyQuotes")

class DataStoreRepository(
    val context: Context,
) {

    private object PrefKeys {
        val dateSaved = stringPreferencesKey("dateToday")

    }

    private val datastore = context.dataStore

    suspend fun saveDate(date: String) {
        datastore.edit {
            it[PrefKeys.dateSaved] = date
        }
    }

    fun getLastSavedDate(): Flow<String> {
        return datastore.data.catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            val opt = it[PrefKeys.dateSaved] ?: "N/A"
            opt
        }

    }

}