package com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.toQuoteItemForSaveCheck
import com.bharath.dailyquotesapp.feature_quotes.presentation.allquotes.CardQuote
import com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.HomeViewModel
import com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.events.HomeEvents
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun RandomQuoteScreen(
    homeViewModel: HomeViewModel,

    ) {


    val randomQuote = homeViewModel.randomQuote.collectAsStateWithLifecycle()


    val lifecycle = LocalLifecycleOwner.current.lifecycle


    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .padding(start = 10.sdp, end = 10.sdp, top = 10.sdp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Quote Of The Day", fontSize = 18.ssp)
            }
        }
    ) {


        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            if (randomQuote.value.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                val mod = Modifier
                    .padding(horizontal = 15.sdp, vertical = 20.sdp)
                    .fillMaxWidth()
                    .height(250.sdp)
                val qoute = randomQuote.value.quoteItem


                val ids by homeViewModel.ids.collectAsStateWithLifecycle(lifecycle)
                val isSaved = ids.contains(qoute._id)
                Spacer(modifier = Modifier.height(40.sdp))
                if (randomQuote.value.quoteItem != QuoteItem()) {
                    CardQuote(quoteItem = qoute.toQuoteItemForSaveCheck(isSaved), modifier = mod) {
                        homeViewModel.onEvent(
                            HomeEvents.ClickedOnFavButton(
                                quoteItem = qoute.toQuoteItemForSaveCheck(
                                    isSaved
                                ), isSaved
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(40.sdp))
                Box(
                    modifier = Modifier
                        .padding(bottom = 40.sdp)
                        .fillMaxSize(), contentAlignment = Alignment.BottomCenter
                ) {
                    Text(text = "Swipe to View More ->")
                }
            }

        }
    }

}
