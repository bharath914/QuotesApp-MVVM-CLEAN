package com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bharath.dailyquotesapp.feature_quotes.data.entity.QuoteItemDto
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Entity(
    tableName = "Quote"
)
@Keep
@Serializable
data class QuoteEntity(
    @SerialName("_id")
    @PrimaryKey val _id: String = "",

    @SerialName("author")
    val author: String = "",


    @SerialName("content")
    val content: String = "",

    @SerialName("dateAdded")
    val dateAdded: String = "",

    @SerialName("dateModified")
    val dateModified: String = "",

    @SerialName("length")
    val length: Int = 0,



    @SerialName("SavedDate")
    val savedDate: String = "",
)

fun QuoteItemDto.toQuoteEntity(savedDate: String): QuoteEntity {
    return QuoteEntity(
        _id, author, content, dateAdded, dateModified, length, savedDate
    )
}

fun QuoteEntity.toQuoteItem(): QuoteItem {
    return QuoteItem(
        _id, author, content, dateModified,
    )
}