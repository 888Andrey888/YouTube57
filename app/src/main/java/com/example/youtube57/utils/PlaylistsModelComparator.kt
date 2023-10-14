package com.example.youtube57.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.youtube57.data.model.PlaylistsModel

object PlaylistsModelComparator : DiffUtil.ItemCallback<PlaylistsModel.Item>() {
    override fun areItemsTheSame(oldItem: PlaylistsModel.Item, newItem: PlaylistsModel.Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PlaylistsModel.Item, newItem: PlaylistsModel.Item): Boolean {
        return oldItem == newItem
    }
}