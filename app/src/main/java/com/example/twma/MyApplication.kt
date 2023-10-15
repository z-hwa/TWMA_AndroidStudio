package com.example.twma

import android.app.Application

//全局application類
class MyApplication: Application() {
    companion object {
        lateinit var instance: Application
    }

    init {
        instance = this
    }
}