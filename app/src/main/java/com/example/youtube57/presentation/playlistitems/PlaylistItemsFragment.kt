package com.example.youtube57.presentation.playlistitems

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.youtube57.core.base.BaseFragment
import com.example.youtube57.data.model.PlaylistsModel
import com.example.youtube57.databinding.FragmentPlaylistItemsBinding
import com.example.youtube57.presentation.MainActivity
import com.example.youtube57.utils.Constants
import com.example.youtube57.utils.IsOnline

class PlaylistItemsFragment : BaseFragment<FragmentPlaylistItemsBinding, PlaylistItemsViewModel>() {

    private val adapter = PlaylistItemsAdapter()

    //    override val viewModel: PlaylistItemsViewModel
//        get() = PlaylistItemsViewModel(MainActivity.repository)
    private val viewModel = PlaylistItemsViewModel(MainActivity.repository)
    private val isOnline: IsOnline by lazy {
        IsOnline(requireContext())
    }

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPlaylistItemsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initResultListener()
        checkConnection()
        initLiveData()
    }

    private fun initView(playlistId: String) {
        viewModel.getPlaylistItems(playlistId)
    }

    private fun initLiveData() {
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

    private fun checkConnection() {
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

    private fun initResultListener() {
        setFragmentResultListener(Constants.REQUIRES_GO_TO_PLAYLIST_ITEMS_FRAGMENT) { _, bundle ->
            bundle.getSerializable(Constants.REQUIRES_SET_ITEM_TO_PLAYLIST_ITEMS_FRAGMENT)
                ?.let { item ->
                    val _item = item as PlaylistsModel.Item
                    initCoordinator(_item)
                    initView(_item.id)
                }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initCoordinator(item: PlaylistsModel.Item) {
        binding.tvTitle.text = item.snippet.title
        binding.tvDescription.text = item.snippet.description
        binding.tvVideosCount.text = item.contentDetails.itemCount.toString() + " video series"
        binding.layoutToolbarItems.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}