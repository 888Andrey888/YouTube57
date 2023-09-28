package com.example.youtube57.presentation.playlists

import androidx.lifecycle.LiveData
import com.example.youtube57.core.base.BaseViewModel
import com.example.youtube57.data.model.PlaylistsModel
import com.example.youtube57.domain.repository.Repository
import com.slottica.reviewfueatures.youtube57_3.core.utils.Resource

internal class PlaylistsViewModel(private val repository: Repository) : BaseViewModel() {

    fun getPlaylists(): LiveData<Resource<PlaylistsModel>> {
        return repository.getPlaylists()
    }
}