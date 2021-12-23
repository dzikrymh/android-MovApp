package me.dzikry.movapp.ui.filter_by_genre

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.dzikry.movapp.data.models.Movie
import me.dzikry.movapp.data.network.APIs
import me.dzikry.movapp.data.network.RetrofitInstance
import me.dzikry.movapp.utils.Tools
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GenreMovieViewModel : ViewModel() {
    lateinit var liveDataListMovie: MutableLiveData<Movie.GetResponseMovie?>

    init {
        liveDataListMovie = MutableLiveData()
    }

    fun getLiveDataMovie(): MutableLiveData<Movie.GetResponseMovie?> {
        return liveDataListMovie
    }

    fun getApiMoviesByGenre(
        genre_id: String,
        page: Int = 1
    ) {
        val retroInstance = RetrofitInstance.getRetrofitMovieInstance()
        val retroService = retroInstance.create(APIs::class.java)
        val call = retroService.getMovieByGenre(
            Tools.API_KEY_MOVIE,
            page,
            genre_id
        )
        call.enqueue(object : Callback<Movie.GetResponseMovie> {
            override fun onFailure(call: Call<Movie.GetResponseMovie>, t: Throwable) {
                liveDataListMovie.postValue(null)
            }

            override fun onResponse(
                call: Call<Movie.GetResponseMovie>,
                response: Response<Movie.GetResponseMovie>
            ) {
                if (response.isSuccessful) {
                    liveDataListMovie.postValue(response.body())
                } else {
                    liveDataListMovie.postValue(null)
                }
            }
        })
    }
}