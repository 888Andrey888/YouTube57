package com.example.youtube57.presentation.playlistitems

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.youtube57.data.model.PlaylistsModel
import com.example.youtube57.databinding.ItemPlaylistBinding
import com.example.youtube57.databinding.ItemPlaylistItemsBinding

class PlaylistItemsAdapter(private val onClickItem: (playlistItem: PlaylistsModel.Item) -> Unit) :
    RecyclerView.Adapter<PlaylistItemsAdapter.PlaylistItemsViewHolder>() {

    private var _list = mutableListOf<PlaylistsModel.Item>()
    private val list: List<PlaylistsModel.Item> get() = _list

    fun addData(playlistModelItem: List<PlaylistsModel.Item>) {
        _list.clear()
        _list.addAll(playlistModelItem)
        notifyItemRangeInserted(_list.size, playlistModelItem.size - _list.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PlaylistItemsViewHolder(
        ItemPlaylistItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PlaylistItemsViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class PlaylistItemsViewHolder(private val binding: ItemPlaylistItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlaylistsModel.Item) {
            binding.tvVideoName.text = item.snippet.title
            binding.imgVideo.load(item.snippet.thumbnails.default.url)
            itemView.setOnClickListener { onClickItem(item) }
        }

    }
}