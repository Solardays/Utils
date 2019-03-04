package com.util.sample

import android.app.Application
import com.util.Utils
import com.util.exception.CrashHandler

class BaseApp:Application(){

    override fun onCreate() {
        super.onCreate()
        CrashHandler.getInstance().init(this)
        Utils.init(this)
    }
}