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
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.SavedQuoteEntity
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.toQuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.toSavedEntity
import com.bharath.dailyquotesapp.feature_quotes.presentation.primary.allquotes.CardQuote
import ir.kaaveh.sdpcompose.sdp

@Composable
fun SavedScreen(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
) {

    val viewModel = hiltViewModel<SavedScreenViewModel>()
    LaunchedEffect(key1 = true, block = {
        viewModel.getAllQuotes()
    })
    Content(viewModel = viewModel, paddingValues)


}

@Immutable
data class ImmutableListWrapperSaved(val list: List<SavedQuoteEntity>)

@Composable
private fun Content(
    viewModel: SavedScreenViewModel,
    paddingValues: PaddingValues,
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
            val list = ImmutableListWrapperSaved(savedItems.value)
            val onDelete: (value: SavedQuoteEntity) -> Unit = remember {
                viewModel::delete
            }
            val mod = Modifier
                .padding(horizontal = 15.sdp, vertical = 20.sdp)
                .fillMaxWidth()
                .height(250.sdp)



            LazyColumn {


                items(list.list) {


                    CardQuote(
                        quoteItem = it.toQuoteItem(
                            lightcolor = Color(0xffff9990),
                            darkColor = Color(0xFF5E3434)
                        ),
                        modifier = mod, fromSavedQuotesScreen = true
                    ) {
                        onDelete(it)
                    }

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