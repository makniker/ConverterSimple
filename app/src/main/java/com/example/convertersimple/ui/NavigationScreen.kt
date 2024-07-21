package com.example.convertersimple.ui

sealed class NavigationScreen(val route: String) {
    data object ConverterScreen: NavigationScreen("main_screen")
    data object ResultScreen: NavigationScreen("result")
}