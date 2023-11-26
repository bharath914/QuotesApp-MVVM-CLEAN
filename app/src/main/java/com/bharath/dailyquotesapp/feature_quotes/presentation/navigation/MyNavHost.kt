package com.bharath.dailyquotesapp.feature_quotes.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.HomeScreen
import com.bharath.dailyquotesapp.feature_quotes.presentation.saved.SavedScreen


@Composable
fun MyNavHost(
    navHostController: NavHostController,

    starDestination: String,
) {

    NavHost(
        navController = navHostController,
        startDestination = starDestination,
        builder = {
            composable(Screens.Home.route) {
                HomeScreen(
                    navHostController,

                    )
            }
            composable(
                Screens.Saved.route,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    )+ fadeIn(spring())
                },
                popExitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(300)
                    )+ fadeOut()
                }
            ) {
                SavedScreen(navHostController = navHostController)
            }
        }
    )


}


sealed class Screens(val route: String, val label: String) {
    object Home : Screens(
        "HomeScreen",
        label = "Home"
    )

    object Saved : Screens(
        "SavedScreen",
        label = "Saved"
    )

}