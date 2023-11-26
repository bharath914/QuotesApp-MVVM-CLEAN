package com.bharath.dailyquotesapp.feature_quotes.domain.entity

import com.bharath.dailyquotesapp.feature_quotes.data.entity.ResultAuthor


data class AuthorItem(
    val _id: String ="",
    val bio: String ="",
    val dateAdded: String="",
    val dateModified: String="",
    val description: String="",
    val link: String="",
    val name: String="",
    val quoteCount: Int=0,
    val slug: String="",
)

fun ResultAuthor.toAuthorItem(): AuthorItem {
    return AuthorItem(
        _id, bio, dateAdded, dateModified, description, link, name, quoteCount, slug
    )
}