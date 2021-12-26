package me.dzikry.movapp.ui.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import me.dzikry.movapp.data.models.Article
import me.dzikry.movapp.databinding.ItemNewsCategoryBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class NewsCategoryAdapter(
    private var news: MutableList<Article>,
    private val onNewsClick: (news: Article) -> Unit
) : RecyclerView.Adapter<NewsCategoryAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = ItemNewsCategoryBinding.inflate(view, parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int = news.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news[position])
    }

    fun appendNewsCategory(news: List<Article>) {
        this.news.addAll(news)
        notifyItemRangeInserted(
            this.news.size,
            news.size - 1
        )
        notifyDataSetChanged()
    }

    fun clearNewsCategory() {
        this.news.clear()
        notifyDataSetChanged()
    }

    inner class NewsViewHolder(itemView: ItemNewsCategoryBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView

        fun bind(news: Article) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(news.urlToImage)
                    .transform(CenterCrop())
                    .into(itemImage)
                itemTitle.text = news.title
                itemNameAuthor.text = news.author

                try {
                    val dateApiFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
                    val date = dateApiFormat.parse(news.publishedAt)

                    val dateView = SimpleDateFormat("MMM, dd yyyy", Locale.ENGLISH)

                    date?.let {
                        itemPublishAuthor.text = dateView.format(it).toString()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    itemPublishAuthor.text = news.publishedAt
                }
            }

            itemView.setOnClickListener { onNewsClick.invoke(news) }
        }
    }
}