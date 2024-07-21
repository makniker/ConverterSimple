package com.example.convertersimple.features.convert.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.convertersimple.ui.common.ContentLoading
import com.example.convertersimple.ui.theme.ConverterSimpleTheme

@Composable
fun ResultScreen(modifier: Modifier, navController: NavController, viewModel: ConverterViewModel) {
    when(val state = viewModel.convertState.collectAsState().value) {
        is ResultUiState.Content -> ResultContent(modifier, state)
        is ResultUiState.Error -> ErrorScreen(modifier, state.error) {
            navController.navigateUp()
        }
        ResultUiState.Loading -> ContentLoading(modifier)
    }
}

@Composable
fun ResultContent(modifier: Modifier, state: ResultUiState.Content) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${state.convertedSum} ${state.exchange}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun ResultContentPreview() {
    ConverterSimpleTheme {
        ResultContent(Modifier, ResultUiState.Content(100.0, 100.0, "USD", "RUB"))
    }
}