package com.example.youtube57.presentation.video

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.youtube57.R
import com.example.youtube57.core.base.BaseFragment
import com.example.youtube57.data.model.PlaylistsModel
import com.example.youtube57.databinding.FragmentVideoBinding
import com.example.youtube57.utils.IsOnline
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
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

    override fun initView() {
        arguments?.let {
            val playlistVideo: PlaylistsModel.Item =
                VideoFragmentArgs.fromBundle(it).playlistVideo
            viewModel.getVideo(playlistVideo.contentDetails.videoId!!)
        }

    }

    override fun initListener() {
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

    override fun initLiveData() {
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
        lifecycle.addObserver(binding.utpvVideo)

        binding.tvTitle.text = items.first().snippet.title
        binding.tvDescription.text = items.first().snippet.description

        binding.utpvVideo.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(items.first().id, 0f)
            }
        })

    }

    override fun checkConnection() {
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