package com.example.androidtestschool

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Контейнер для фабрики и клиента (стафф для ретрофита)
object ApiWorker {
    private var mClient: OkHttpClient? = null
    private var mGsonConverter: GsonConverterFactory? = null

    val client: OkHttpClient
        get() {
            if (mClient == null) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                val httpBuilder = OkHttpClient.Builder()
                httpBuilder
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                mClient = httpBuilder.build()

            }
            return mClient!!
        }


    val gsonConverter: GsonConverterFactory
        get() {
            if (mGsonConverter == null) {
                mGsonConverter = GsonConverterFactory
                    .create()
            }
            return mGsonConverter!!
        }
}