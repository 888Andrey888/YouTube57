package com.example.youtube57.presentation.video

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.youtube57.R
import com.example.youtube57.core.base.BaseFragment
import com.example.youtube57.data.model.PlaylistsModel
import com.example.youtube57.databinding.FragmentVideoBinding
import com.example.youtube57.utils.Constants
import com.example.youtube57.utils.IsOnline
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoFragment : BaseFragment<FragmentVideoBinding, VideoViewModel>() {

    override val viewModel: VideoViewModel by viewModel()
    private val isOnline: IsOnline by lazy {
        IsOnline(requireContext())
    }

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentVideoBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initResultListener()
        checkConnection()
        initLiveData()
        initListener()
    }

    private fun initListener() {
        binding.layoutToolbarItems.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnDownload.setOnClickListener {
            download()
        }
    }

    private fun download() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setView(R.layout.layout_download).show()
    }

    private fun initResultListener() {
        setFragmentResultListener(Constants.REQUIRES_GO_TO_VIDEO_FRAGMENT) { _, bundle ->
            bundle.getSerializable(Constants.REQUIRES_SET_ITEM_TO_VIDEO_FRAGMENT)
                ?.let { item ->
                    val _item = item as PlaylistsModel.Item
                    initView(_item.contentDetails.videoId)
                }
        }
    }

    private fun initView(videoId: String) {
        viewModel.getVideo(videoId)
    }

    private fun initLiveData() {
        viewModel.video.observe(viewLifecycleOwner) { item ->
            setView(item.items)
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

    private fun setView(items: List<PlaylistsModel.Item>) {
        binding.tvTitle.text = items.first().snippet.title
        binding.tvDescription.text = items.first().snippet.description
        binding.imgVideo.load(items.first().snippet.thumbnails.standard.url)
    }

    private fun checkConnection() {
        isOnline.observe(viewLifecycleOwner) { isConnect ->
            if (!isConnect) {
                binding.llMainContent.visibility = View.GONE
                binding.llInclude.visibility = View.VISIBLE
            }
            binding.layoutNoConnection.btnTryAgain.setOnClickListener {
                if (isConnect) {
                    binding.llMainContent.visibility = View.VISIBLE
                    binding.llInclude.visibility = View.GONE
                }
            }
        }
    }

}