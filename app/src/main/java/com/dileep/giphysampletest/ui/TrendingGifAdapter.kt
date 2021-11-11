package com.dileep.giphysampletest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dileep.giphysampletest.R
import com.dileep.giphysampletest.data.source.local.entity.Gif
import com.dileep.giphysampletest.databinding.GifRvItemBinding

class TrendingGifAdapter(
    private val mListener: TrendingGifToggleListener
): PagingDataAdapter<Gif, TrendingGifAdapter.TrendingViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        val binding = DataBindingUtil.inflate<GifRvItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.gif_rv_item,
            parent,
            false
        )
        return TrendingViewHolder(binding)
    }

    inner class TrendingViewHolder(private val binding: GifRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(gif: Gif) {
            with(binding){
                item = gif
                listener = mListener
                executePendingBindings()
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Gif>() {
            override fun areItemsTheSame(oldItem: Gif, newItem: Gif): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Gif, newItem: Gif): Boolean =
                oldItem == newItem
        }
    }
}