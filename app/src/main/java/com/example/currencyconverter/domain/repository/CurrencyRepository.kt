package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.domain.model.CurrencyConversion

interface  CurrencyRepository {

    suspend fun convertCurrency(
        fromCurrency: String,
        toCurrency: String,
        amount: Double
    ): Result<CurrencyConversion>

}