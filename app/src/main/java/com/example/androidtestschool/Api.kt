package com.example.androidtestschool

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET

interface Api {
    @GET("latest?base=USD")
    fun getCurrencies(): Call<Bodies.GetCurrencyBody>
    // объект для быстрого вызова клиента ретрофита
    companion object ApiService {
        fun getApiClient(): Api = Retrofit.Builder()
            .baseUrl("https://api.exchangeratesapi.io")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ApiWorker.gsonConverter)
            .client(ApiWorker.client)
            .build()
            .create(Api::class.java)
    }
}