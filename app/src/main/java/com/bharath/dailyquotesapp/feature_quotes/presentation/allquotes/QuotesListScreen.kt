package com.bharath.dailyquotesapp.feature_quotes.presentation.allquotes

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.toQuoteItemForSaveCheck
import com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.events.HomeEvents
import com.bharath.dailyquotesapp.feature_quotes.presentation.mainscreen.MainViewModel
import com.bharath.dailyquotesapp.ui.theme.getColors
import com.bharath.dailyquotesapp.ui.theme.roboto
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp


@Composable
fun QuotesListScreen(
//    homeViewModel: HomeViewModel,
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel,
) {

    val viewModel = hiltViewModel<QuotesListViewModel>()

    QuotesContent(viewModel = viewModel, paddingValues, mainViewModel)
}

@Composable
private fun QuotesContent(
    viewModel: QuotesListViewModel,
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel,
) {


    LaunchedEffect(key1 = true, block = {
viewModel.getList()
    })

    val quotes = viewModel.listOfQuotes.collectAsLazyPagingItems()


    val colors = getColors(isSystemInDarkTheme())
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),

        ) {
        val mod = Modifier
            .padding(horizontal = 15.sdp, vertical = 20.sdp)
            .fillMaxWidth()
            .height(250.sdp)


        var index by remember {
            mutableIntStateOf(0)
        }
        if (quotes.loadState.refresh is LoadState.Loading) {

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

        } else {

            LazyColumn(content = {

                itemsIndexed(quotes) { ind, quoteItem ->
                    val animatedProgress = remember {
                        Animatable(initialValue = 0.35f)
                    }
                    LaunchedEffect(key1 = Unit, block = {
                        animatedProgress.animateTo(
                            targetValue = 1f,
                            animationSpec = tween(
                                durationMillis = 300,
                                easing = LinearOutSlowInEasing
                            )

                        )
                    })
                    quoteItem?.let {

                        val isSaved = mainViewModel.set.contains(it._id)
                        CardQuote(
                            quoteItem = it.toQuoteItemForSaveCheck(isSaved),
                            mod.graphicsLayer {
                                scaleX = animatedProgress.value
                                scaleY = animatedProgress.value
                            }
                        ) {

                            viewModel.onEvent(
                                HomeEvents.ClickedOnFavButton(
                                    quoteItem.toQuoteItemForSaveCheck(
                                        isSaved
                                    ), isSaved
                                )
                            )
                        }
                    }

                }
                item {
                    if (quotes.loadState.append is LoadState.Loading) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.padding(vertical = 20.sdp))
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
    modifier: Modifier,
    fromSavedQuotesScreen: Boolean = false,
    onclickOnfavButton: () -> Unit,
//    bg: Color,
) {

    var clickedSaved by remember {
        mutableStateOf(value = quoteItem.isSaved)
    }

    Card(
        onClick = { /*TODO*/ }, modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) quoteItem.darkColor else quoteItem.color
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.sdp, vertical = 15.sdp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "\"" + quoteItem.content + "\"",
                    fontSize = 18.ssp,
                    fontFamily = roboto,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(5.sdp))
                Text(
                    text = "~ " + quoteItem.author,
                    fontSize = 12.ssp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    fontFamily = roboto,
                    fontWeight = FontWeight.Normal
                )

            }
            Row(
                modifier = Modifier
                    .padding(5.sdp)
                    .fillMaxSize(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End

            ) {

                IconButton(onClick = {
                    clickedSaved = !clickedSaved
                    onclickOnfavButton()
                }) {
                    Icon(
                        imageVector = if (fromSavedQuotesScreen) Icons.Outlined.Delete else if (clickedSaved) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = if (clickedSaved) Color.Red.copy(alpha = 0.7f) else Color.Black
                    )
                }
            }
        }

    }
}