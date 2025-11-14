package com.example.currencyconverter.ui.feature.converter

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.ui.feature.converter.model.ConverterFormEvent
import com.example.currencyconverter.ui.feature.converter.model.ConverterFormState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ConverterViewModel : ViewModel() {

    private val _formState = MutableStateFlow(ConverterFormState())
    val formState = _formState.asStateFlow()

    init {
        _formState.update { state ->
            state.copy(
                fromCurrenciesList = listOf("BRL", "USD", "EUR"),
                toCurrenciesList = listOf("USD", "BRL", "EUR"),
                fromCurrencyAmount = "BRL",
                toCurrencyAmount = "USD"
            )
        }
    }

    fun onFormEvent(event: ConverterFormEvent) {
        when (event) {
            is ConverterFormEvent.OnFromCurrencySelected -> {
                _formState.update { state ->
                    state.copy(fromCurrencySelected = event.currency)
                }
            }

            is ConverterFormEvent.OnToCurrencySelected -> {
                _formState.update { state ->
                    state.copy(toCurrencySelected = event.currency)
                }
            }

            is ConverterFormEvent.OnFromCurrencyAmountChanged -> {
                _formState.update { state ->
                    state.copy(fromCurrencyAmount = event.amount)
                }
            }

            ConverterFormEvent.SendConverterFormEvent -> {
                Log.d("ConverterViewModel", "onFormEvent: ${_formState.value}")
            }
        }
    }
}