package com.bharath.dailyquotesapp.feature_quotes.presentation.authors.authorDetail

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.AuthorItem
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.toQuoteItemForSaveCheck
import com.bharath.dailyquotesapp.feature_quotes.presentation.allquotes.CardQuote
import com.bharath.dailyquotesapp.feature_quotes.presentation.mainscreen.MainViewModel
import com.bharath.dailyquotesapp.ui.theme.roboto
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.Dispatchers

@Composable
fun AuthorDetailScreen(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel,
) {
    val lifeCycle = LocalLifecycleOwner.current.lifecycle
//must to give the lifecycle , if didn't given it will collect even when we exit the screen
    val authorItem = mainViewModel.authorItem.collectAsStateWithLifecycle(lifecycle = lifeCycle)

    val viewModel = hiltViewModel<AuthorDetailViewModel>()


    authorItem.value.let { author ->
        LaunchedEffect(key1 = true, block = {
            viewModel.getQuotes(author.slug)
        })
        Content(
            authorItem = author, authorDetailViewModel = viewModel, paddingValues, mainViewModel
        )
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Content(
    authorItem: AuthorItem,
    authorDetailViewModel: AuthorDetailViewModel,
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel,
) {


    Scaffold(topBar = {
        TopBar(authorItem = authorItem)
    }) { innerPadd ->


        val quotes =
            authorDetailViewModel.quotesFlow?.collectAsLazyPagingItems(context = Dispatchers.IO)
        Column(
            modifier = Modifier
                .padding(innerPadd)
                .fillMaxSize()
        ) {
            val mod = Modifier
                .padding(horizontal = 15.sdp, vertical = 20.sdp)
                .fillMaxWidth()
                .height(250.sdp)

            if (quotes?.loadState?.refresh is LoadState.Loading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            LazyColumn {
                quotes?.let {
                    items(quotes) {
                        val animatedProgress = remember {
                            Animatable(initialValue = 0.35f)
                        }
                        LaunchedEffect(key1 = Unit, block = {
                            animatedProgress.animateTo(
                                targetValue = 1f, animationSpec = tween(
                                    durationMillis = 300, easing = LinearOutSlowInEasing
                                )

                            )
                        })
                        val contains = mainViewModel.set.contains(it!!._id)
                        CardQuote(
                            quoteItem = it.toQuoteItemForSaveCheck(contains),
                            modifier = mod.graphicsLayer {


                                scaleY = animatedProgress.value
                                scaleX = animatedProgress.value
                            }) {

                            authorDetailViewModel.onEvent(
                                AuthorDetailEvent.onClickFav(
                                    contains, it.toQuoteItemForSaveCheck(contains)
                                )
                            )
                        }

                    }
                    item {
                        if (quotes.loadState.append is LoadState.Loading) {

                            CircularProgressIndicator()

                        }
                    }

                }
            }


        }
    }


}

/*
Feature need to be updated
 */
@Composable
private fun TopBar(
    authorItem: AuthorItem,
) {

    Row(
        modifier = Modifier
            .padding(horizontal = 16.sdp)
            .height(50.sdp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = authorItem.name,
            fontFamily = roboto,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.ssp,
            maxLines = 1
        )
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontFamily = roboto, fontSize = 12.ssp)) {
                    append("Total : ")
                }
                append(authorItem.quoteCount.toString())
            },
            fontFamily = roboto,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.ssp,
            maxLines = 1
        )

    }


}


//
//@Composable
//fun WebPage(
//    authorItem: AuthorItem,
//) {
//
//    AndroidView(
//        factory = {
//            WebView(it).apply {
//                layoutParams = ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT
//                )
//                settings.javaScriptEnabled = true
//                settings.loadWithOverviewMode = true
//                settings.useWideViewPort = true
//                settings.builtInZoomControls = false
//                settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
//                webChromeClient = WebChromeClient()
//                loadUrl(authorItem.link)
//            }
//        },
//        modifier = Modifier
//            .background(MaterialTheme.colorScheme.background)
//            .fillMaxSize()
//    )
//
//}


// needed if we have more contents we need it
sealed class TabsContent {
//    data class AuthorDetails(val )
}