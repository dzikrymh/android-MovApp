package me.dzikry.movapp.ui.filter_by_genre

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import me.dzikry.movapp.data.models.Movie
import me.dzikry.movapp.databinding.ActivityGenreMovieBinding
import me.dzikry.movapp.ui.detail_movie.DetailMovieActivity
import me.dzikry.movapp.ui.filter_by_genre.adapter.MoviesAdapter
import me.dzikry.movapp.utils.Tools

class GenreMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGenreMovieBinding
    private lateinit var viewModel: GenreMovieViewModel

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var moviesLayoutMgr: StaggeredGridLayoutManager

    private var moviesPage = 1
    private lateinit var genre_id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenreMovieBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(GenreMovieViewModel::class.java)
        setContentView(binding.root)

        moviesLayoutMgr = StaggeredGridLayoutManager(
            Tools.getGridSpanCount(this),
            StaggeredGridLayoutManager.VERTICAL
        )
        binding.discoverMovies.layoutManager = moviesLayoutMgr
        moviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        binding.discoverMovies.adapter = moviesAdapter

        val extras = intent.extras

        if (extras != null) {
            populateGenre(extras)
        } else {
            finish()
        }
    }

    private fun populateGenre(extras: Bundle) {
        extras.getString(Tools.GENRE_NAME)?.let { genre_name ->
            binding.genreMovie.text = genre_name
        }
        extras.getInt(Tools.GENRE_ID)?.let { genre_id ->
            this.genre_id = genre_id.toString()
            getMovies(moviesPage, genre_id.toString())
        }
    }

    private fun getMovies(moviesPage: Int, genre_id: String) {
        viewModel.getLiveDataMovie().observe(this, Observer {
            if (it != null) {
                moviesAdapter.appendMovies(it.results)
                attachMoviesOnScrollListener()
            } else {
                Toast.makeText(this, "Error get data movie", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.getApiMoviesByGenre(genre_id, moviesPage)
    }

    var firstVisibleItem: IntArray? = null
    var pastVisibleItems: Int = 0
    private fun attachMoviesOnScrollListener() {
        binding.discoverMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = moviesLayoutMgr.itemCount
                val visibleItemCount = moviesLayoutMgr.childCount
                firstVisibleItem = moviesLayoutMgr.findFirstVisibleItemPositions(firstVisibleItem)
                if (firstVisibleItem != null && firstVisibleItem!!.size > 0) {
                    pastVisibleItems = firstVisibleItem!![0]
                }

                if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                    binding.discoverMovies.removeOnScrollListener(this)
                    moviesPage++
                    getMovies(moviesPage, genre_id)
                }
            }
        })
    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra(Tools.MOVIE_ID, movie.id)
        startActivity(intent)
    }
}