package me.dzikry.movapp.utils

import android.app.Activity
import android.util.DisplayMetrics
import android.view.Display
import me.dzikry.movapp.R


class Tools {
    companion object {
        /**
         * Base url api
         */
        const val BASE_URL_MOVIE = "https://api.themoviedb.org/3/"
        const val API_KEY_MOVIE = "6f444511e0cc6abeaa64e43815365c8b"

        /**
         * Base path untuk mengambil media
         */
        const val BASE_PATH_TRAILER = "https://www.youtube.com/watch?v="
        const val BASE_PATH_POSTER = "https://image.tmdb.org/t/p/w342"
        const val BASE_PATH_BACKDROP = "https://image.tmdb.org/t/p/w1280"
        const val BASE_PATH_AVATAR = "https://secure.gravatar.com/avatar"

        /**
         * Send Data Key
         */
        const val MOVIE_ID = "extra_movie_id"
        const val GENRE_ID = "extra_genre_id"
        const val GENRE_NAME = "extra_genre_name"

        fun getGridSpanCount(activity: Activity): Int {
            val display: Display = activity.windowManager.defaultDisplay
            val displayMetrics = DisplayMetrics()
            display.getMetrics(displayMetrics)
            val screenWidth = displayMetrics.widthPixels.toFloat()
            val cellWidth = activity.resources.getDimension(R.dimen.item_movie_width)
            return Math.round(screenWidth / cellWidth)
        }
    }
}