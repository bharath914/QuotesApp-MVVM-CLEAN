package com.bharath.dailyquotesapp.feature_quotes.presentation.primary.tagscreen

import com.bharath.dailyquotesapp.feature_quotes.domain.entity.TagItem

data class TagsListState(
    val isLoading: Boolean = false,
    val tagList: List<TagItem> = emptyList(),
    val error: String = "",
)
