// Name: Jenny Vu
// CWID:  884427030
// Email: jvu00@csu.fullerton.edu
package edu.fz.cs411.persproject

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.lang.IllegalStateException



class MyAppRepo private constructor(private val dataStore: DataStore<Preferences>) {

    val rSwitchLoad: Flow<Boolean> =this.dataStore.data.map {prefs ->
        prefs[rSwitchKey] ?: true
    }.distinctUntilChanged()
    val gSwitchLoad: Flow<Boolean> =this.dataStore.data.map {prefs ->
        prefs[gSwitchKey] ?: true
    }.distinctUntilChanged()
    val bSwitchLoad: Flow<Boolean> =this.dataStore.data.map {prefs ->
        prefs[bSwitchKey] ?: true
    }.distinctUntilChanged()

    val rValLoad: Flow<Int> =this.dataStore.data.map {prefs ->
        prefs[rValKey] ?: 0
    }.distinctUntilChanged()
    val gValLoad: Flow<Int> =this.dataStore.data.map {prefs ->
        prefs[gValKey] ?: 0
    }.distinctUntilChanged()
    val bValLoad: Flow<Int> =this.dataStore.data.map {prefs ->
        prefs[bValKey] ?: 0
    }.distinctUntilChanged()

    suspend fun saveSwitch(value: Boolean, index: Int) {
        val switchKey: Preferences.Key<Boolean> = when (index) {
            1 -> rSwitchKey
            2 -> gSwitchKey
            3 -> bSwitchKey
            else -> {
                throw NoSuchFieldException("Invalid Switch Index: $index")
            }
        }
        saveSwitches(switchKey, value)
        Log.d("RepoSwitch", "Switch was stored")
    }
    private suspend fun saveSwitches(key: Preferences.Key<Boolean>, value: Boolean){
        this.dataStore.edit{ prefs ->
            prefs[key]=value
        }
        Log.i("IN SWITCHES", "$key and $value")
    }

    suspend fun saveValue(value: Int, index: Int) {
        val valueKey: Preferences.Key<Int> = when (index) {
            1 -> rValKey
            2 -> gValKey
            3 -> bValKey
            else -> {
                throw NoSuchFieldException("Invalid Switch Index: $index")
            }
        }
        saveValues(valueKey, value)
        Log.d("RepoVal", "Value was stored")
    }
    private suspend fun saveValues(key: Preferences.Key<Int>, value: Int){
        this.dataStore.edit{ prefs ->
            prefs[key]=value
        }
        Log.i("IN VALUES", "$key and $value")
    }

    companion object{
        private const val PREF_DATA_FILE_NAME = "MyDataStoreFile"
        private val rSwitchKey = booleanPreferencesKey("rSwitchK")
        private val gSwitchKey = booleanPreferencesKey("gSwitchK")
        private val bSwitchKey = booleanPreferencesKey("bSwitchK")
        private val rValKey = intPreferencesKey("rValK")
        private val gValKey = intPreferencesKey("gValK")
        private val bValKey = intPreferencesKey("bValK")


        private var singleInstanceOfMyAppRepo: MyAppRepo? = null

        fun initialize(context: Context){
            if(singleInstanceOfMyAppRepo == null){
                val dataStore = PreferenceDataStoreFactory.create{
                    context.preferencesDataStoreFile(PREF_DATA_FILE_NAME)
                }
                singleInstanceOfMyAppRepo = MyAppRepo(dataStore)
                Log.d("Repo", "initalizing MyAppRepo")
            }
        }
        fun getRepo(): MyAppRepo{
            Log.d("Repo", "getting MyAppRepo")
            return singleInstanceOfMyAppRepo ?: throw  IllegalStateException("singleInstance not initialized")
        }
    }
}