package com.example.youtube57.presentation.playlists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.youtube57.R
import com.example.youtube57.core.network.RetrofitClient
import com.example.youtube57.databinding.FragmentPlaylistsBinding
import com.slottica.reviewfueatures.youtube57_3.core.utils.Status
import com.slottica.reviewfueatures.youtube57_3.domain.repository.Repository

class PlaylistsFragment : Fragment() {

    private val playlistsViewModel = PlaylistsViewModel(Repository(RetrofitClient().createApiService()))
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

        playlistsViewModel.getPlaylists().observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    it.data?.let { it1 -> adapter.addData(it1.items) }
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