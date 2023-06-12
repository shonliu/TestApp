package com.liubka.test

import android.app.Application
import com.liubka.test.repository.MyRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication: Application() {

    @Inject
    lateinit var myRepository: MyRepository
}