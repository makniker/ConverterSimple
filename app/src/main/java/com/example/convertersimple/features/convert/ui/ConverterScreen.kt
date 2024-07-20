package com.example.convertersimple.features.convert.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.convertersimple.ui.theme.ConverterSimpleTheme

@Composable
fun ConverterScreen(viewModel: ConverterViewModel = viewModel(), modifier: Modifier, navController: NavController) {
    val state = viewModel.uiState.collectAsState().value
    val screenModifier = Modifier
        .then(modifier)
        .fillMaxSize()
    when (state) {
        is ConverterUiState.Content -> ContentMain(
            state,
            { sum, base, exchange -> viewModel.convertCurrency(sum, base, exchange) },
            screenModifier
        )

        is ConverterUiState.Error -> ContentError(state, screenModifier)
        ConverterUiState.Loading -> ContentLoading(screenModifier)
    }
}

@Composable
fun ContentMain(
    state: ConverterUiState.Content,
    onConvertButtonPressed: (sum: Double, base: String, exchange: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val currencies = listOf("USD", "EUR", "RUB", "JPY", "GBP")
    var selectedBaseCurrency by remember { mutableStateOf(currencies[0]) }
    var selectedExchangedCurrency by remember { mutableStateOf(currencies[1]) }
    var amount by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    fun validate(text: String) {
        isError = text.isEmpty()
        try {
            text.toDouble()
            isError = false
        } catch (e: NumberFormatException) {
            isError = true
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Currency Converter", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.weight(1f))
        Row {
            OutlinedTextField(value = amount,
                onValueChange = {
                    amount = it
                    validate(amount)
                },
                label = { Text(text = "Amount") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                supportingText = { Text(text = if (isError) "Not a number" else "Must be number") },
                isError = isError
            )

            CurrencyInputField(
                modifier = Modifier.weight(1f),
                selectedBaseCurrency,
                { newText -> selectedBaseCurrency = newText },
                currencies = currencies
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                value = "",
                readOnly = true,
                onValueChange = {},
                label = { Text("Choose Currency") },
                singleLine = true,
                enabled = false,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            CurrencyInputField(
                modifier = Modifier.weight(1f),
                selectedExchangedCurrency,
                { newText -> selectedExchangedCurrency = newText },
                currencies = currencies
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                onConvertButtonPressed(
                    amount.toDouble(), selectedBaseCurrency, selectedExchangedCurrency
                )
            }, modifier = Modifier.fillMaxWidth(), enabled = !isError && amount.isNotBlank()
        ) {
            Text(text = "Convert")
        }
    }
}

@Composable
fun ContentLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .then(modifier)
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
fun ContentError(state: ConverterUiState.Error, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = state.error)
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Try Again")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContentPreview() {
    ConverterSimpleTheme {
        ConverterScreen(modifier = Modifier, navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun ContentMainPreview() {
    fun convertCurrency(sum: Double, base: String, exchange: String) {}
    ConverterSimpleTheme {
        ContentMain(ConverterUiState.Content(),
            { sum, base, exchange -> convertCurrency(sum, base, exchange) })
    }
}

@Preview(showBackground = true)
@Composable
fun ContentErrorPreview() {
    ConverterSimpleTheme {
        ContentError(ConverterUiState.Error("Something went wrong"), modifier = Modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun ContentLoadingPreview() {
    ConverterSimpleTheme {
        ContentLoading(modifier = Modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyInputField(
    modifier: Modifier,
    selectedCurrency: String,
    onSelectedCurrencyChanged: (String) -> Unit,
    currencies: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCurrencyState by remember { mutableStateOf(selectedCurrency) }
    ExposedDropdownMenuBox(
        expanded = expanded, onExpandedChange = { expanded = it }, modifier = modifier.padding(8.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.menuAnchor(),
            value = selectedCurrencyState,
            onValueChange = onSelectedCurrencyChanged,
            singleLine = true,
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = {
            expanded = false
        }) {
            currencies.forEach { selectionOption ->
                DropdownMenuItem(onClick = {
                    onSelectedCurrencyChanged(selectionOption)
                    expanded = false
                    selectedCurrencyState = selectionOption
                }, text = { Text(text = selectionOption) })
            }
        }


    }
}