package com.example.youtube57.presentation.playlists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.youtube57.core.base.BaseFragment
import com.example.youtube57.data.model.PlaylistsModel
import com.example.youtube57.databinding.FragmentPlaylistsBinding
import com.example.youtube57.presentation.playlists.pagingloadstate.PlaylistsLoadStateAdapter
import com.example.youtube57.utils.IsOnline
import com.example.youtube57.utils.PlaylistsModelComparator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistsFragment : BaseFragment<FragmentPlaylistsBinding, PlaylistsViewModel>() {

    private val adapter = PlaylistsAdapter(PlaylistsModelComparator, this::onClickItem)
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
        viewModel.getPagingPlaylists().observe(viewLifecycleOwner) { list ->
            viewModel.viewModelScope.launch(Dispatchers.IO) {
//                работает без них
//                adapter.withLoadStateHeaderAndFooter(
//                    header = PlaylistsLoadStateAdapter(),
//                    footer = PlaylistsLoadStateAdapter()
//                )
                adapter.submitData(lifecycle = lifecycle, list)
            }
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

    override fun initView() {
        binding.rvPlaylists.adapter = adapter
    }

    private fun onClickItem(playlistItem: PlaylistsModel.Item) {
        findNavController().navigate(
            PlaylistsFragmentDirections.actionPlaylistsFragmentToPlaylistItemsFragment(
                playlistItem
            )
        )
    }
}