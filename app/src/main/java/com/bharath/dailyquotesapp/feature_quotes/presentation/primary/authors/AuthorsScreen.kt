package com.bharath.dailyquotesapp.feature_quotes.presentation.primary.authors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.AuthorItem
import com.bharath.dailyquotesapp.feature_quotes.presentation.primary.mainscreen.MainViewModel
import com.bharath.dailyquotesapp.feature_quotes.presentation.navigation.Screens
import com.bharath.dailyquotesapp.ui.theme.roboto
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp


@Composable
fun AuthorsScreen(
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel,
    navHostController: NavHostController,

    ) {
    val viewModel = hiltViewModel<AuthorViewModel>()
    AuthorsContent(
        paddingValues = paddingValues,
        mainViewModel = mainViewModel,
        viewModel = viewModel,
        navHostController = navHostController
    )


}


@Composable
private fun AuthorsContent(
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel,
    viewModel: AuthorViewModel,
    navHostController: NavHostController,
) {

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        val authors = viewModel.authorsFlow.collectAsLazyPagingItems()

        val cardMod = Modifier
            .padding(15.sdp)
            .height(200.sdp)
            .fillMaxWidth()

        if (authors.loadState.refresh is LoadState.Loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn {

                items(authors) {
                    it?.let { authorItem ->
                        AuthorsCard(authorItem = authorItem, modifier = cardMod) {
                            mainViewModel.updateAuthorItem(authorItem)
                            navHostController.navigate(Screens.QuoteDetailScreen.route)
                        }
                    }
                }
                item {
                    if (authors.loadState.append is LoadState.Loading) {

                        CircularProgressIndicator(modifier = Modifier.padding(vertical = 20.sdp))
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorsCard(
    authorItem: AuthorItem,
    modifier: Modifier,
    onClickAuthorsCard: () -> Unit,
) {

    Card(onClick = {
        onClickAuthorsCard()
    }, modifier = modifier) {

        Column(
            modifier = Modifier
                .padding(10.sdp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = authorItem.name,
                fontFamily = roboto,
                fontWeight = FontWeight.Bold,
                fontSize = 18.ssp
            )
            Spacer(modifier = Modifier.height(10.sdp))
            Text(
                text = authorItem.bio,
                fontFamily = roboto,
                fontSize = 12.ssp,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(10.sdp))
            Text(
                text = authorItem.description,
                fontFamily = roboto,
                fontSize = 14.ssp,
                fontWeight = FontWeight.SemiBold
            )


        }

    }

}