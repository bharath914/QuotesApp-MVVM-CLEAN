package com.bharath.dailyquotesapp.feature_quotes.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun MyBottomNavigationBar(
    navHostController: NavHostController,
) {

    val list = listOf(
        Screens.Saved,
        Screens.AllQuotes,
        Screens.TagScreens,
        Screens.AuthorScreen,
    )
    val clr = MaterialTheme.colorScheme.background.copy(alpha = 0.7f)
    val currentScreen by navHostController.currentBackStackEntryAsState()
    val currentRoute = currentScreen?.destination?.route

    val set = hashSetOf<String>(
        Screens.QuoteDetailScreen.route,
        Screens.SearchScreen.route

    )

    AnimatedVisibility(
        visible = !set.contains(currentRoute), enter = expandVertically(tween(200)),
        exit = shrinkVertically(tween(150))
    ) {


        NavigationBar(
            containerColor = clr
        ) {


            list.forEachIndexed { index, screens ->

                NavigationBarItem(
                    selected = currentRoute == screens.route,
                    onClick = {

                        if (screens.route != currentRoute) {

                            navHostController.popBackStack()

                            navHostController.navigate(screens.route) {
                                this.launchSingleTop = true

                            }
                        }
                    },
                    icon = {
                        Icon(imageVector = screens.icon!!, contentDescription = null)
                    },
                    label = {
                        Text(text = screens.label, fontWeight = FontWeight.Bold)
                    }
                )
            }
        }
    }
}