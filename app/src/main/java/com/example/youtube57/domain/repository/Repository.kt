package com.example.youtube57.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.youtube57.core.network.RemoteDataSource
import com.example.youtube57.data.model.PlaylistsModel
import com.example.youtube57.domain.paging.PagingSource
import com.example.youtube57.utils.Constants

class Repository(private val remoteDataSource: RemoteDataSource) {

    fun getPlaylists(): LiveData<PagingData<PlaylistsModel.Item>> {
        val pagingData = Pager(
            config = PagingConfig(
                initialLoadSize = 20,
                pageSize = Int.MAX_VALUE,
                enablePlaceholders = true,
                prefetchDistance = 5
            ),
            pagingSourceFactory = {
                PagingSource(
                    remoteDataSource = remoteDataSource,
                    query = Constants.GET_PLAYLISTS,
                    ""
                )
            }
        )

        return pagingData.liveData
    }

    fun getPlaylistItems(playlistId: String): LiveData<PagingData<PlaylistsModel.Item>> {
        val pagingData = Pager(
            config = PagingConfig(
                initialLoadSize = 20,
                pageSize = Int.MAX_VALUE,
                enablePlaceholders = true,
                prefetchDistance = 5
            ),
            pagingSourceFactory = {
                PagingSource(
                    remoteDataSource = remoteDataSource,
                    query = Constants.GET_PLAYLISTS_ITEM,
                    playlistId
                )
            }
        )

        return pagingData.liveData
    }

    suspend fun getVideo(videoId: String): Result<PlaylistsModel> {
        return remoteDataSource.getVideo(videoId)
    }
}