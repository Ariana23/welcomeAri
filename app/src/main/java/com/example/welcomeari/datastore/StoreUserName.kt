package com.example.welcomeari.datastore

import android.content.Context
import android.preference.Preference
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


// AGREGAAR INYECCION DE DEPEDENCIAS
class StoreUserName (private val context: Context){
    companion object{
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("UserName")
        val USER_NAME_KEY = stringPreferencesKey("user_name")
                    }
    val getName: Flow<String?> = context.dataStore.data
        .map {
            preferences ->
            preferences[USER_NAME_KEY] ?: ""
        }
    suspend fun saveName(name: String){
        context.dataStore.edit {
            preferences ->
            preferences[USER_NAME_KEY] = name
        }
    }
    }



