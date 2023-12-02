package com.bharath.dailyquotesapp.feature_quotes.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.toAuthorItem
import com.bharath.dailyquotesapp.feature_quotes.presentation.primary.allquotes.CardQuote
import com.bharath.dailyquotesapp.feature_quotes.presentation.primary.authors.AuthorsCard
import com.bharath.dailyquotesapp.feature_quotes.presentation.primary.homescreen.components.QuoteCard
import ir.kaaveh.sdpcompose.sdp


@Composable
fun SearchScreen(

    navHostController: NavHostController,
) {
    val viewModel = hiltViewModel<SearchViewModel>()
    Content(searchViewModel = viewModel, navHostController = navHostController)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    searchViewModel: SearchViewModel,
    navHostController: NavHostController,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        val lifecycle = LocalLifecycleOwner.current.lifecycle
        Spacer(modifier = Modifier.height(5.sdp))
        var active by remember {
            mutableStateOf(false)
        }


        val searchQuery by searchViewModel.searchText.collectAsStateWithLifecycle(lifecycle)
        val mod = Modifier
            .padding(horizontal = 15.sdp, vertical = 20.sdp)
            .fillMaxWidth()
            .height(250.sdp)


        SearchBar(query = searchQuery,
            onQueryChange = {
            searchViewModel.emitSearchText(it)
        }, onSearch = {
            searchViewModel.searchQuotes()
        }, active = active, onActiveChange = {
            active = it
        }) {


            val items = searchViewModel.quotesFlow.collectAsLazyPagingItems()

            LazyColumn(content = {
                items(items){
                    it?.let {
                    CardQuote(quoteItem =it , modifier =mod ) {

                    }

                    }
                }
            })

        }
    }
}