package com.example.youtube57.presentation.playlists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.youtube57.R
import com.example.youtube57.core.base.BaseFragment
import com.example.youtube57.core.utils.Status
import com.example.youtube57.databinding.FragmentPlaylistsBinding
import com.example.youtube57.presentation.MainActivity
import com.example.youtube57.utils.IsOnline

internal class PlaylistsFragment : BaseFragment<FragmentPlaylistsBinding>() {

    private val adapter = PlaylistsAdapter()
    private val playlistsViewModel = PlaylistsViewModel(MainActivity.repository)

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPlaylistsBinding.inflate(inflater, container, false)

    override fun checkConnection() {
        IsOnline(requireContext()).observe(viewLifecycleOwner) {
            if (!it)
                findNavController().navigate(R.id.noConnectionFragment)
        }
    }

    override fun initRecycler() {
        playlistsViewModel.getPlaylists().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    resource.data?.let { adapter.addData(it.items) }
                    binding.rvPlaylists.adapter = adapter
                }

                Status.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        "${resource.message} ${resource.code}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }
}