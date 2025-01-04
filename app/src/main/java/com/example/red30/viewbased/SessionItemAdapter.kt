package com.example.red30.viewbased

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.example.red30.data.SessionInfo
import com.example.red30.data.initial
import com.example.red30.databinding.ViewSessionItemBinding

class SessionItemAdapter(
    val itemClickListener: (SessionInfo) -> Unit
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

                if (speaker.imageUrl.isNullOrEmpty()) {
                    speakerInitial.letter = speaker.initial
                    speakerInitial.visible()
                    speakerImage.invisible()
                } else {
                    speakerImage.load(speaker.imageUrl) {
                        crossfade(true)
                        transformations(CircleCropTransformation())
                    }
                    speakerInitial.invisible()
                    speakerImage.visible()
                }

                root.setOnClickListener {
                    itemClickListener(sessionInfo)
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
