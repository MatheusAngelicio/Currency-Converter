package com.example.currencyconverter.ui.feature.converter

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme

@Composable
fun ConverterScreen() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterContent() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Currency Converter") }
            )
        }
    ) { }

}

@Preview
@Composable
private fun ConverterContentPreview() {
    CurrencyConverterTheme {
        ConverterContent()
    }
}