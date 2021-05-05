package com.cpw.myclass.http

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit


class OkHttpManager private constructor() {
    private val logInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor(HttpLogger())
    private val mOkHttpClient: OkHttpClient
    private val mHandler: Handler

    /**
     * 对外提供的Get方法访问
     *
     * @param url
     * @param callBack
     */
    operator fun get(url: String, callBack: MyCallback) {
        /**
         * 通过url和GET方式构建Request
         */
        val request: Request = bulidRequestForGet(url)
        /**
         * 请求网络的逻辑
         */
        requestNetWork(request, callBack)
    }

    /**
     * GET方式构建Request
     *
     * @param url
     * @return
     */
    private fun bulidRequestForGet(url: String): Request {
        return Request.Builder()
            .url(url)
            .get()
            .build()
    }

    /**
     * 对外提供的Post方法访问
     *
     * @param url
     * @param parms:   提交内容为表单数据
     * @param callBack
     */
    fun post(
        url: String,
        parms: Map<*, *>,
        callBack: MyCallback
    ) {
        /**
         * 通过url和POST方式构建Request
         */
        val request: Request = bulidRequestForPostByForm(url, parms as Map<String, String>)
        /**
         * 请求网络的逻辑
         */
        requestNetWork(request, callBack)
    }

    /**
     * POST方式构建Request {Form}
     *
     * @param url
     * @param parms
     * @return
     */
    private fun bulidRequestForPostByForm(
        url: String,
        parms: Map<*, *>
    ): Request {
        var builder = FormBody.Builder()
        builder = initBuilder(parms, builder)
        val body = builder.build()
//        var lastUrl = url
//        if (parms.isNotEmpty()) {
//            lastUrl += '?'
//            for (key in parms) {
//                lastUrl = lastUrl + key.key + '=' + key.value + '&'
//            }
//            lastUrl = lastUrl.substring(0, lastUrl.length - 1)
//        }
        return Request.Builder()
            .url(url)
            .post(body)
            .build()
    }

    private fun initBuilder(
        parms: Map<*, *>?,
        builder: FormBody.Builder
    ): FormBody.Builder {
        if (parms != null) {
            for ((key, value) in parms) {
                if (value is String) {
                    builder.add(key as String, value)
                }
            }
        }
        return builder
    }

    private fun requestNetWork(request: Request, callBack: MyCallback) {
        /**
         * 处理连网逻辑，此处只处理异步操作enqueue
         */
        callBack.onBefore(request)
        mOkHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException) {
                mHandler.post { Log.e("okHttpError", "请求出错" + e.message) }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call?, response: Response) {
                if (response.isSuccessful) {
                    val json: String = response.body()?.string() ?: ""
                    mHandler.post {
                        callBack.onSuccess(json)
                        callBack.onAfter()
                    }
                } else {
                    mHandler.post { callBack.onAfter() }
                }
                response.body()?.close()
            }
        })
    }

    companion object {
        /**
         * 网络访问要求singleton
         */
        var instance: OkHttpManager? = null
            get() {
                if (field == null) {
                    synchronized(OkHttpManager::class.java) {
                        if (field == null) {
                            field = OkHttpManager()
                        }
                    }
                }
                return field
            }
            private set
        val JSON = MediaType.parse("application/json; charset=utf-8")
    }

    init {
        /**
         * okHttp3中超时方法移植到Builder中
         */
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        mOkHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(logInterceptor)
                .build()
        mHandler = Handler(Looper.getMainLooper())
    }
}
