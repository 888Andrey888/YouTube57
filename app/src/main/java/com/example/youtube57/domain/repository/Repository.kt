package com.example.youtube57.domain.repository

import com.example.youtube57.core.network.RemoteDataSource
import com.example.youtube57.data.model.PlaylistsModel

class Repository(private val remoteDataSource: RemoteDataSource) {

    suspend fun getPlaylists(): Result<PlaylistsModel> {
        return remoteDataSource.getPlaylists()
    }
}