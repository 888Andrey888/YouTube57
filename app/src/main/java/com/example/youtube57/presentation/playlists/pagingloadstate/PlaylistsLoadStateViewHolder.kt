package com.example.youtube57.presentation.playlists.pagingloadstate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.youtube57.R
import com.example.youtube57.databinding.ItemProgressBarBinding

class PlaylistsLoadStateViewHolder(
    binding: ItemProgressBarBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(loadState: LoadState) {
        loadState.endOfPaginationReached
    }

    companion object {
        fun create(parent: ViewGroup): PlaylistsLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_progress_bar,
                parent,
                false
            )
            val binding = ItemProgressBarBinding.bind(view)
            return PlaylistsLoadStateViewHolder(binding)
        }
    }
}