package com.example.androidtestschool

object Bodies {
    data class GetCurrencyBody(
        val rates: Map<String, Float>
    )
}