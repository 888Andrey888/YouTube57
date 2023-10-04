package com.example.youtube57.presentation.playlistitems

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.youtube57.core.base.BaseFragment
import com.example.youtube57.databinding.FragmentPlaylistItemsBinding
import com.example.youtube57.presentation.MainActivity

class PlaylistItemsFragment : BaseFragment<FragmentPlaylistItemsBinding, PlaylistItemsViewModel>() {
    override val viewModel: PlaylistItemsViewModel
        get() = PlaylistItemsViewModel(MainActivity.repository)

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPlaylistItemsBinding.inflate(inflater, container, false)

    override fun checkConnection() {

    }

    override fun initRecycler() {

    }

    override fun initView() {

    }
}