package com.example.youtube57.presentation.playlists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.youtube57.core.base.BaseFragment
import com.example.youtube57.core.utils.Status
import com.example.youtube57.databinding.FragmentPlaylistsBinding
import com.example.youtube57.presentation.MainActivity
import com.example.youtube57.utils.PlayListViewModelFactory

internal class PlaylistsFragment : BaseFragment<FragmentPlaylistsBinding, PlaylistsViewModel>() {

    private val adapter = PlaylistsAdapter()
    private val factory = PlayListViewModelFactory(MainActivity.repository)
    override val viewModel: PlaylistsViewModel
        get() = ViewModelProvider(this, factory)[PlaylistsViewModel::class.java]

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPlaylistsBinding.inflate(inflater, container, false)

    override fun initRecycler() {
        viewModel.getPlaylists().observe(viewLifecycleOwner) { resource ->
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