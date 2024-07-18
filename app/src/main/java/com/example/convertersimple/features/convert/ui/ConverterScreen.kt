package com.example.convertersimple.features.convert.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.convertersimple.ui.theme.ConverterSimpleTheme

@Composable
fun ConverterScreen() {
}

@Composable
fun Content() {
    val currencies = listOf("USD", "EUR", "RUB", "JPY", "GBP")
    var selectedBaseCurrency by remember { mutableStateOf("USD") }
    var selectedExchangedCurrency by remember { mutableStateOf("USD") }
    var amount by remember { mutableStateOf("") }
    var calculatedAmount by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Currency Converter", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.weight(1f))
        Row {
            OutlinedTextField(value = amount,
                onValueChange = { amount = it },
                label = { Text(text = "Amount") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            InputFields(
                modifier = Modifier.weight(1f),
                "",
                { newText -> selectedBaseCurrency = newText },
                currencies = currencies
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            OutlinedTextField(value = calculatedAmount,
                readOnly = true,
                onValueChange = {},
                label = { Text(text = "Calculated amount") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),

            )

            InputFields(
                modifier = Modifier.weight(1f),
                "",
                { newText -> selectedExchangedCurrency = newText },
                currencies = currencies
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {}, modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Convert")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContentPreview() {
    ConverterSimpleTheme {
        Content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputFields(
    modifier: Modifier,
    selectedCurrency: String,
    onSelectedCurrencyChanged: (String) -> Unit,
    currencies: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded, onExpandedChange = { expanded = it }, modifier = modifier.padding(8.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.menuAnchor(),
            value = "USD",
            onValueChange = onSelectedCurrencyChanged,
            readOnly = true,
            singleLine = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        )
        val filteringOptions =
            currencies.filter { it.contains(selectedCurrency, ignoreCase = true) }
        if (filteringOptions.isNotEmpty()) {
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = {
                expanded = false
            }) {
                filteringOptions.forEach { selectionOption ->
                    DropdownMenuItem(onClick = {}, text = { Text(text = selectionOption) })
                }
            }
        }

    }
}