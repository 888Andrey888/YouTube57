package com.example.youtube57.presentation.playlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youtube57.core.base.BaseViewModel
import com.example.youtube57.data.model.PlaylistsModel
import com.example.youtube57.domain.repository.Repository

class PlaylistsViewModel(private val repository: Repository) : BaseViewModel() {

    private var _playlists = MutableLiveData<PlaylistsModel>()
    val playlists: LiveData<PlaylistsModel> get() = _playlists

    fun getPlaylists() = doOperation(
        operation = { repository.getPlaylists() },
        success = { _playlists.postValue(it) }
    )
}