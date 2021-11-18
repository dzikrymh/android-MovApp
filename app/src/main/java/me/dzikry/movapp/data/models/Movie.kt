package me.dzikry.movapp.data.models

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("release_date") val releaseDate: String
) {
    data class GetResponseMovie(
        @SerializedName("page") val page: Int,
        @SerializedName("results") val results: List<Movie>,
        @SerializedName("total_pages") val total_pages: Int,
        @SerializedName("total_results") val total_results: Long,
    )
}
