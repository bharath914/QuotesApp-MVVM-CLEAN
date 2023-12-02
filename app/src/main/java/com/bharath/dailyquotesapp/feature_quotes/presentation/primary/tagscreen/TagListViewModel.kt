package com.bharath.dailyquotesapp.feature_quotes.presentation.primary.tagscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharath.dailyquotesapp.feature_quotes.data.other.Resource
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.TagListUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TagListViewModel @Inject constructor(
    private val tagListUseCases: TagListUseCases,
) : ViewModel() {


    private val _tagsList = MutableStateFlow(TagsListState())
    val tagsList = _tagsList.asStateFlow()


    fun getAllTags() {
        tagListUseCases().onEach { result ->

            when (result) {
                is Resource.Success -> {

                    _tagsList.tryEmit(
                        TagsListState(
                            tagList = result.data ?: emptyList()
                        )

                    )

                }

                is Resource.Loading -> {
                    _tagsList.tryEmit(
                        TagsListState(
                            isLoading = true
                        )
                    )
                }

                else -> {
                    _tagsList.tryEmit(
                        TagsListState(
                            error = result.message ?: "Unexpected Error!"
                        )
                    )
                }
            }

        }.launchIn(viewModelScope)
    }
}