package me.dzikry.movapp.ui.detail_movie

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import me.dzikry.movapp.data.models.DetailMovie
import me.dzikry.movapp.data.models.Genre
import me.dzikry.movapp.data.models.Review
import me.dzikry.movapp.databinding.ActivityDetailMovieBinding
import me.dzikry.movapp.ui.detail_movie.adapter.GenreAdapter
import me.dzikry.movapp.ui.detail_movie.adapter.ReviewAdapter
import me.dzikry.movapp.ui.filter_by_genre.GenreMovieActivity
import me.dzikry.movapp.utils.Tools
import java.lang.Exception
import java.text.SimpleDateFormat

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var viewModel: DetailMovieViewModel

    private lateinit var reviewMoviesAdapter: ReviewAdapter
    private lateinit var reviewMoviesLayoutMgr: LinearLayoutManager

    private lateinit var genreAdapter: GenreAdapter
    private lateinit var genreLayoutMgr: LinearLayoutManager

    private var reviewMoviesPage = 1
    private var movie_id: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailMovieViewModel::class.java)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras

        if (extras != null) {
            populateMovie(extras)
        } else {
            finish()
        }

        // review
        reviewMoviesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.reviewMovies.layoutManager = reviewMoviesLayoutMgr
        reviewMoviesAdapter = ReviewAdapter(mutableListOf()) { review -> showReviewDetails(review) }
        binding.reviewMovies.adapter = reviewMoviesAdapter

        // genre
        genreLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.genreMovie.layoutManager = genreLayoutMgr
        genreAdapter = GenreAdapter(mutableListOf()) { genre -> showMovieByGenre(genre) }
        binding.genreMovie.adapter = genreAdapter
    }

    private fun populateMovie(extras: Bundle) {
        extras.getLong(Tools.MOVIE_ID)?.let { movie_id ->
            this.movie_id = movie_id
            getDetailMovie(movie_id)
            getReviewMovies(movie_id, reviewMoviesPage)
        }
    }

    private fun getDetailMovie(movie_id: Long) {
        // get detail movie
        viewModel.getLiveDataDetailMovie().observe(this, Observer {
            if (it != null) {
                stateUI(it)
            } else {
                Toast.makeText(this, "Error get data movie", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.getApiDetailMovie(movie_id.toString())

        // get link trailer
        binding.movieTrailer.setOnClickListener {
            binding.movieTrailer.visibility = View.GONE

            viewModel.getLiveDataTrailerLink().observe(this, Observer {
                if (it != null) {
                    val url = Tools.BASE_PATH_TRAILER + it.results[0].key
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Error get data trailer", Toast.LENGTH_SHORT).show()
                }
                binding.movieTrailer.visibility = View.VISIBLE
            })
            viewModel.getApiTrailerLink(movie_id.toString())
        }
    }

    private fun getReviewMovies(movie_id: Long, page: Int) {
        viewModel.getLiveDataReviewMovie().observe(this, Observer {
            if (it != null) {
                reviewMoviesAdapter.appendReviews(it.results)
                attachReviewMoviesOnScrollListener()
            } else {
                Toast.makeText(this, "Error get data movie", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.getApiReviewMovie(movie_id.toString(), page)
    }

    private fun attachReviewMoviesOnScrollListener() {
        binding.reviewMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    val visibleItemCount = reviewMoviesLayoutMgr.childCount
                    val totalItemCount = reviewMoviesLayoutMgr.itemCount
                    val pastVisibleItems = reviewMoviesLayoutMgr.findFirstVisibleItemPosition()

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        binding.reviewMovies.removeOnScrollListener(this)
                        reviewMoviesPage++
                        getReviewMovies(movie_id, reviewMoviesPage)
                    }
                }
            }
        })
    }

    private fun stateUI(it: DetailMovie) {
        Glide.with(this)
            .load(Tools.BASE_PATH_BACKDROP + it.backdropPath)
            .transform(CenterCrop())
            .into(binding.movieBackdrop)

        Glide.with(this)
            .load(Tools.BASE_PATH_POSTER + it.posterPath)
            .transform(CenterCrop())
            .into(binding.moviePoster)

        binding.movieTitle.text = it.title
        binding.movieRating.rating = it.rating / 2
        try {
            val parser = SimpleDateFormat("yyyy-MM-dd")
            val formatter = SimpleDateFormat("dd MMMM yyyy")
            val dt = formatter.format(parser.parse(it.releaseDate))
            binding.movieReleaseDate.text = dt
        } catch (e: Exception) {
            binding.movieReleaseDate.text = it.releaseDate
        }
        binding.movieOverview.text = it.overview
        genreAdapter.appendGenres(it.genres)
    }

    private fun showMovieByGenre(genre: Genre) {
        val intent = Intent(this, GenreMovieActivity::class.java)
        intent.putExtra(Tools.GENRE_ID, genre.id)
        intent.putExtra(Tools.GENRE_NAME, genre.name)
        startActivity(intent)
    }

    private fun showReviewDetails(review: Review) {
        Toast.makeText(this, "\uD83D\uDE3B", Toast.LENGTH_SHORT).show()
    }
}