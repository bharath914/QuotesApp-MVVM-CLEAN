package com.bharath.dailyquotesapp.feature_quotes.presentation.primary.homescreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bharath.dailyquotesapp.feature_quotes.presentation.primary.homescreen.screens.RandomQuoteScreen
import com.bharath.dailyquotesapp.feature_quotes.presentation.primary.mainscreen.MainScreen
import com.bharath.dailyquotesapp.feature_quotes.presentation.navigation.Screens
import kotlinx.coroutines.launch


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




    Scaffold { padd ->


        Column(
            modifier = Modifier
                .padding(padd)
                .fillMaxSize()
        ) {


            val scope = rememberCoroutineScope()

            val currentScreen by navHostController.currentBackStackEntryAsState()
            val route = currentScreen?.destination?.route

            HorizontalPager(
                state = pagerState,
                userScrollEnabled = pagerState.currentPage == 0
            ) { it ->


                when (it) {
                    0 -> {

                        RandomQuoteScreen(homeViewModel = homeViewModel)
                    }

                    1 -> {
                        MainScreen(navHostController = navHostController,
                            onClickSearchIcon = {
                                route?.let { rte ->
                                    navHostController.navigate(Screens.SearchScreen.route + "$rte")
                                }
                            }, onclickBackButton = {
                                scope.launch {

                                    pagerState.animateScrollToPage(
                                        0,
                                    )
                                }
                            })
                    }
                }
            }
        }
    }

}
