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
import com.example.youtube57.utils.IsOnline
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistItemsFragment : BaseFragment<FragmentPlaylistItemsBinding, PlaylistItemsViewModel>() {

    private val adapter = PlaylistItemsAdapter(this::onClickItem)
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
            viewModel.getPlaylistItems(playlistItem.id)
            initCoordinator(playlistItem)
        }

    }

    override fun initLiveData() {
        viewModel.playlistItems.observe(viewLifecycleOwner) { list ->
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

    private fun initRecycler(items: List<PlaylistsModel.Item>) {
        adapter.addData(items)
        binding.rvPlaylistItems.adapter = adapter
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