package com.bharath.dailyquotesapp.feature_quotes.data.entity

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Keep
@Serializable
data class QuoteItemDto(
    @SerialName("_id")
    val _id: String = "",

    @SerialName("author")
    val author: String = "",

    @SerialName("authorSlug")
    val authorSlug: String = "",

    @SerialName("content")
    val content: String = "",

    @SerialName("dateAdded")
    val dateAdded: String = "",

    @SerialName("dateModified")
    val dateModified: String = "",

    @SerialName("length")
    val length: Int = 0,

    @SerialName("tags")
    val tags: List<String> = emptyList(),

    )