package com.cpw.myclass.http

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor


class HttpLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Log.d("HttpLogInfo", message) //okHttp的详细日志会打印出来
    }
}