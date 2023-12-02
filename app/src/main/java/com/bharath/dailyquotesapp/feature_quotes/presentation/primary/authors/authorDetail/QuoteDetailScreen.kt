package com.bharath.dailyquotesapp.feature_quotes.presentation.primary.authors.authorDetail

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
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
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.TagItem
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.toQuoteItemForSaveCheck
import com.bharath.dailyquotesapp.feature_quotes.presentation.primary.allquotes.CardQuote
import com.bharath.dailyquotesapp.feature_quotes.presentation.primary.mainscreen.MainViewModel
import com.bharath.dailyquotesapp.ui.theme.roboto
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO

@Composable
fun QuoteDetailScreen(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel,
) {
    val lifeCycle = LocalLifecycleOwner.current.lifecycle
//must to give the lifecycle , if didn't given it will collect even when we exit the screen
    val quoteDetailItem =
        mainViewModel.quoteDetailItem.collectAsStateWithLifecycle(
            lifecycle = lifeCycle,
            context = IO
        )

    val viewModel = hiltViewModel<QuoteDetailViewModel>()


    quoteDetailItem.value.let { quotesDetail ->
        if (quotesDetail.authorItem == null) {

            LaunchedEffect(key1 = true, block = {
                viewModel.getQuotesByTag(tag = quotesDetail.tagItem?.slug!!)
            })
            Content(
                tagItem = quotesDetail.tagItem,
                quoteDetailViewModel = viewModel,
                paddingValues = paddingValues,
                isTag = true,
                mainViewModel = mainViewModel
            )

        } else {


            LaunchedEffect(key1 = true, block = {
                viewModel.getQuotesByAuthor(quotesDetail.authorItem.slug)
            })
            Content(
                authorItem = quotesDetail.authorItem,
                quoteDetailViewModel = viewModel,
                paddingValues = paddingValues,
                mainViewModel = mainViewModel
            )
        }
    }


}



@Composable
private fun Content(
    isTag: Boolean = false,
    tagItem: TagItem? = null,
    authorItem: AuthorItem? = null,
    quoteDetailViewModel: QuoteDetailViewModel,
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel,
) {


    Scaffold(topBar = {
        if (!isTag) TopBarForAuthor(authorItem = authorItem!!) else TopBarForTag(tagItem = tagItem!!)
    }) { innerPadd ->


        val quotes =
            quoteDetailViewModel.quotesFlow?.collectAsLazyPagingItems(context = IO)
        Column(
            modifier = Modifier
                .padding(innerPadd)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
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
            val set = mainViewModel.set

            LazyColumn {
                quotes?.let {
                    items(quotes) {
                        it?.let {
                            val contains = set.contains(it._id)
                            QuoteItemHolder(it = it, mod = mod, contains = contains) {
                                quoteDetailViewModel.onEvent(
                                    AuthorDetailEvent.onClickFav(
                                        contains, it.toQuoteItemForSaveCheck(contains)
                                    )
                                )
                            }

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

@Composable
private fun QuoteItemHolder(
    it: QuoteItem,
    contains: Boolean,
    mod: Modifier,
    onClickFav: () -> Unit,
) {
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

    CardQuote(
        quoteItem = it.toQuoteItemForSaveCheck(contains),
        modifier = mod.graphicsLayer {


            scaleY = animatedProgress.value
            scaleX = animatedProgress.value
        }) {

        onClickFav()
    }

}

/*
Feature need to be updated
 */

@Composable
fun TopBarForTag(
    tagItem: TagItem,
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
            text = tagItem.name,
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
                append(tagItem.quoteCount.toString())
            },
            fontFamily = roboto,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.ssp,
            maxLines = 1
        )
    }
}


@Composable
private fun TopBarForAuthor(
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