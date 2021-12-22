package me.dzikry.movapp.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.dzikry.movapp.data.models.Genre
import me.dzikry.movapp.data.models.Movie
import me.dzikry.movapp.data.network.APIs
import me.dzikry.movapp.data.network.RetrofitInstance
import me.dzikry.movapp.utils.Tools
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {
    var liveDataListPopular: MutableLiveData<Movie.GetResponseMovie?>
    var liveDataListTopRated: MutableLiveData<Movie.GetResponseMovie?>
    var liveDataListUpcoming: MutableLiveData<Movie.GetResponseMovie?>
    var liveDataListGenre: MutableLiveData<Genre.GetResponseGenre?>

    init {
        liveDataListPopular = MutableLiveData()
        liveDataListTopRated = MutableLiveData()
        liveDataListUpcoming = MutableLiveData()
        liveDataListGenre = MutableLiveData()
    }

    fun getLiveDataPopular(): MutableLiveData<Movie.GetResponseMovie?> {
        return liveDataListPopular
    }

    fun getLiveDataTopRated(): MutableLiveData<Movie.GetResponseMovie?> {
        return liveDataListTopRated
    }

    fun getLiveDataUpcoming(): MutableLiveData<Movie.GetResponseMovie?> {
        return liveDataListUpcoming
    }

    fun getLiveDataGenre(): MutableLiveData<Genre.GetResponseGenre?> {
        return liveDataListGenre
    }

    fun getApiMoviesPopular(
        page: Int = 1
    ) {
        val retroInstance = RetrofitInstance.getRetrofitInstance()
        val retroService = retroInstance.create(APIs::class.java)
        val call = retroService.getPopularMovie(
            Tools.API_KEY_MOVIE,
            page
        )
        call.enqueue(object : Callback<Movie.GetResponseMovie> {
            override fun onFailure(call: Call<Movie.GetResponseMovie>, t: Throwable) {
                liveDataListPopular.postValue(null)
            }

            override fun onResponse(
                call: Call<Movie.GetResponseMovie>,
                response: Response<Movie.GetResponseMovie>
            ) {
                if (response.isSuccessful) {
                    liveDataListPopular.postValue(response.body())
                } else {
                    liveDataListPopular.postValue(null)
                }
            }
        })
    }

    fun getApiMoviesTopRated(
        page: Int = 1
    ) {
        val retroInstance = RetrofitInstance.getRetrofitInstance()
        val retroService = retroInstance.create(APIs::class.java)
        val call = retroService.getTopRatedMovie(
            Tools.API_KEY_MOVIE,
            page
        )
        call.enqueue(object : Callback<Movie.GetResponseMovie> {
            override fun onFailure(call: Call<Movie.GetResponseMovie>, t: Throwable) {
                liveDataListTopRated.postValue(null)
            }

            override fun onResponse(
                call: Call<Movie.GetResponseMovie>,
                response: Response<Movie.GetResponseMovie>
            ) {
                if (response.isSuccessful) {
                    liveDataListTopRated.postValue(response.body())
                } else {
                    liveDataListTopRated.postValue(null)
                }
            }
        })
    }

    fun getApiMoviesUpcoming(
        page: Int = 1
    ) {
        val retroInstance = RetrofitInstance.getRetrofitInstance()
        val retroService = retroInstance.create(APIs::class.java)
        val call = retroService.getUpcomingMovie(
            Tools.API_KEY_MOVIE,
            page
        )
        call.enqueue(object : Callback<Movie.GetResponseMovie> {
            override fun onFailure(call: Call<Movie.GetResponseMovie>, t: Throwable) {
                liveDataListUpcoming.postValue(null)
            }

            override fun onResponse(
                call: Call<Movie.GetResponseMovie>,
                response: Response<Movie.GetResponseMovie>
            ) {
                if (response.isSuccessful) {
                    liveDataListUpcoming.postValue(response.body())
                } else {
                    liveDataListUpcoming.postValue(null)
                }
            }
        })
    }

    fun getApiGenreList() {
        val retroInstance = RetrofitInstance.getRetrofitInstance()
        val retroService = retroInstance.create(APIs::class.java)
        val call = retroService.getGenreMovie(
            Tools.API_KEY_MOVIE
        )
        call.enqueue(object : Callback<Genre.GetResponseGenre> {
            override fun onResponse(
                call: Call<Genre.GetResponseGenre>,
                response: Response<Genre.GetResponseGenre>
            ) {
                if (response.isSuccessful) {
                    liveDataListGenre.postValue(response.body())
                } else {
                    liveDataListGenre.postValue(null)
                }
            }

            override fun onFailure(call: Call<Genre.GetResponseGenre>, t: Throwable) {
                liveDataListGenre.postValue(null)
            }
        })
    }
}