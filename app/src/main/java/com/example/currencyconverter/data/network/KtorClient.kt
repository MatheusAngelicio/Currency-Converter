package com.example.currencyconverter.data.network

import com.example.currencyconverter.data.network.apiKey.CURRENCY_API_KEY
import com.example.currencyconverter.data.network.model.CurrencyConversionResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ApiClient {
    val client = HttpClient {
        expectSuccess = true

        defaultRequest {
            url("https://v6.exchangerate-api.com/v6/${CURRENCY_API_KEY}/ ")
        }

        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }

        install(Logging) {
            logger = Logger.SIMPLE
        }
    }
}

class KtorClient(private val client: HttpClient = ApiClient.client) {

    suspend fun convertCurrency(
        fromCurrency: String,
        toCurrency: String,
        amount: Double
    ): CurrencyConversionResponse {
        return client.get("pair/${fromCurrency}/${toCurrency}/${amount}").body()
    }


}