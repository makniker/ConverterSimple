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
import com.example.convertersimple.ui.NavigationScreen
import com.example.convertersimple.ui.common.ContentLoading
import com.example.convertersimple.ui.common.CurrencyInputField
import com.example.convertersimple.ui.common.ErrorScreen
import com.example.convertersimple.ui.theme.ConverterSimpleTheme

@Composable
fun ConverterScreen(
    viewModel: ConverterViewModel = viewModel(),
    modifier: Modifier,
    navController: NavController,
) {
    val state = viewModel.uiState.collectAsState().value
    val screenModifier = Modifier
        .then(modifier)
        .fillMaxSize()
    viewModel.fetchCurrencies()
    when (state) {
        is ConverterUiState.Content -> ContentMain(
            state,
            { sum, base, exchange -> viewModel.convertCurrency(sum, base, exchange) },
            screenModifier,
            navController
        )

        is ConverterUiState.Error -> ErrorScreen(screenModifier,
            state.error,
        ) { viewModel.fetchCurrencies() }

        ConverterUiState.Loading -> ContentLoading(screenModifier)
    }
}

@Composable
fun ContentMain(
    state: ConverterUiState.Content,
    onConvertButtonPressed: (sum: Double, base: String, exchange: String) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    var selectedBaseCurrency by remember { mutableStateOf(state.currency[0]) }
    var selectedExchangedCurrency by remember { mutableStateOf(state.currency[1]) }
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
                currencies = state.currency
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
                currencies = state.currency
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                onConvertButtonPressed(
                    amount.toDouble(), selectedBaseCurrency, selectedExchangedCurrency
                )
                navController.navigate("${NavigationScreen.ConverterScreen.route}/${NavigationScreen.ResultScreen.route}")
            }, modifier = Modifier.fillMaxWidth(), enabled = !isError && amount.isNotBlank()
        ) {
            Text(text = "Convert")
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
    fun mockFun(sum: Double, base: String, exchange: String) {}
    ConverterSimpleTheme {
        ContentMain(ConverterUiState.Content(listOf("USD", "EUR", "RUB", "JPY", "GBP")),
            { sum, base, exchange -> mockFun(sum, base, exchange) }, navController = rememberNavController()
        )
    }
}
