package com.bharath.dailyquotesapp.feature_quotes.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.HomeScreen


@Composable
fun MyNavHost(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    starDestination: String,
) {

    NavHost(
        navController = navHostController,
        startDestination = starDestination,
        builder = {
            composable(Screens.Home.route) {
                HomeScreen(
                    navHostController,
                    paddingValues,
                )
            }
        }
    )


}


sealed class Screens(val route: String, val label: String) {
    object Home : Screens(
        "HomeScreen",
        label = "Home"
    )

}