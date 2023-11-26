package com.bharath.dailyquotesapp.feature_quotes.presentation.mainscreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
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

    ) {

    val mainViewModel = hiltViewModel<MainViewModel>()
    val currentScreen by navHostController.currentBackStackEntryAsState()

    val currentRoute = currentScreen?.destination?.route

    LaunchedEffect(key1 = true, block = {
        mainViewModel.getIds()
    })
    Scaffold(
        topBar = {
            if (currentRoute != Screens.AuthorDetailScreen.route) {
                TopBar {

                    onclickBackButton()
                }
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
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 14.sdp)
            .height(40.sdp)
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = { onclickBackButton() }) {
            Icon(imageVector = Icons.Outlined.ArrowBackIosNew, contentDescription = null)
        }

    }

}

