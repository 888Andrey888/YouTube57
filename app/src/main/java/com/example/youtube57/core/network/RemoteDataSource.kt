package com.example.youtube57.core.network

import com.example.youtube57.BuildConfig
import com.example.youtube57.core.base.BaseDataSource
import com.example.youtube57.core.utils.Resource
import com.example.youtube57.data.model.PlaylistsModel
import com.example.youtube57.utils.Constants

class RemoteDataSource(private val apiService: ApiService) : BaseDataSource() {

    suspend fun getPlaylists(): Resource<PlaylistsModel> {
        return getResult {
            apiService.getPlaylists(
                part = Constants.PART,
                channelId = Constants.CHANNEL_ID,
                apiKey = BuildConfig.API_KEY,
                maxResults = 12,
            )
        }
    }
}