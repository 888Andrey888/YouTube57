package com.example.youtube57.presentation.playlistitems

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.youtube57.core.base.BaseViewModel
import com.example.youtube57.data.model.PlaylistsModel
import com.example.youtube57.domain.repository.Repository

class PlaylistItemsViewModel(private val repository: Repository) : BaseViewModel() {

    fun getPagingPlaylistItems(playlistId: String): LiveData<PagingData<PlaylistsModel.Item>> {
        return repository.getPlaylistItems(playlistId)
    }
}