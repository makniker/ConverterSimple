package com.example.convertersimple.features.convert.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.convertersimple.ui.theme.ConverterSimpleTheme

@Composable
fun ErrorScreen(modifier: Modifier, errorMessage: String, onRetryButtonPressed: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Error",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Red,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = errorMessage, style = MaterialTheme.typography.titleLarge, color = Color.Red
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = onRetryButtonPressed, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Try Again")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContentErrorPreview() {
    ConverterSimpleTheme {
        ErrorScreen(
            errorMessage = "Something went wrong",
            onRetryButtonPressed = {},
            modifier = Modifier
        )
    }
}
