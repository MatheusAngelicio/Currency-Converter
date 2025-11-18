package com.example.currencyconverter.ui.feature.converter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.core.UiState
import com.example.currencyconverter.domain.repository.CurrencyRepository
import com.example.currencyconverter.ui.feature.converter.model.ConverterFormEvent
import com.example.currencyconverter.ui.feature.converter.model.ConverterFormState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConverterViewModel(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val _formState = MutableStateFlow(ConverterFormState())
    val formState = _formState.asStateFlow()

    private val _conversionState = MutableStateFlow<UiState>(UiState.Idle)
    val conversionState = _conversionState.asStateFlow()

    init {
        _formState.update { state ->
            state.copy(
                fromCurrenciesList = listOf("BRL", "USD", "EUR"),
                toCurrenciesList = listOf("USD", "BRL", "EUR"),
                fromCurrencySelected = "BRL",
                toCurrencySelected = "USD"
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
                convertCurrency()
            }
        }
    }

    private fun convertCurrency() {
        // "viewModelScope.launch" para vincular o ciclo de vida da coroutine com o ciclo de vida do ViewModel
        // se o viewmodel morrer no meio de uma requisicao por exemplo, nao preciso cuidar disso
        viewModelScope.launch {
            val fromCurrency = _formState.value.fromCurrencySelected
            val toCurrency = _formState.value.toCurrencySelected
            val fromAmount = _formState.value.fromCurrencyAmount.toDoubleOrNull()

            if (fromCurrency.isNotBlank() && toCurrency.isNotBlank() && fromAmount != null) {
                _conversionState.update { UiState.Loading }

                currencyRepository.convertCurrency(
                    fromCurrency,
                    toCurrency,
                    fromAmount
                ).fold(
                    // Ja que estou usando "RESULT", com o metodo "fold" consigo recuperar o valor de sucesso, ou valor de erro se caiu no erro la no meu repository
                    onSuccess = {

                        // autalizo o formulario la na UI com o valor convertido
                        _formState.update {
                            it.copy(toCurrencyAmount = it.toCurrencyAmount)
                        }

                        // atualizo o estado da minha variavel de requisicao passando que deu sucesso
                        _conversionState.update {
                            UiState.Success
                        }
                    },
                    onFailure = { error ->
                        _conversionState.update {
                            UiState.Error(error.message ?: "Something went wrong")
                        }
                    }
                )


            } else {
                _conversionState.update {
                    UiState.Error("Invalid input")
                }
            }


        }
    }
}