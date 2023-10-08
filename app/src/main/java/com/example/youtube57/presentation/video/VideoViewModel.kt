package com.example.youtube57.presentation.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youtube57.core.base.BaseViewModel
import com.example.youtube57.data.model.PlaylistsModel
import com.example.youtube57.domain.repository.Repository

class VideoViewModel(private val repository: Repository): BaseViewModel() {

    private var _video = MutableLiveData<PlaylistsModel>()
    val video: LiveData<PlaylistsModel> get() = _video

    fun getVideo(videoId: String) = doOperation(
        operation = { repository.getVideo(videoId) },
        success = { _video.postValue(it) }
    )
}