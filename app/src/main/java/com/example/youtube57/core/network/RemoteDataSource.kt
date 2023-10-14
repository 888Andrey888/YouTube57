package com.example.youtube57.core.network

import com.example.youtube57.BuildConfig
import com.example.youtube57.core.base.BaseDataSource
import com.example.youtube57.data.model.PlaylistsModel
import com.example.youtube57.utils.Constants

class RemoteDataSource(private val apiService: ApiService) : BaseDataSource() {

    suspend fun getPlaylists(pageToken: String): Result<PlaylistsModel> {
        return getResult {
            apiService.getPlaylists(
                part = Constants.PART,
                channelId = Constants.CHANNEL_ID,
                apiKey = BuildConfig.API_KEY,
                maxResults = 12,
                pageToken = pageToken
            )
        }
    }

    suspend fun getPlaylistItems(playlistId: String, pageToken: String): Result<PlaylistsModel>{
        return getResult {
            apiService.getPlaylistItems(
                part = Constants.PART,
                apiKey = BuildConfig.API_KEY,
                playlistId = playlistId,
                maxResults = 12,
                pageToken = pageToken
            )
        }
    }

    suspend fun getVideo(videoId: String): Result<PlaylistsModel>{
        return getResult {
            apiService.getVideo(
                part = Constants.PART,
                apiKey = BuildConfig.API_KEY,
                videoId = videoId
            )
        }
    }
}