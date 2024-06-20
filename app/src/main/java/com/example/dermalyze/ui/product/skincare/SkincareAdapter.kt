package com.example.dermalyze.ui.product.skincare

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dermalyze.databinding.ContentProductBinding
import com.example.dermalyze.ui.main.models.Skincare
import com.example.dermalyze.ui.main.utils.Extensions.showImageInto

class SkincareAdapter (private val data: (Skincare) -> Unit) : ListAdapter<Skincare, SkincareAdapter.SkincareViewHolder>(
    DIFF_CALLBACK
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkincareViewHolder {
        val binding = ContentProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SkincareViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SkincareViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SkincareViewHolder(private val binding: ContentProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(skincare: Skincare) {
            binding.apply {
                tvTitleProduct.text = skincare.title
                tvDescriptionProduct.text = skincare.description
                tvContentProduct.text = skincare.readme
                imgCardOne.showImageInto(itemView.context, skincare.imageUrl)
                itemView.setOnClickListener { data.invoke(skincare) }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Skincare>() {
            override fun areItemsTheSame(oldItem: Skincare, newItem: Skincare) = oldItem == newItem
            override fun areContentsTheSame(oldItem: Skincare, newItem: Skincare) =
                oldItem.id == newItem.id
        }
    }

}