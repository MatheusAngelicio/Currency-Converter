package com.example.currencyconverter.ui.feature.converter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconverter.R
import com.example.currencyconverter.ui.feature.converter.model.ConverterFormEvent
import com.example.currencyconverter.ui.feature.converter.model.ConverterFormState
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme

@Composable
fun ConverterScreen() {

    val viewModel = viewModel<ConverterViewModel>()
    val formState by viewModel.formState.collectAsStateWithLifecycle()

    ConverterContent(
        formState = formState,
        onFormEvent = viewModel::onFormEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterContent(
    formState: ConverterFormState,
    onFormEvent: (ConverterFormEvent) -> Unit
) {
    Scaffold(
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.medium,
                contentPadding = PaddingValues(vertical = 16.dp),
                onClick = {},
            ) {
                Text(text = "Currency Converter")
            }
        },
        // para que quando teclado subir, o botao da bottombar acompanhar o teclado e ficar em cima dele
        modifier = Modifier.imePadding(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Currency Converter", fontWeight = FontWeight.SemiBold)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    // sobrescrevendo o containerColor
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .consumeWindowInsets(innerPadding)
                // para respeitar a systembar do dispositivo
                .systemBarsPadding()
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column {
                    CurrencyField(
                        currencies = formState.fromCurrenciesList,
                        selectedCurrency = formState.fromCurrencySelected,
                        currencyAmount = formState.fromCurrencyAmount,
                        onCurrencySelected = {
                            onFormEvent(ConverterFormEvent.OnFromCurrencySelected(it))
                        },
                        onCurrencyAmountChanged = {
                            onFormEvent(ConverterFormEvent.OnFromCurrencyAmountChanged(it))
                        },
                    )

                    CurrencyField(
                        currencies = formState.toCurrenciesList,
                        selectedCurrency = formState.toCurrencySelected,
                        currencyAmount = formState.toCurrencyAmount,
                        onCurrencySelected = {
                            onFormEvent(ConverterFormEvent.OnToCurrencySelected(it))
                        },
                        onCurrencyAmountChanged = {},
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                    border = BorderStroke(
                        width = 0.5.dp,
                        color = Color.LightGray,
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_downward),
                        contentDescription = "Arrow Icon",
                        modifier = Modifier.padding(4.dp),
                    )
                }
            }
        }
    }

}

@Preview
@Composable
private fun ConverterContentPreview() {
    CurrencyConverterTheme {
        ConverterContent(
            formState = ConverterFormState(
                fromCurrenciesList = listOf("BRL", "USD", "EUR"),
                toCurrenciesList = listOf("USD", "BRL", "EUR"),
                fromCurrencySelected = "BRL",
                toCurrencySelected = "USD"
            ),
            onFormEvent = {}
        )
    }
}