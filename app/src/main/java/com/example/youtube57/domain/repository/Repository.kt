package com.example.youtube57.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.youtube57.core.network.RemoteDataSource
import com.example.youtube57.core.utils.Resource
import com.example.youtube57.data.model.PlaylistsModel

class Repository(private val remoteDataSource: RemoteDataSource) {

    internal fun getPlaylists(): LiveData<Resource<PlaylistsModel>> {
        return liveData {
            emit(Resource.loading())
            emit(remoteDataSource.getPlaylists())
        }
    }
}