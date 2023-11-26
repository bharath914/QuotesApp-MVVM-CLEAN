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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.HomeViewModel
import com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.events.HomeEvents
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun RandomQuoteScreen(
    homeViewModel: HomeViewModel,
) {


    val randomQuote = homeViewModel.randomQuote.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.sdp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                    IconButton(onClick = {
                        homeViewModel.onEvent(HomeEvents.ShowNavigationDrawer)
                    }) {
                        Icon(imageVector = Icons.Rounded.Sort, contentDescription = null)
                    }
                }

                Box(modifier = Modifier.weight(4f), contentAlignment = Alignment.Center) {
                    Text(text = "Quote Of The Day", fontSize = 18.ssp)
                }
                Box(modifier = Modifier.weight(1f))
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



                Spacer(modifier = Modifier.height(40.sdp))
                CardQuote(quoteItem = qoute, modifier = mod){}
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
