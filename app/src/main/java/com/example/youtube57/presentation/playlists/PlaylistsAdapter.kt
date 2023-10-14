package com.example.youtube57.presentation.playlists


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.youtube57.data.model.PlaylistsModel
import com.example.youtube57.databinding.ItemPlaylistBinding

class PlaylistsAdapter(
    diffUtilCallback: DiffUtil.ItemCallback<PlaylistsModel.Item>,
    private val onClickItem: (playlistItem: PlaylistsModel.Item) -> Unit
) : PagingDataAdapter<PlaylistsModel.Item, PlaylistsAdapter.PlaylistsViewHolder>(diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PlaylistsViewHolder(
        ItemPlaylistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: PlaylistsViewHolder, position: Int) {
        val newPosition = getItem(position)
        newPosition?.let { holder.bind(it) }
    }

    inner class PlaylistsViewHolder(private val binding: ItemPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: PlaylistsModel.Item) {
            binding.tvPlaylistName.text = item.snippet.title
            binding.tvPlaylistVideoCount.text =
                item.contentDetails.itemCount.toString() + " video series"
            if (!item.snippet.thumbnails.default.url.isNullOrEmpty())
                binding.imgPlaylist.load(item.snippet.thumbnails.default.url)
            itemView.setOnClickListener { onClickItem(item) }
        }
    }
}