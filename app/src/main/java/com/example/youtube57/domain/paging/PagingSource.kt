package com.example.youtube57.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.youtube57.core.network.RemoteDataSource
import com.example.youtube57.data.model.PlaylistsModel
import com.example.youtube57.utils.Constants

class PagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val query: String,
    private val utils: String
) : PagingSource<String, PlaylistsModel.Item>() {
    override fun getRefreshKey(state: PagingState<String, PlaylistsModel.Item>): String? {
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, PlaylistsModel.Item> {
        try {
            val pageToken = params.key ?: ""
            var response: Result<PlaylistsModel>? = null
            var nextKey = ""
            when (query) {
                Constants.GET_PLAYLISTS -> response =
                    remoteDataSource.getPlaylists(pageToken)

                Constants.GET_PLAYLISTS_ITEM -> response =
                    remoteDataSource.getPlaylistItems(utils, pageToken)
            }
            val items = mutableListOf<PlaylistsModel.Item>()

            if (response != null) {
                when {
                    response.isSuccess -> response.onSuccess {
                        items.addAll(it.items)
                    }

                    response.isFailure -> response.onFailure {

                    }

                }

                if (response.isSuccess) response.onSuccess { nextKey = it.nextPageToken }
            }

            return LoadResult.Page(data = items, prevKey = null, nextKey = nextKey)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}