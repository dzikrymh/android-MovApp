package me.dzikry.movapp.ui.search_news

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import me.dzikry.movapp.R
import me.dzikry.movapp.databinding.FragmentSearchNewsBinding

class SearchNewsFragment : Fragment() {

    companion object {
        fun newInstance() = SearchNewsFragment()
    }

    private lateinit var binding: FragmentSearchNewsBinding
    private lateinit var viewModel: SearchNewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchNewsViewModel::class.java)

        binding.apply {
            edtSearch.setOnEditorActionListener { view, id, keyEvent ->
                if (id == EditorInfo.IME_ACTION_SEARCH) {
                    searchNews(edtSearch.text.trim().toString())
                    true
                }
                false
            }
        }
    }

    private fun searchNews(keyword: String) {
        TODO("Not yet implemented")
    }

}