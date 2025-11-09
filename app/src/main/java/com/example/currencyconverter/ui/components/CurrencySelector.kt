package com.example.currencyconverter.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.R
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme

@Composable
fun CurrencySelector(
    currencies: List<String>,
    selectedCurrency: String,
    onCurrencySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .clickable {
                expanded = true
            }
            .padding(16.dp)
    ) {
        Text(
            text = selectedCurrency,
            fontWeight = FontWeight.Bold
        )
        Icon(
            painter = painterResource(R.drawable.ic_arrow_drop_down),
            contentDescription = null,
            modifier = Modifier.padding(start = 8.dp)
        )
    }

    Box {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            currencies.forEach { currencies ->
                DropdownMenuItem(
                    text = { Text(text = currencies) },
                    onClick = {
                        onCurrencySelected(currencies)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrencySelectorPreview() {
    CurrencyConverterTheme {
        CurrencySelector(
            currencies = listOf("USD", "EUR", "GBP"),
            selectedCurrency = "USD",
            onCurrencySelected = {}
        )
    }

}