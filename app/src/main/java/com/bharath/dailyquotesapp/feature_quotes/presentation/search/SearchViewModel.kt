package com.bharath.dailyquotesapp.feature_quotes.presentation.search


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bharath.dailyquotesapp.feature_quotes.presentation.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(

    savedStateHandle: SavedStateHandle,

    ) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val route = mutableStateOf(Screens.AllQuotes.route)

    init {
        savedStateHandle.get<String>("route")?.let {
            route.value = it
        }
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.clickedOnEnter -> {

                if (route.value == Screens.AuthorScreen.route) {
                    // search for authors
                } else if (route.value == Screens.Saved.route) {
                    // search for saved items
                } else {
                    // search for all quotes
                }

            }
        }


    }


    fun searchAuthors() {



    }

    fun searchSavedItems() {

    }

    fun searchQuotes() {

    }


}