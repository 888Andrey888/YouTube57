package com.example.youtube57.presentation.playlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.youtube57.core.network.RetrofitClient
import com.example.youtube57.databinding.FragmentPlaylistsBinding
import com.example.youtube57.domain.repository.Repository
import com.slottica.reviewfueatures.youtube57_3.core.utils.Status

class PlaylistsFragment : Fragment() {

    private val playlistsViewModel =
        PlaylistsViewModel(Repository(RetrofitClient().createApiService()))
    private lateinit var binding: FragmentPlaylistsBinding
    private val adapter = PlaylistsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playlistsViewModel.getPlaylists().observe(viewLifecycleOwner) {resource ->
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