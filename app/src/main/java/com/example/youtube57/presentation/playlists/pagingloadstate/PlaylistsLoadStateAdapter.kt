package com.example.youtube57.presentation.playlists.pagingloadstate

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class PlaylistsLoadStateAdapter() : LoadStateAdapter<PlaylistsLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: PlaylistsLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PlaylistsLoadStateViewHolder {
        return PlaylistsLoadStateViewHolder.create(parent = parent)
    }
}