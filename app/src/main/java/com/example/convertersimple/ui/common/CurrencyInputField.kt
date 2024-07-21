package com.example.convertersimple.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
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