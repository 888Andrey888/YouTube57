package com.example.youtube57.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.youtube57.BuildConfig
import com.example.youtube57.core.network.ApiService
import com.example.youtube57.core.network.RemoteDataSource
import com.example.youtube57.data.model.PlaylistsModel
import com.example.youtube57.utils.Constants
import com.example.youtube57.core.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(private val remoteDataSource: RemoteDataSource) {

    internal fun getPlaylists(): LiveData<Resource<PlaylistsModel>> {
        return liveData{
            emit(Resource.loading())
            emit(remoteDataSource.getPlaylists())
        }
    }
}