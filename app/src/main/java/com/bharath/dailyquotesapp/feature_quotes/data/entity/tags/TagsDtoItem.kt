package com.bharath.dailyquotesapp.feature_quotes.data.entity.tags

import androidx.annotation.Keep
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.TagItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class TagsDtoItem(
    @SerialName("_id")
    val _id: String,
    @SerialName("dateAdded")
    val dateAdded: String,
    @SerialName("dateModified")
    val dateModified: String,
    @SerialName("name")
    val name: String,
    @SerialName("quoteCount")
    val quoteCount: Int,
    @SerialName("slug")
    val slug: String,
)

fun TagsDtoItem.toDomain(): TagItem {
    return TagItem(
        _id, name, quoteCount, slug
    )
}