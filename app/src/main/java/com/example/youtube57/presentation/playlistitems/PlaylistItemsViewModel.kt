package com.example.youtube57.presentation.playlistitems

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youtube57.core.base.BaseViewModel
import com.example.youtube57.data.model.PlaylistsModel
import com.example.youtube57.domain.repository.Repository

class PlaylistItemsViewModel(private val repository: Repository) : BaseViewModel() {

    private var _playlistItems = MutableLiveData<PlaylistsModel>()
    val playlistItems: LiveData<PlaylistsModel> get() = _playlistItems

    fun getPlaylistItems(playlistId: String) = doOperation(
        operation = { repository.getPlaylistItems(playlistId) },
        success = { _playlistItems.postValue(it) }
    )
}