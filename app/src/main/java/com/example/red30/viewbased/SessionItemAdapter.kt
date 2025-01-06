package com.example.red30.viewbased

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.red30.data.SessionInfo
import com.example.red30.data.initial
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
                speakerImage.letter = speaker.initial
                speakerImage.imageUrl = speaker.imageUrl

                favorite.setOnClickListener {
                    onFavoriteClick(sessionInfo.session.id)
                }

                root.setOnClickListener {
                    onSessionItemClick(sessionInfo.session.id)
                }
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

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<SessionInfo>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
