package com.example.convertersimple.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.convertersimple.ui.theme.ConverterSimpleTheme

@Composable
fun ContentLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .then(modifier)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContentLoadingPreview() {
    ConverterSimpleTheme {
        ContentLoading(modifier = Modifier)
    }
}