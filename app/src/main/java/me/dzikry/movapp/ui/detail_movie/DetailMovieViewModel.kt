package me.dzikry.movapp.ui.detail_movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.dzikry.movapp.data.models.DetailMovie
import me.dzikry.movapp.data.models.Review
import me.dzikry.movapp.data.models.Trailer
import me.dzikry.movapp.data.network.APIs
import me.dzikry.movapp.data.network.RetrofitInstance
import me.dzikry.movapp.utils.Tools
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMovieViewModel : ViewModel() {
    lateinit var liveDataMovie: MutableLiveData<DetailMovie?>
    lateinit var liveDataTrailer: MutableLiveData<Trailer.GetResponseTrailer?>
    lateinit var liveDataReview: MutableLiveData<Review.GetReviewResponse?>

    init {
        liveDataMovie = MutableLiveData()
        liveDataTrailer = MutableLiveData()
        liveDataReview = MutableLiveData()
    }

    fun getLiveDataDetailMovie(): MutableLiveData<DetailMovie?> {
        return liveDataMovie
    }

    fun getLiveDataTrailerLink(): MutableLiveData<Trailer.GetResponseTrailer?> {
        return liveDataTrailer
    }

    fun getLiveDataReviewMovie(): MutableLiveData<Review.GetReviewResponse?> {
        return liveDataReview
    }

    fun getApiDetailMovie(
        movie_id: String,
    ) {
        val retroInstance = RetrofitInstance.getRetrofitInstance()
        val retroService = retroInstance.create(APIs::class.java)
        val call = retroService.getDetailMovie(
            movie_id,
            Tools.API_KEY_MOVIE,
        )
        call.enqueue(object : Callback<DetailMovie> {
            override fun onFailure(call: Call<DetailMovie>, t: Throwable) {
                liveDataMovie.postValue(null)
            }

            override fun onResponse(
                call: Call<DetailMovie>,
                response: Response<DetailMovie>
            ) {
                if (response.isSuccessful) {
                    liveDataMovie.postValue(response.body())
                } else {
                    liveDataMovie.postValue(null)
                }
            }
        })
    }

    fun getApiTrailerLink(
        movie_id: String
    ) {
        val retroInstance = RetrofitInstance.getRetrofitInstance()
        val retroService = retroInstance.create(APIs::class.java)
        val call = retroService.getTrailerLink(
            movie_id,
            Tools.API_KEY_MOVIE,
        )
        call.enqueue(object : Callback<Trailer.GetResponseTrailer> {
            override fun onFailure(call: Call<Trailer.GetResponseTrailer>, t: Throwable) {
                liveDataTrailer.postValue(null)
            }

            override fun onResponse(
                call: Call<Trailer.GetResponseTrailer>,
                response: Response<Trailer.GetResponseTrailer>
            ) {
                if (response.isSuccessful) {
                    liveDataTrailer.postValue(response.body())
                } else {
                    liveDataTrailer.postValue(null)
                }
            }
        })
    }

    fun getApiReviewMovie(
        movie_id: String,
        page: Int,
    ) {
        val retroInstance = RetrofitInstance.getRetrofitInstance()
        val retroService = retroInstance.create(APIs::class.java)
        val call = retroService.getReviewMovie(
            movie_id,
            Tools.API_KEY_MOVIE,
            page,
        )
        call.enqueue(object : Callback<Review.GetReviewResponse> {
            override fun onFailure(call: Call<Review.GetReviewResponse>, t: Throwable) {
                liveDataReview.postValue(null)
            }

            override fun onResponse(
                call: Call<Review.GetReviewResponse>,
                response: Response<Review.GetReviewResponse>
            ) {
                if (response.isSuccessful) {
                    liveDataReview.postValue(response.body())
                } else {
                    liveDataReview.postValue(null)
                }
            }
        })
    }
}