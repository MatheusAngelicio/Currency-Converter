package com.example.currencyconverter.ui.feature.converter.model

data class ConverterFormState(
    val fromCurrenciesList: List<String> = emptyList(),
    val toCurrenciesList: List<String> = emptyList(),
    val fromCurrencySelected: String = "",
    val toCurrencySelected: String = "",
    val fromCurrencyAmount: String = "0",
    val toCurrencyAmount: String = "0",
)
