package me.dzikry.movapp.data.network

import me.dzikry.movapp.data.models.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIs {
    @GET("movie/popular")
    fun getPopularMovie(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
    ): Call<Movie.GetResponseMovie>

    @GET("movie/top_rated")
    fun getTopRatedMovie(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
    ): Call<Movie.GetResponseMovie>

    @GET("movie/upcoming")
    fun getUpcomingMovie(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
    ): Call<Movie.GetResponseMovie>

    @GET("genre/movie/list")
    fun getGenreMovie(
        @Query("api_key") api_key: String,
    ): Call<Genre.GetResponseGenre>

    @GET("discover/movie")
    fun getMovieByGenre(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
        @Query("with_genres") genre_id: String,
    ): Call<Movie.GetResponseMovie>

    @GET("movie/{movie_id}")
    fun getDetailMovie(
        @Path("movie_id") movie_id: String,
        @Query("api_key") api_key: String,
    ): Call<DetailMovie>

    @GET("movie/{movie_id}/videos")
    fun getTrailerLink(
        @Path("movie_id") movie_id: String,
        @Query("api_key") api_key: String,
    ): Call<Trailer.GetResponseTrailer>

    @GET("movie/{movie_id}/reviews")
    fun getReviewMovie(
        @Path("movie_id") movie_id: String,
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
    ): Call<Review.GetReviewResponse>

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("apiKey") api_key: String,
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): Response<Article.GetNewsResponse>

    @GET("everything")
    suspend fun getSearchNews(
        @Query("apiKey") api_key: String,
        @Query("q") keyword: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): Response<Article.GetNewsResponse>
}