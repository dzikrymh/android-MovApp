package me.dzikry.movapp.data.models

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
) {
    data class GetResponseGenre(
        @SerializedName("genres") val genres: List<Genre>
    )
}