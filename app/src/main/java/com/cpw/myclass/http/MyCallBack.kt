package com.cpw.myclass.http
import okhttp3.*
import java.io.IOException

interface MyCallback {
    fun onSuccess(json: String)
    fun onBefore(request: Request)
    fun onAfter()
    fun onFailed(e: IOException)
}