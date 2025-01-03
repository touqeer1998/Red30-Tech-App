package com.example.red30.viewbased

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.red30.data.SessionInfo
import com.example.red30.databinding.ViewSessionItemBinding

class SessionItemAdapter(
    val itemClickListener: (SessionInfo) -> Unit
) : RecyclerView.Adapter<SessionItemAdapter.ViewHolder>() {

    private val items: MutableList<SessionInfo> = mutableListOf()

    inner class ViewHolder(val binding: ViewSessionItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(sessionInfo: SessionInfo) {
            with(binding) {
                name.text = sessionInfo.session.name
                track.text = sessionInfo.session.track
                roomName.text = sessionInfo.session.roomName
                speakerName.text = sessionInfo.speaker.name

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
