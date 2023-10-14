package com.example.youtube57.presentation.playlistitems

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.youtube57.data.model.PlaylistsModel
import com.example.youtube57.databinding.ItemPlaylistBinding
import com.example.youtube57.databinding.ItemPlaylistItemsBinding

class PlaylistItemsAdapter(
    diffUtilCallback: DiffUtil.ItemCallback<PlaylistsModel.Item>,
    private val onClickItem: (playlistItem: PlaylistsModel.Item) -> Unit
) : PagingDataAdapter<PlaylistsModel.Item, PlaylistItemsAdapter.PlaylistItemsViewHolder>(
    diffUtilCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PlaylistItemsViewHolder(
        ItemPlaylistItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: PlaylistItemsViewHolder, position: Int) {
        val newPosition = getItem(position)
        newPosition?.let { holder.bind(it) }
    }

    inner class PlaylistItemsViewHolder(private val binding: ItemPlaylistItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlaylistsModel.Item) {
            binding.tvVideoName.text = item.snippet.title
            if (!item.snippet.thumbnails.default.url.isNullOrEmpty())
                binding.imgVideo.load(item.snippet.thumbnails.default.url)
            itemView.setOnClickListener { onClickItem(item) }
        }

    }
}