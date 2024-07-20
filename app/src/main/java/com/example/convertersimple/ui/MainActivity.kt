package com.example.convertersimple.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.convertersimple.features.convert.ui.ConverterScreen
import com.example.convertersimple.features.convert.ui.ErrorScreen
import com.example.convertersimple.features.convert.ui.ResultScreen
import com.example.convertersimple.ui.theme.ConverterSimpleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConverterSimpleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "main_screen") {
                        composable("main_screen") {
                            ConverterScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )
                        }
                        composable(
                            "main_screen/{value}/{currency}", arguments = listOf(
                                navArgument("value") { type = NavType.FloatType },
                                navArgument("currency") { type = NavType.StringType },
                            )
                        ) { backStackEntry ->
                            val value = backStackEntry.arguments?.getFloat("value", 0f)
                            val currency = backStackEntry.arguments?.getString("username")
                            ResultScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                                value!!,
                                currency!!
                            )
                        }
                        composable(
                            "main_screen/error_screen?error={error}",
                            arguments = listOf(navArgument("error") {
                                defaultValue = "Something went wrong while fetching data"
                            })
                        ) { backStackEntry ->
                            ErrorScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                                backStackEntry.arguments?.getString("error")!!
                            )
                        }
                    }
                }
            }
        }
    }
}