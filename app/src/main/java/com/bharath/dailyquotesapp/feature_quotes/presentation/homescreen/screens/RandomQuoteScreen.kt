package com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.HomeViewModel

@Composable
fun RandomQuoteScreen(
    homeViewModel: HomeViewModel,
) {

    LaunchedEffect(key1 = true, block = {
        homeViewModel.getRandomQuote()
    })

    val randomQuote = homeViewModel.randomQuote.collectAsStateWithLifecycle()

    Column (
        modifier=Modifier.fillMaxSize()
    ){

        if (randomQuote.value.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {

            Text(text = randomQuote.value.quoteItem.content)
        }

    }
}
