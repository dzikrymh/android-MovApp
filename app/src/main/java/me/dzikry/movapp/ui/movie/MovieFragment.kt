package me.dzikry.movapp.ui.movie

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.dzikry.movapp.data.models.Genre
import me.dzikry.movapp.data.models.Movie
import me.dzikry.movapp.databinding.FragmentMovieBinding
import me.dzikry.movapp.ui.detail_movie.DetailMovieActivity
import me.dzikry.movapp.ui.filter_by_genre.GenreMovieActivity
import me.dzikry.movapp.ui.movie.adapter.GenreAdapter
import me.dzikry.movapp.ui.movie.adapter.MoviesAdapter
import me.dzikry.movapp.utils.Tools

class MovieFragment : Fragment() {

    companion object {
        fun newInstance() = MovieFragment()
    }

    private lateinit var binding: FragmentMovieBinding
    private lateinit var viewModel: MovieViewModel

    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var popularMoviesLayoutMgr: LinearLayoutManager

    private var popularMoviesPage = 1

    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var topRatedMoviesLayoutMgr: LinearLayoutManager

    private var topRatedMoviesPage = 1

    private lateinit var upcomingMoviesAdapter: MoviesAdapter
    private lateinit var upcomingMoviesLayoutMgr: LinearLayoutManager

    private var upcomingMoviesPage = 1

    private lateinit var genreAdapter: GenreAdapter
    private lateinit var genreLayoutMgr: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        // popular
        popularMoviesLayoutMgr = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.popularMovies.layoutManager = popularMoviesLayoutMgr
        popularMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        binding.popularMovies.adapter = popularMoviesAdapter

        // top rated
        topRatedMoviesLayoutMgr = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.topRatedMovies.layoutManager = topRatedMoviesLayoutMgr
        topRatedMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        binding.topRatedMovies.adapter = topRatedMoviesAdapter

        // upcoming
        upcomingMoviesLayoutMgr = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.upcomingMovies.layoutManager = upcomingMoviesLayoutMgr
        upcomingMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        binding.upcomingMovies.adapter = upcomingMoviesAdapter

        // genre
        genreLayoutMgr = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.genreMovie.layoutManager = genreLayoutMgr
        genreAdapter = GenreAdapter(mutableListOf()) { genre -> showMovieByGenre(genre) }
        binding.genreMovie.adapter = genreAdapter

        getPopularMovies(popularMoviesPage)
        getTopRatedMovies(topRatedMoviesPage)
        getUpcomingMovies(upcomingMoviesPage)
        getGenreMovies()
    }

    private fun getGenreMovies() {
        viewModel.getLiveDataGenre().observe(viewLifecycleOwner, {
            if (it != null) {
                genreAdapter.appendGenres(it.genres)
            } else {
                Toast.makeText(context, "Error get data genre", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.getApiGenreList()
    }

    private fun getPopularMovies(page: Int) {
        viewModel.getLiveDataPopular().observe(viewLifecycleOwner, {
            if (it != null) {
                popularMoviesAdapter.appendMovies(it.results)
                attachPopularMoviesOnScrollListener()
            } else {
                Toast.makeText(context, "Error get data movie", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.getApiMoviesPopular(page)
    }

    private fun attachPopularMoviesOnScrollListener() {
        binding.popularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dx > 0) { //check for scroll down
                    val visibleItemCount = popularMoviesLayoutMgr.childCount
                    val totalItemCount = popularMoviesLayoutMgr.itemCount
                    val pastVisibleItems = popularMoviesLayoutMgr.findFirstVisibleItemPosition()

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        binding.popularMovies.removeOnScrollListener(this)
                        popularMoviesPage++
                        getPopularMovies(popularMoviesPage)
                    }
                }
            }
        })
    }

    private fun getTopRatedMovies(page: Int) {
        viewModel.getLiveDataTopRated().observe(viewLifecycleOwner, {
            if (it != null) {
                topRatedMoviesAdapter.appendMovies(it.results)
                attachTopRatedMoviesOnScrollListener()
            } else {
                Toast.makeText(context, "Error get data movie", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.getApiMoviesTopRated(page)
    }

    private fun attachTopRatedMoviesOnScrollListener() {
        binding.topRatedMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dx > 0) { //check for scroll down
                    val visibleItemCount = topRatedMoviesLayoutMgr.childCount
                    val totalItemCount = topRatedMoviesLayoutMgr.itemCount
                    val pastVisibleItems = topRatedMoviesLayoutMgr.findFirstVisibleItemPosition()

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        binding.topRatedMovies.removeOnScrollListener(this)
                        topRatedMoviesPage++
                        getTopRatedMovies(topRatedMoviesPage)
                    }
                }
            }
        })
    }

    private fun getUpcomingMovies(page: Int) {
        viewModel.getLiveDataUpcoming().observe(viewLifecycleOwner, {
            if (it != null) {
                upcomingMoviesAdapter.appendMovies(it.results)
                attachUpcomingMoviesOnScrollListener()
            } else {
                Toast.makeText(context, "Error get data movie", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.getApiMoviesUpcoming(page)
    }

    private fun attachUpcomingMoviesOnScrollListener() {
        binding.upcomingMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dx > 0) { //check for scroll down
                    val visibleItemCount = upcomingMoviesLayoutMgr.childCount
                    val totalItemCount = upcomingMoviesLayoutMgr.itemCount
                    val pastVisibleItems = upcomingMoviesLayoutMgr.findFirstVisibleItemPosition()

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        binding.upcomingMovies.removeOnScrollListener(this)
                        upcomingMoviesPage++
                        getUpcomingMovies(upcomingMoviesPage)
                    }
                }
            }
        })
    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(context, DetailMovieActivity::class.java)
        intent.putExtra(Tools.MOVIE_ID, movie.id)
        startActivity(intent)
    }

    private fun showMovieByGenre(genre: Genre) {
        val intent = Intent(context, GenreMovieActivity::class.java)
        intent.putExtra(Tools.GENRE_ID, genre.id)
        intent.putExtra(Tools.GENRE_NAME, genre.name)
        startActivity(intent)
    }

}