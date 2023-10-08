package com.example.youtube57.core.di

import com.example.youtube57.presentation.playlistitems.PlaylistItemsViewModel
import com.example.youtube57.presentation.playlists.PlaylistsViewModel
import com.example.youtube57.presentation.video.VideoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PlaylistsViewModel(get()) }
    viewModel { PlaylistItemsViewModel(get()) }
    viewModel { VideoViewModel(get()) }
}