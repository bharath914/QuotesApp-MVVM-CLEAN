package com.bharath.dailyquotesapp.feature_quotes.domain.usecases

import com.bharath.dailyquotesapp.feature_quotes.data.entity.tags.toDomain
import com.bharath.dailyquotesapp.feature_quotes.data.other.Resource
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.TagItem
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.toQuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.Repository
import com.bharath.dailyquotesapp.ui.theme.dark_colors
import com.bharath.dailyquotesapp.ui.theme.light_colors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TagListUseCases @Inject constructor(
    private val repository: Repository,
) {

    operator fun invoke(): Flow<Resource<List<TagItem>>> = flow {
        try {
            emit(Resource.Loading())
            val res = repository.getAllTags()
            emit(Resource.Success(data = res.map {
                it.toDomain()
            }))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error!"))
            e.printStackTrace()
        }
    }
}


class GetQuotesByTag @Inject constructor(
    private val repository: Repository,
) {
    operator fun invoke(tag: String): Flow<Resource<List<QuoteItem>>> = flow {
        try {
            emit(Resource.Loading())
            val lightColor = light_colors.shuffled()
            val darkColor = dark_colors.shuffled()

            val res = repository.getQuotesByTag(tag)
            emit(Resource.Success(data = res.results.mapIndexed { index, result ->
                result.toQuoteItem(lightColor[index], darkColor[index])
            }))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "UnexpectedErro"))
            e.printStackTrace()
        }
    }
}