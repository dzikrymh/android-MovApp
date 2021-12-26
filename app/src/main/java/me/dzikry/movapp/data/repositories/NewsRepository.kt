package me.dzikry.movapp.data.repositories

import me.dzikry.movapp.data.models.Article
import me.dzikry.movapp.data.network.APIs
import me.dzikry.movapp.utils.Tools
import java.lang.Exception

class NewsRepository(private val api: APIs) {

    suspend fun getTopHeadlines(category: String, page: Int): Article.GetNewsResponse {
        val response = api.getTopHeadlines(
            api_key = Tools.API_KEY_NEWS,
            country = "id",
            category = category,
            language = "id",
            page = page,
            pageSize = 20,
        )
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception(response.message())
        }
    }

    suspend fun getSearchNews(keyword: String, page: Int): Article.GetNewsResponse {
        val response = api.getSearchNews(
            api_key = Tools.API_KEY_NEWS,
            keyword = keyword,
            language = "id",
            page = page,
            pageSize = 20,
        )
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception(response.message())
        }
    }
}