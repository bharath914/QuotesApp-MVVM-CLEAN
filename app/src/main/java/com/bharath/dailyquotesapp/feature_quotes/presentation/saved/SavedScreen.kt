package com.bharath.dailyquotesapp.feature_quotes.presentation.saved

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.toQuoteItem
import com.bharath.dailyquotesapp.feature_quotes.presentation.allquotes.CardQuote
import com.bharath.dailyquotesapp.feature_quotes.presentation.saved.events.SavedScreenEvents
import com.bharath.dailyquotesapp.ui.theme.dark_colors
import com.bharath.dailyquotesapp.ui.theme.light_colors
import ir.kaaveh.sdpcompose.sdp

@Composable
fun SavedScreen(
    navHostController: NavHostController,
    paddingValues: PaddingValues
) {

    val viewModel = hiltViewModel<SavedScreenViewModel>()
    LaunchedEffect(key1 = true, block = {
        viewModel.getAllQuotes()
    })
    Content(viewModel = viewModel,paddingValues)


}

@Composable
private fun Content(
    viewModel: SavedScreenViewModel,
    paddingValues: PaddingValues
) {

    Scaffold(

        modifier = Modifier.padding(paddingValues)
    ) { padd ->
        Column(
            modifier = Modifier
                .padding(padd)
                .fillMaxSize()
        ) {
            val savedItems = viewModel.savedItems.collectAsStateWithLifecycle()
            val mod = Modifier
                .padding(horizontal = 15.sdp, vertical = 20.sdp)
                .fillMaxWidth()
                .height(250.sdp)


            val darkcolors = dark_colors.shuffled()
            val lightcolors = light_colors.shuffled()
            var idx = 0
            LazyColumn {


                items(savedItems.value) {

                    if (idx >= savedItems.value.size) {
                        idx = 0
                    }
                    CardQuote(
                        quoteItem = it.toQuoteItem(lightcolors[idx], darkcolors[idx]),
                        modifier = mod, fromSavedQuotesScreen = true
                    ) {

                        viewModel.onEvent(SavedScreenEvents.clickOnDelete(it))
                    }
                    idx++
                }

            }


        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    TopAppBar(title = { Text(text = "Saved Quotes") })
}