package com.dicoding.picodiploma.mystory2.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.mystory2.data.api.respone.ListStoryItem
import com.dicoding.picodiploma.mystory2.databinding.ItemStoryBinding

class StoryAdapter(private val onClick: (ListStoryItem) -> Unit) : PagingDataAdapter<ListStoryItem, StoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = getItem(position)
        if (story != null) {
            holder.bind(story, onClick)
        }
    }

    class ViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem, onClick: (ListStoryItem) -> Unit) {
            binding.apply {
                tvItemName.text = story.name
                tvDetailDescription.text = story.description
                Glide.with(itemView.context)
                    .load(story.photoUrl)
                    .into(ivItemPhoto)

                root.setOnClickListener {
                    onClick(story)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean =
                oldItem == newItem
        }
    }
}