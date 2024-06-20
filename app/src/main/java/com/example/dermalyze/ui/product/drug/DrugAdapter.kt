package com.example.dermalyze.ui.product.drug

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dermalyze.databinding.ContentObatBinding
import com.example.dermalyze.ui.main.models.Obat
import com.example.dermalyze.ui.main.utils.Extensions.showImageInto

class DrugAdapter (private val data: (Obat) -> Unit) : ListAdapter<Obat, DrugAdapter.ObatViewHolder>(
    DIFF_CALLBACK
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObatViewHolder {
        val binding = ContentObatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ObatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ObatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ObatViewHolder(private val binding: ContentObatBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(obat: Obat) {
            binding.apply {
                tvTitleObat.text = obat.title
                tvDescriptionObat.text = obat.description
                tvContentObat.text = obat.readme
                imgCardObat.showImageInto(itemView.context, obat.imageUrl)
                itemView.setOnClickListener { data.invoke(obat) }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Obat>() {
            override fun areItemsTheSame(oldItem: Obat, newItem: Obat) = oldItem == newItem
            override fun areContentsTheSame(oldItem: Obat, newItem: Obat) =
                oldItem.id == newItem.id
        }
    }

}