package com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.HomeViewModel


@Composable
fun QuotesListScreen(
    homeViewModel: HomeViewModel,
) {


    QuotesContent(viewModel = homeViewModel)
}

@Composable
private fun QuotesContent(
    viewModel: HomeViewModel,
) {


    LaunchedEffect(key1 = true) {
        viewModel.getRandomQuote()
    }

    val quotes = viewModel.listOfQuotes.collectAsLazyPagingItems()

    if (quotes.loadState.refresh is LoadState.Loading) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

    } else {
        LazyColumn(content = {

            items(quotes.itemSnapshotList) {
                Text(text = it?.content ?: "---")
            }

        })
    }

}