package com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.screens.QuotesListScreen
import com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.screens.RandomQuoteScreen
import com.bharath.dailyquotesapp.feature_quotes.presentation.navigation.Screens
import ir.kaaveh.sdpcompose.sdp


@Composable
fun HomeScreen(
    navHostController: NavHostController,

    ) {

    val homeViewModel = hiltViewModel<HomeViewModel>()
    HomeContent(homeViewModel = homeViewModel, navHostController)


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeContent(
    homeViewModel: HomeViewModel,
    navHostController: NavHostController,
) {


    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        2

    }

    val context = LocalContext.current
    LaunchedEffect(key1 = true, block = {
        homeViewModel.getIds()
        homeViewModel.manageDailyQuote(context)
    })


    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerContent = {
            DrawerContent {
                navHostController.navigate(Screens.Saved.route)
            }
        },
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,


        ) {
        val scope = rememberCoroutineScope()


        val showDrawer = homeViewModel.showNavBar.collectAsStateWithLifecycle(initialValue = false)


        //Totally Not Recommended Need to fix asap
        if (showDrawer.value) {


            LaunchedEffect(key1 = true) {


                drawerState.open()


                homeViewModel.setNavBar(showDrawer.value.not())

            }
        }


        Scaffold(

        ) { padd ->


            Column(
                modifier = Modifier
                    .padding(padd)
                    .fillMaxSize()
            ) {


                HorizontalPager(state = pagerState) {

                    when (it) {
                        0 -> {
                            RandomQuoteScreen(homeViewModel = homeViewModel)
                        }

                        1 -> {
                            QuotesListScreen(homeViewModel = homeViewModel)
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun DrawerContent(
    onClickOnSave: () -> Unit,

    ) {

    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .padding(vertical = 20.sdp, horizontal = 10.sdp)
                .fillMaxSize()

        ) {


            NavigationDrawerItem(label = {
                Text(text = "Saved", fontWeight = FontWeight.Bold)
            }, selected = true, onClick = {
                onClickOnSave()
            }, icon = {
                Icon(
                    imageVector = Icons.Rounded.Bookmark,
                    contentDescription = null
                )
            }
            )
        }

    }
}