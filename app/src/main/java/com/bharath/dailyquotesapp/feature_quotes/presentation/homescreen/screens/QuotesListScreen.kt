package com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem
import com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.HomeViewModel
import com.bharath.dailyquotesapp.ui.theme.roboto
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp


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


    val quotes = viewModel.listOfQuotes.collectAsLazyPagingItems()


    Column(
        modifier = Modifier.fillMaxSize(),

        ) {


        if (quotes.loadState.refresh is LoadState.Loading) {

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

        } else {
            LazyColumn(content = {

                items(quotes) {
                    CardQuote(quoteItem = it!!)
                }
                item {
                    if (quotes.loadState.append is LoadState.Loading) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }


            })
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardQuote(
    quoteItem: QuoteItem,
) {
    Card(
        onClick = { /*TODO*/ }, modifier = Modifier
            .padding(horizontal = 15.sdp, vertical = 20.sdp)
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = quoteItem.content,
                fontSize = 18.ssp,
                fontFamily = roboto,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(5.sdp))
            Text(
                text = quoteItem.author,
                fontSize = 12.ssp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                fontFamily = roboto,
                fontWeight = FontWeight.Normal
            )

        }
    }
}