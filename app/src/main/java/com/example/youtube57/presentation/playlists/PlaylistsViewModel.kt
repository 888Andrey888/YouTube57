package com.example.youtube57.presentation.playlists

import androidx.lifecycle.LiveData
import com.example.youtube57.core.base.BaseViewModel
import com.example.youtube57.data.model.PlaylistsModel
import com.slottica.reviewfueatures.youtube57_3.core.utils.Resource
import com.slottica.reviewfueatures.youtube57_3.domain.repository.Repository

internal class PlaylistsViewModel(private val repository: Repository) : BaseViewModel() {
	
	fun getPlaylists(): LiveData<Resource<PlaylistsModel>> {
		return repository.getPlaylists()
	}
}