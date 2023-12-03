package com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(
    tableName = "SavedQuotes"
)
@Serializable
data class SavedQuoteEntity(
    @SerialName("id")
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @SerialName("idOfQuote")
    val idOfQuote: String = "",

    @SerialName("author")
    val author: String = "",


    @SerialName("content")
    val content: String = "",


    @SerialName("tags")

    val tags: List<String> = emptyList(),
    @SerialName("dateModified")
    val dateModified: String = "",


    )


class StringListConverter {
    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toList(string: String): List<String> {
        return string.split(",")
    }

}


fun SavedQuoteEntity.toQuoteItem(
    lightcolor: Color,
    darkColor: Color,
    isSaved: Boolean = false,
): QuoteItem {
    return QuoteItem(
        _id = idOfQuote, author, content, dateModified, tags, isSaved, lightcolor, darkColor
    )
}