/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.myapplication.sleepypck.sleepquality

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.sleepypck.database.SleepDatabaseDao
import kotlinx.coroutines.*
import okhttp3.Dispatcher

class SleepQualityViewModel(
    private val sleepNightKey: Long = 0L,
    val database: SleepDatabaseDao
) : ViewModel(){

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _endQuality = MutableLiveData<Boolean>()
    val endQuality: LiveData<Boolean>
        get() = _endQuality

    fun onValutation(quality: Int){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val toVal = database.get(sleepNightKey)?: return@withContext
                toVal.sleepQuality = quality
                database.update(toVal)
            }
            _endQuality.value = true
        }
    }

    fun endValutation(){
        _endQuality.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}