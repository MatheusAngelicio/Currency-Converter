package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.network.KtorClient
import com.example.currencyconverter.domain.model.CurrencyConversion
import com.example.currencyconverter.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val ktorClient: KtorClient
) : CurrencyRepository {

    override suspend fun convertCurrency(
        fromCurrency: String,
        toCurrency: String,
        amount: Double
    ): Result<CurrencyConversion> {
        // "runCatching" > serve pra trabalhar com "Result" :
        // vai rodar o codigo que esta dentro {}
        // se extourar alguma excecao o runCatching vai retornar um Result com a excecao
        // se cair no sucesso vai retornar o Result com sucesso
        return runCatching {
            val response = ktorClient.convertCurrency(
                fromCurrency = fromCurrency,
                toCurrency = toCurrency,
                amount = amount
            )

            // retorna um objeto CurrencyConversion ja mapeado com os dados da response
            CurrencyConversion(
                baseCode = response.baseCode,
                targetCode = response.targetCode,
                conversionRate = response.conversionRate.toString(),
                conversionResult = response.conversionResult.toString()
            )

        }


    }
}