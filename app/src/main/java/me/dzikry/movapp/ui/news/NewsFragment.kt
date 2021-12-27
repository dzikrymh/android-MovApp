package me.dzikry.movapp.ui.news

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import me.dzikry.movapp.R
import me.dzikry.movapp.data.models.Article
import me.dzikry.movapp.data.network.APIs
import me.dzikry.movapp.data.network.Resource
import me.dzikry.movapp.data.network.RetrofitInstance
import me.dzikry.movapp.data.repositories.NewsRepository
import me.dzikry.movapp.databinding.FragmentNewsBinding
import me.dzikry.movapp.ui.news.adapter.NewsAdapter
import me.dzikry.movapp.ui.news.adapter.TrendingAdapter

class NewsFragment : Fragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }

    private lateinit var binding: FragmentNewsBinding
    private lateinit var viewModel: NewsViewModel

    private lateinit var mAdapter: NewsAdapter
    private lateinit var mAdapter2: TrendingAdapter

    private var newsCategoryPage = 1
    private var newsTrendingPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val api = RetrofitInstance.getRetrofitNewsInstance().create(APIs::class.java)
        val repo = NewsRepository(api)
        val factory = NewsViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory).get(NewsViewModel::class.java)

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, id ->
            if (id == R.id.radioBusiness) {
                clearDataNewsCategory()
                getNewsCategory("business", newsCategoryPage)
            } else if (id == R.id.radioEntertainment) {
                clearDataNewsCategory()
                getNewsCategory("entertainment", newsCategoryPage)
            } else if (id == R.id.radioGeneral) {
                clearDataNewsCategory()
                getNewsCategory("general", newsCategoryPage)
            } else if (id == R.id.radioHealth) {
                clearDataNewsCategory()
                getNewsCategory("health", newsCategoryPage)
            } else if (id == R.id.radioScience) {
                clearDataNewsCategory()
                getNewsCategory("science", newsCategoryPage)
            } else if (id == R.id.radioSports) {
                clearDataNewsCategory()
                getNewsCategory("sports", newsCategoryPage)
            } else {
                clearDataNewsCategory()
                getNewsCategory("technology", newsCategoryPage)
            }
        }

        getNewsCategory("business", newsCategoryPage)
        getNewsTrending(newsTrendingPage)
    }

    private fun clearDataNewsCategory() {
        viewModel.clearHeadlinesNews()
    }

    private fun showNewsDetails(news: Article) {
        Toast.makeText(context, news.title, Toast.LENGTH_SHORT).show()
    }

    private fun getNewsCategory(category: String, page: Int) {
        viewModel.getHeadlinesNews(category, page)
        viewModel.headlineNews.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    binding.progressBarNewsCategory.visibility = View.GONE
                    binding.recyclerViewCategory.visibility = View.VISIBLE
                    response.data?.let {
                        mAdapter = NewsAdapter {news -> showNewsDetails(news)}
                        binding.recyclerViewCategory.apply {
                            adapter = mAdapter
                        }
                        mAdapter.differ.submitList(it)
                    }
                }
                is Resource.Error -> {
                    binding.progressBarNewsCategory.visibility = View.GONE
                    binding.recyclerViewCategory.visibility = View.GONE
                    response.message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    binding.progressBarNewsCategory.visibility = View.VISIBLE
                    binding.recyclerViewCategory.visibility = View.GONE
                }
            }
        })
    }

    private fun getNewsTrending(page: Int) {
        viewModel.getTrendingNews("", page)
        viewModel.trendingNews.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    binding.progressBarNewsTrending.visibility = View.GONE
                    binding.recyclerViewTrending.visibility = View.VISIBLE
                    response.data?.let {
                        mAdapter2 = TrendingAdapter {news -> showNewsDetails(news)}
                        binding.recyclerViewTrending.apply {
                            adapter = mAdapter2
                        }
                        mAdapter2.differ.submitList(it)
                    }
                }
                is Resource.Error -> {
                    binding.progressBarNewsTrending.visibility = View.GONE
                    binding.recyclerViewTrending.visibility = View.GONE
                    response.message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    binding.progressBarNewsTrending.visibility = View.VISIBLE
                    binding.recyclerViewTrending.visibility = View.GONE
                }
            }
        })
    }

}