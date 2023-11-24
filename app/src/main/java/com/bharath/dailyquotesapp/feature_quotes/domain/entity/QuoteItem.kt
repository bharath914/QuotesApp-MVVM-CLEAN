package com.bharath.dailyquotesapp.feature_quotes.domain.entity

import com.bharath.dailyquotesapp.feature_quotes.data.entity.QuoteItemDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class QuoteItem(
    @SerialName("_id")
    val _id: String = "",

    @SerialName("author")
    val author: String = "",


    @SerialName("content")
    val content: String = "",


    @SerialName("dateModified")
    val dateModified: String = "",

    @SerialName("tags")
    val tags: List<String> = emptyList(),
)


fun QuoteItemDto.toDomain(): QuoteItem {
    return QuoteItem(
        _id, author, content, dateModified, tags
    )
}