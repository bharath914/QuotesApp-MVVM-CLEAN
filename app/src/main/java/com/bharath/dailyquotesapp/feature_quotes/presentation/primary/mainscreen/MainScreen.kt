package com.bharath.dailyquotesapp.feature_quotes.presentation.primary.mainscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bharath.dailyquotesapp.feature_quotes.presentation.navigation.MyBottomNavigationBar
import com.bharath.dailyquotesapp.feature_quotes.presentation.navigation.MyNavHost
import com.bharath.dailyquotesapp.feature_quotes.presentation.navigation.Screens
import ir.kaaveh.sdpcompose.sdp

@Composable
fun MainScreen(
    navHostController: NavHostController,
    onclickBackButton: () -> Unit,
    onClickSearchIcon: () -> Unit,

    ) {

    val mainViewModel = hiltViewModel<MainViewModel>()
    val currentScreen by navHostController.currentBackStackEntryAsState()

    val currentRoute = currentScreen?.destination?.route

    LaunchedEffect(key1 = true, block = {
        mainViewModel.getIds()
    })

    val doNotShowTopBarSet = hashSetOf<String>(
        Screens.QuoteDetailScreen.route,
        Screens.SearchScreen.route
    )
    Scaffold(
        topBar = {
            if (!doNotShowTopBarSet.contains(currentRoute)) {
                TopBar(
                    onClickSearchIcon = {
                        onClickSearchIcon()
                    }, onclickBackButton = {
                        onclickBackButton()
                    }
                )
            }
        },
        bottomBar = {
            MyBottomNavigationBar(navHostController = navHostController)
        }
    ) { padd ->
        MyNavHost(
            navHostController = navHostController,
            starDestination = Screens.AllQuotes.route,
            paddingValues = padd,
            mainViewModel = mainViewModel
        )


    }

}

@Composable
private fun TopBar(
    onclickBackButton: () -> Unit,
    onClickSearchIcon: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 14.sdp)
            .height(40.sdp)
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        IconButton(onClick = { onclickBackButton() }) {
            Icon(imageVector = Icons.Outlined.ArrowBackIosNew, contentDescription = null)
        }

        AnimatedVisibility(visible = false) {

            IconButton(onClick = { onClickSearchIcon() }) {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
            }
        }

    }

}

