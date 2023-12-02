package com.bharath.dailyquotesapp.feature_quotes.data.entity.tags

import com.bharath.dailyquotesapp.feature_quotes.domain.entity.TagItem
import kotlinx.serialization.Serializable

@Serializable
data class TagsDtoItem(

    val _id: String,
    val dateAdded: String,
    val dateModified: String,
    val name: String,
    val quoteCount: Int,
    val slug: String,
)

fun TagsDtoItem.toDomain(): TagItem {
    return TagItem(
        _id, name, quoteCount, slug
    )
}