package com.example.dermalyze.ui.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dermalyze.databinding.ItemArticleBinding
import com.example.dermalyze.ui.main.models.Article
import com.example.dermalyze.ui.main.utils.Extensions.showImageInto

class ArticleAdapter(private val data: (Article) -> Unit) : ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.apply {
                tvTitle.text = article.title
                tvPublisher.text = article.publisher
                tvDatePublish.text = article.datePublish
                ivCover.showImageInto(itemView.context, article.imageUrl)
                itemView.setOnClickListener { data.invoke(article) }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article) = oldItem == newItem
            override fun areContentsTheSame(oldItem: Article, newItem: Article) = oldItem.id == newItem.id
        }
    }
}
