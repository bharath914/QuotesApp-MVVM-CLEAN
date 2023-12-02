package com.bharath.dailyquotesapp.feature_quotes.presentation.primary.mainscreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.AuthorItem
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.TagItem
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.local.GetAllSavedQuoteIdsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject


data class QuotesDetail(
    val authorItem: AuthorItem? = null,
    val tagItem: TagItem? = null,
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllSavedQuoteIdsUseCase: GetAllSavedQuoteIdsUseCase,
) : ViewModel() {

    private val _quoteDetailItem = MutableStateFlow(QuotesDetail())
    val quoteDetailItem = _quoteDetailItem.asStateFlow()
    fun updateAuthorItem(authorItem: AuthorItem) {
        _quoteDetailItem.update {
            QuotesDetail(
                authorItem = authorItem
            )
        }
    }

    fun updateQuoteItemForDetailScreen(item: TagItem) {
        _quoteDetailItem.update {
            QuotesDetail(
                tagItem = item
            )
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