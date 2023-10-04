package com.example.youtube57.presentation.playlists

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.youtube57.core.base.BaseFragment
import com.example.youtube57.databinding.FragmentPlaylistsBinding
import com.example.youtube57.presentation.MainActivity
import com.example.youtube57.utils.IsOnline

class PlaylistsFragment : BaseFragment<FragmentPlaylistsBinding, PlaylistsViewModel>() {

    private val adapter = PlaylistsAdapter()
    override val viewModel: PlaylistsViewModel
        get() = PlaylistsViewModel(MainActivity.repository)

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPlaylistsBinding.inflate(inflater, container, false)

    override fun checkConnection() {
        IsOnline(requireContext()).observe(viewLifecycleOwner) { isConnect ->
            if (!isConnect) {
                binding.rvPlaylists.visibility = View.GONE
                binding.llInclude.visibility = View.VISIBLE
            }
            binding.layoutNoConnection.btnTryAgain.setOnClickListener {
                if (isConnect) {
                    binding.rvPlaylists.visibility = View.VISIBLE
                    binding.llInclude.visibility = View.GONE
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.playlists.observe(viewLifecycleOwner){
            adapter.addData(it.items)
        }
    }

    override fun initRecycler() {
        //binding.progressBar.visibility = View.GONE

        /*viewModel.playlists.observe(viewLifecycleOwner) {
            Log.d("ololo", "initRecycler: ok")
            adapter.addData(it.items)
        }*/
        binding.rvPlaylists.adapter = adapter
    }

    override fun initView() {
        viewModel.getPlaylists()
    }
}