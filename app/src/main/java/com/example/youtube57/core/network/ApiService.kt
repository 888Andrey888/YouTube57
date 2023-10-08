package com.example.youtube57.core.network

import com.example.youtube57.data.model.PlaylistsModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("playlists")
    suspend fun getPlaylists(
        @Query("part") part: String,
        @Query("key") apiKey: String,
        @Query("channelId") channelId: String,
        @Query("maxResults") maxResults: Int
    ): Response<PlaylistsModel>

    @GET("playlistItems")
    suspend fun getPlaylistItems(
        @Query("part") part: String,
        @Query("key") apiKey: String,
        @Query("playlistId") playlistId: String,
        @Query("maxResults") maxResults: Int
    ): Response<PlaylistsModel>

    @GET("videos")
    suspend fun getVideo(
        @Query("part") part: String,
        @Query("key") apiKey: String,
        @Query("id") videoId: String
    ): Response<PlaylistsModel>

}