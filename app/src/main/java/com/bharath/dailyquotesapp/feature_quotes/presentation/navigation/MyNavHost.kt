package com.bharath.dailyquotesapp.feature_quotes.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Draw
import androidx.compose.material.icons.outlined.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bharath.dailyquotesapp.feature_quotes.presentation.search.SearchScreen
import com.bharath.dailyquotesapp.feature_quotes.presentation.allquotes.QuotesListScreen
import com.bharath.dailyquotesapp.feature_quotes.presentation.authors.AuthorsScreen
import com.bharath.dailyquotesapp.feature_quotes.presentation.authors.authorDetail.AuthorDetailScreen
import com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.HomeScreen
import com.bharath.dailyquotesapp.feature_quotes.presentation.mainscreen.MainViewModel
import com.bharath.dailyquotesapp.feature_quotes.presentation.saved.SavedScreen


@Composable
fun MyNavHost(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    starDestination: String,
    mainViewModel: MainViewModel,
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
                        towards = AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(300)
                    ) + fadeIn(spring())
                },
                popExitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(300)
                    ) + fadeOut()
                }
            ) {
                SavedScreen(navHostController = navHostController, paddingValues)
            }


            composable(Screens.AuthorScreen.route, enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(300)
                ) + fadeIn(spring())
            },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(300)
                    ) + fadeOut()
                }) {
                AuthorsScreen(paddingValues, mainViewModel, navHostController)
            }
            composable(Screens.AllQuotes.route) {
                QuotesListScreen(paddingValues,mainViewModel)
            }
            composable(Screens.AuthorDetailScreen.route) {
                AuthorDetailScreen(
                    navHostController = navHostController,
                    paddingValues = paddingValues,
                    mainViewModel
                )
            }


            composable(Screens.SearchScreen.route){
                SearchScreen(navHostController)
            }




        }
    )


}


sealed class Screens(val route: String, val label: String, val icon: ImageVector? = null) {
    object Home : Screens(
        "HomeScreen",
        label = "Home"
    )

    object Saved : Screens(
        "SavedScreen",
        label = "Saved",
        icon = Icons.Outlined.Bookmarks
    )


    object AllQuotes : Screens(
        "AllQuotes",
        label = "AllQuotes",
        icon = Icons.Outlined.List
    )

    object AuthorScreen : Screens(
        "Authors",
        label = "Authors",
        icon = Icons.Outlined.Draw
    )


    object AuthorDetailScreen : Screens(
        "AuthorDetail",
        "AuthorDetail",

        )
    object SearchScreen : Screens(
        route = "SearchScreen/{route}",
        "SearchScreen"
    )

}