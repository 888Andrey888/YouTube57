package com.example.youtube57.presentation.playlists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.youtube57.core.base.BaseFragment
import com.example.youtube57.data.model.PlaylistsModel
import com.example.youtube57.databinding.FragmentPlaylistsBinding
import com.example.youtube57.utils.IsOnline
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistsFragment : BaseFragment<FragmentPlaylistsBinding, PlaylistsViewModel>() {

    private val adapter = PlaylistsAdapter(this::onClickItem)
    private val isOnline: IsOnline by lazy {
        IsOnline(requireContext())
    }
    override val viewModel: PlaylistsViewModel by viewModel()

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPlaylistsBinding.inflate(inflater, container, false)

    override fun initListener() {
//        TODO("Not yet implemented")
    }

    override fun initLiveData() {
        viewModel.playlists.observe(viewLifecycleOwner) { list ->
            initRecycler(list.items)
        }

        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            if (loading)
                binding.progressBar.visibility = View.VISIBLE
            else
                binding.progressBar.visibility = View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun checkConnection() {
        isOnline.observe(viewLifecycleOwner) { isConnect ->
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

    private fun initRecycler(items: List<PlaylistsModel.Item>) {
        adapter.addData(items)
        binding.rvPlaylists.adapter = adapter
    }

    override fun initView() {
        viewModel.getPlaylists()
    }

    private fun onClickItem(playlistItem: PlaylistsModel.Item) {
        findNavController().navigate(
            PlaylistsFragmentDirections.actionPlaylistsFragmentToPlaylistItemsFragment(
                playlistItem
            )
        )
    }
}