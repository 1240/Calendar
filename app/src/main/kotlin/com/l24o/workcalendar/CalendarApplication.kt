package com.l24o.workcalendar

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDexApplication

class CalendarApplication : MultiDexApplication() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}