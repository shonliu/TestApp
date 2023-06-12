package com.liubka.test.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liubka.test.repository.MyRepository
import com.liubka.test.workmanager.InitWorkerHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val initWorkerHandler: InitWorkerHandler,
    private val myRepository: MyRepository
) : ViewModel() {

    private val _timeStampLivedata = MutableLiveData<String>()
    val timeStampLivedata: LiveData<String> = _timeStampLivedata

    fun initWorker() {
        initWorkerHandler.initWorker()
    }

    fun processRebootedTimestamps() {
        viewModelScope.launch(Dispatchers.IO) {
            val timestamps = myRepository.getObjects()
            var result = ""

            if(timestamps.isNotEmpty()) {
                timestamps.forEachIndexed { index, obj ->
                    result += "● ${index.inc()} - ${obj.timestamp} ${System.lineSeparator()}"
                }
            } else {
                result = "No boots detected"
            }
            _timeStampLivedata.postValue(result)
        }
    }
}