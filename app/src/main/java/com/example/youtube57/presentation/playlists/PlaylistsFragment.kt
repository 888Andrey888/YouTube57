package com.example.youtube57.presentation.playlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.youtube57.core.base.BaseFragment
import com.example.youtube57.databinding.FragmentPlaylistsBinding
import com.example.youtube57.presentation.MainActivity
import com.slottica.reviewfueatures.youtube57_3.core.utils.Status

class PlaylistsFragment : BaseFragment<FragmentPlaylistsBinding>() {

    private val playlistsViewModel = PlaylistsViewModel(MainActivity.repository)
    private val adapter = PlaylistsAdapter()
    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPlaylistsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playlistsViewModel.getPlaylists().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.let { adapter.addData(it.items) }
                    binding.rvPlaylists.adapter = adapter
                }

                Status.ERROR -> {

                }

                Status.LOADING -> {

                }
            }
        }
    }
}