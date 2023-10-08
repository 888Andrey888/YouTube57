package com.example.youtube57.presentation.playlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.youtube57.R
import com.example.youtube57.core.base.BaseFragment
import com.example.youtube57.data.model.PlaylistsModel
import com.example.youtube57.databinding.FragmentPlaylistsBinding
import com.example.youtube57.utils.Constants
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkConnection()
        initView()
        initLiveData()
    }

    private fun initLiveData() {
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

    private fun checkConnection() {
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

    private fun initView() {
        viewModel.getPlaylists()
    }

    private fun onClickItem(playlistItem: PlaylistsModel.Item) {
        setFragmentResult(
            Constants.REQUIRES_GO_TO_PLAYLIST_ITEMS_FRAGMENT,
            bundleOf(Constants.REQUIRES_SET_ITEM_TO_PLAYLIST_ITEMS_FRAGMENT to playlistItem)
        )
        findNavController().navigate(R.id.playlistItemsFragment)
    }
}