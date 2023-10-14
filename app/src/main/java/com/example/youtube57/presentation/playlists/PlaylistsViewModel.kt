package com.example.youtube57.presentation.playlists

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.youtube57.core.base.BaseViewModel
import com.example.youtube57.data.model.PlaylistsModel
import com.example.youtube57.domain.repository.Repository

class PlaylistsViewModel(private val repository: Repository) : BaseViewModel() {

    fun getPagingPlaylists(): LiveData<PagingData<PlaylistsModel.Item>> {
        return repository.getPlaylists()
    }

}