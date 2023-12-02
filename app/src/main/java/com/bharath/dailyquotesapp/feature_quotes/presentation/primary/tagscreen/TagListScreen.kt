package com.bharath.dailyquotesapp.feature_quotes.presentation.primary.tagscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.TagItem
import com.bharath.dailyquotesapp.feature_quotes.presentation.navigation.Screens
import com.bharath.dailyquotesapp.feature_quotes.presentation.primary.mainscreen.MainViewModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun TagListScreen(
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
) {
    val tagListViewModel = hiltViewModel<TagListViewModel>()
    Content(tagListViewModel = tagListViewModel, paddingValues, mainViewModel, navHostController)

}

@Composable
private fun Content(

    tagListViewModel: TagListViewModel,
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
) {

    val tags by tagListViewModel.tagsList.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true, block = {
        tagListViewModel.getAllTags()
    })
    if (tags.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (tags.error.isNotBlank()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = tags.error)
        }
    } else {

        val tagsList = tags.tagList




        LazyColumn(
            content = {
                itemsIndexed(tagsList) { ind, it ->
                    TagListItem(tagItem = it) {
                        mainViewModel.updateQuoteItemForDetailScreen(it)
                        navHostController.navigate(Screens.QuoteDetailScreen.route)
                    }
                }
            }, modifier = Modifier.padding(paddingValues)
        )


    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagListItem(
    tagItem: TagItem,
    onClick: () -> Unit,

    ) {

    Card(
        onClick = {
                  onClick()
        }, modifier = Modifier.padding(10.sdp), colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer .copy(0.6f)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(12.sdp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {
            Text(
                text = tagItem.name,
                fontWeight = FontWeight.Bold,
                fontSize = 15.ssp,
                maxLines = 1,
            )
            Text(
                text = tagItem.quoteCount.toString(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.ssp,
                minLines = 1,
            )
        }
    }
}