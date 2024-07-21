package com.example.convertersimple.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.convertersimple.features.convert.ui.ConverterScreen
import com.example.convertersimple.features.convert.ui.ConverterViewModel
import com.example.convertersimple.features.convert.ui.ResultScreen
import com.example.convertersimple.ui.theme.ConverterSimpleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val viewModel: ConverterViewModel = viewModel()
            ConverterSimpleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = "main_screen") {
                        composable(NavigationScreen.ConverterScreen.route) {
                            ConverterScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                                viewModel = viewModel,
                            )
                        }
                        composable(
                            "${NavigationScreen.ConverterScreen.route}/${NavigationScreen.ResultScreen.route}",
                        ) {
                            ResultScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                                viewModel = viewModel,
                            )
                        }
                    }
                }
            }
        }
    }
}