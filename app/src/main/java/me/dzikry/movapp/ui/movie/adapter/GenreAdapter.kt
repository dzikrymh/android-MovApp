package me.dzikry.movapp.ui.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.dzikry.movapp.data.models.Genre
import me.dzikry.movapp.databinding.ItemGenreBinding

class GenreAdapter(
    private var genres: MutableList<Genre>,
    private val onGenreClick: (genre: Genre) -> Unit
) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenreAdapter.GenreViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = ItemGenreBinding.inflate(view, parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreAdapter.GenreViewHolder, position: Int) {
        holder.bind(genres[position])
    }

    override fun getItemCount(): Int = genres.size

    fun appendGenres(genres: List<Genre>) {
        this.genres.addAll(genres)
        notifyItemRangeInserted(
            this.genres.size,
            genres.size - 1
        )
        notifyDataSetChanged()
    }

    inner class GenreViewHolder(itemView: ItemGenreBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView

        fun bind(genre: Genre) {
            binding.textGenre.text = genre.name

            itemView.setOnClickListener { onGenreClick.invoke(genre) }
        }
    }

}