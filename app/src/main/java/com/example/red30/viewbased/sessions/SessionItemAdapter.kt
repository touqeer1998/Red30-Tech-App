package com.example.red30.viewbased.sessions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.red30.data.SessionInfo
import com.example.red30.databinding.ViewSessionItemBinding

class SessionItemAdapter(
    val onSessionItemClick: (Int) -> Unit,
    val onFavoriteClick: (Int) -> Unit,
) : RecyclerView.Adapter<SessionItemAdapter.ViewHolder>() {
    private val items: MutableList<SessionInfo> = mutableListOf()

    inner class ViewHolder(val binding: ViewSessionItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(sessionInfo: SessionInfo) {
            val session = sessionInfo.session
            val speaker = sessionInfo.speaker

            with(binding) {
                name.text = session.name
                track.text = session.track
                roomName.text = session.roomName
                speakerName.text = speaker.name
                favorite.isSelected = sessionInfo.isFavorite
                speakerImage.name = speaker.name
                speakerImage.imageUrl = speaker.imageUrl

                favorite.setOnClickListener {
                    onFavoriteClick(sessionInfo.session.id)
                }

                root.setOnClickListener {
                    onSessionItemClick(sessionInfo.session.id)
                }
            }
        }

        fun bind(sessionInfo: SessionInfo, payloads: List<Any>) {
            val bundle = payloads[0] as Bundle
            if (bundle.containsKey("isFavorite")) {
                binding.favorite.isSelected = sessionInfo.isFavorite
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewSessionItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) return onBindViewHolder(holder, position)

        holder.bind(items[position], payloads)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<SessionInfo>) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = items.size

            override fun getNewListSize(): Int = newItems.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition].session.id == newItems[newItemPosition].session.id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition] == newItems[newItemPosition]
            }

            override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                val oldItem = items[oldItemPosition]
                val newItem = newItems[newItemPosition]
                val diffBundle = Bundle().apply {
                    if (oldItem.isFavorite != newItem.isFavorite) {
                        putBoolean("isFavorite", newItem.isFavorite)
                    }
                }

                return if (diffBundle.size() == 0) null else diffBundle
            }
        })

        items.clear()
        items.addAll(newItems)

        diffResult.dispatchUpdatesTo(this@SessionItemAdapter)
    }
}