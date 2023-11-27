package com.bharath.dailyquotesapp.feature_quotes.presentation.mainscreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.AuthorItem
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.local.GetAllSavedQuoteIdsUseCase
import com.bharath.dailyquotesapp.feature_quotes.presentation.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllSavedQuoteIdsUseCase: GetAllSavedQuoteIdsUseCase,
) : ViewModel() {

    private val _authorItem = MutableStateFlow(AuthorItem())
    val authorItem = _authorItem.asStateFlow()
    fun updateAuthorItem(authorItem: AuthorItem) {
        _authorItem.update {
            authorItem
        }
    }



    val set: HashSet<String> = HashSet()


    fun getIds() {
        getAllSavedQuoteIdsUseCase().onEach {
            it.collectLatest { list ->
                set.addAll(list)
            }
        }.launchIn(viewModelScope)

    }

}