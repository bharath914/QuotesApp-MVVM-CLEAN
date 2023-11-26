package com.bharath.dailyquotesapp.feature_quotes.domain.entity

import androidx.compose.ui.graphics.Color
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.SavedQuoteEntity
import com.bharath.dailyquotesapp.feature_quotes.data.entity.QuoteItemDto
import com.bharath.dailyquotesapp.feature_quotes.data.entity.Result
import kotlinx.serialization.SerialName


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


    @SerialName("isSaved")
    val isSaved: Boolean = false,

    val color: Color = Color(0xFEB9090),
    val darkColor: Color = Color(0xEE312626),
)


fun Result.toQuoteItem(clr: Color, darkColor: Color): QuoteItem {
    return QuoteItem(
        _id, author, content, dateModified, tags, color = clr, darkColor = darkColor
    )
}

fun QuoteItemDto.toDomain(color: Color, darkColor: Color, isSaved: Boolean = false): QuoteItem {
    return QuoteItem(
        _id,
        author,
        content,
        dateModified,
        tags,
        color = color,
        darkColor = darkColor,
        isSaved = isSaved
    )
}


fun QuoteItem.toQuoteItemForSaveCheck(contains: Boolean): QuoteItem {
    return QuoteItem(
        _id,
        author,
        content,
        dateModified,
        tags,
        contains,
        color,
        darkColor

    )


}

fun QuoteItem.toSavedEntity():SavedQuoteEntity{
    return SavedQuoteEntity(
        idOfQuote = _id,
        content = content,
        author = author,
        dateModified = dateModified
    )
}
