package me.dzikry.movapp.data.models

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("author") val author: String,
    @SerializedName("author_details") val author_details: AuthorDetail,
    @SerializedName("content") val content: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
) {
    data class AuthorDetail(
        @SerializedName("name") val name: String,
        @SerializedName("username") val username: String,
        @SerializedName("avatar_path") val photo: String,
        @SerializedName("rating") val rating: Double,
    )

    data class GetReviewResponse(
        @SerializedName("page") val page: Int,
        @SerializedName("results") val results: List<Review>,
        @SerializedName("total_pages") val total_pages: Int,
        @SerializedName("total_results") val total_results: Int,
    )
}
