package com.example.youtube57.presentation.playlistitems

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.youtube57.core.base.BaseFragment
import com.example.youtube57.data.model.PlaylistsModel
import com.example.youtube57.databinding.FragmentPlaylistItemsBinding
import com.example.youtube57.presentation.playlists.pagingloadstate.PlaylistsLoadStateAdapter
import com.example.youtube57.utils.IsOnline
import com.example.youtube57.utils.PlaylistsModelComparator
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistItemsFragment : BaseFragment<FragmentPlaylistItemsBinding, PlaylistItemsViewModel>() {

    private val adapter = PlaylistItemsAdapter(PlaylistsModelComparator, this::onClickItem)
    private var playlistId: String = ""
    private val isOnline: IsOnline by lazy {
        IsOnline(requireContext())
    }
    override val viewModel: PlaylistItemsViewModel by viewModel()

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPlaylistItemsBinding.inflate(inflater, container, false)

    override fun initListener() {
//        TODO("Not yet implemented")
    }

    override fun initView() {
        arguments?.let {
            val playlistItem: PlaylistsModel.Item =
                PlaylistItemsFragmentArgs.fromBundle(it).playlistItem
            playlistId = playlistItem.id
            initCoordinator(playlistItem)
        }
        binding.rvPlaylistItems.adapter = adapter
    }

    override fun initLiveData() {
        viewModel.getPagingPlaylistItems(playlistId).observe(viewLifecycleOwner) { list ->
            adapter.submitData(lifecycle = lifecycle, list)
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
                binding.llContainerPlaylistItems.visibility = View.GONE
                binding.flInclude.visibility = View.VISIBLE
            }
            binding.layoutNoConnection.btnTryAgain.setOnClickListener {
                if (isConnect) {
                    binding.llContainerPlaylistItems.visibility = View.VISIBLE
                    binding.flInclude.visibility = View.GONE
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initCoordinator(item: PlaylistsModel.Item) {
        binding.tvTitle.text = item.snippet.title
        binding.tvDescription.text = item.snippet.description
        binding.tvVideosCount.text = "${item.contentDetails.itemCount} video series"
        binding.layoutToolbarItems.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun onClickItem(playlistItem: PlaylistsModel.Item) {
        findNavController().navigate(
            PlaylistItemsFragmentDirections.actionPlaylistItemsFragmentToVideoFragment(
                playlistItem
            )
        )
    }

}