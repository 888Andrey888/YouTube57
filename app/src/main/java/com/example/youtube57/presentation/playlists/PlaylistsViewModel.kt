package com.example.youtube57.presentation.playlists

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.youtube57.core.base.BaseViewModel
import com.example.youtube57.data.model.PlaylistsModel
import com.example.youtube57.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistsViewModel(private val repository: Repository) : BaseViewModel() {

    private val _playlists = MutableLiveData<PlaylistsModel>()
    val playlists: LiveData<PlaylistsModel> get() = _playlists

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getPlaylists() {
        viewModelScope.launch {
            val result = repository.getPlaylists()
            when {
                result.isSuccess -> {
                    result.onSuccess {
                        _playlists.postValue(it)
                    }
                }

                result.isFailure -> {
                    result.onFailure {
                        _error.postValue(it.message)
                    }
                }
            }
        }
    }
}