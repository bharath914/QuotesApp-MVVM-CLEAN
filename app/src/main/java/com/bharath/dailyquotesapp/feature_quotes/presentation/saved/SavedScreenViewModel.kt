package com.bharath.dailyquotesapp.feature_quotes.presentation.saved

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.SavedQuoteEntity
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.toSavedEntity
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.local.DeleteFromSavedQuotesUseCase
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.local.GetAllSavedQuotesUseCase
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.local.InsertIntoSavedQuotesUseCase
import com.bharath.dailyquotesapp.feature_quotes.presentation.saved.events.SavedScreenEvents
import com.bharath.dailyquotesapp.ui.theme.dark_colors
import com.bharath.dailyquotesapp.ui.theme.light_colors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedScreenViewModel @Inject constructor(

    private val deleteUseCase: DeleteFromSavedQuotesUseCase,
    private val insertUseCase: InsertIntoSavedQuotesUseCase,
    private val getAllSavedUseCase: GetAllSavedQuotesUseCase,

    ) : ViewModel() {

    private val _savedItems = MutableStateFlow(emptyList<SavedQuoteEntity>())
    val savedItems = _savedItems.asStateFlow()


    fun getAllQuotes() {


        val light = light_colors.shuffled()
        val dark = dark_colors.shuffled()
        getAllSavedUseCase().onEach { flow ->
            flow.collectLatest {

                _savedItems.tryEmit(
                    it.mapIndexed { index, savedQuoteEntity ->
                        savedQuoteEntity
                    }
                )
            }
        }.launchIn(viewModelScope)


    }


    private var recentlyDeleted: SavedQuoteEntity? = null
    fun onEvent(events: SavedScreenEvents) {
        when (events) {
            is SavedScreenEvents.clickOnDelete -> {

                recentlyDeleted = events.quoteEntity.toSavedEntity()
                viewModelScope.launch(IO) {
                    Log.d(
                        "Saved",
                        "onEvent: ${events.quoteEntity._id}  and ${events.quoteEntity.content}"
                    )
                    deleteUseCase(events.quoteEntity.toSavedEntity())
                }
            }

            is SavedScreenEvents.undoDelete -> {
                recentlyDeleted?.let {


                    viewModelScope.launch(IO) {
                        insertUseCase(it)
                    }
                }
            }
        }
    }


    fun delete(saved: SavedQuoteEntity) {
        viewModelScope.launch(IO) {
            deleteUseCase(saved)
        }
    }

}