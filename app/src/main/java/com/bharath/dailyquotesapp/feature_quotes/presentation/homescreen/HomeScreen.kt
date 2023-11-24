package com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.screens.QuotesListScreen
import com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.screens.RandomQuoteScreen


@Composable
fun HomeScreen(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
) {

    val homeViewModel = hiltViewModel<HomeViewModel>()
    HomeContent(homeViewModel = homeViewModel)


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeContent(
    homeViewModel: HomeViewModel,
) {


    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        2

    }

    Column(
        modifier = Modifier.fillMaxSize()
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