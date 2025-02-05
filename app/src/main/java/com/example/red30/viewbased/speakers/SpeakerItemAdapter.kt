package com.example.red30.viewbased.speakers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.red30.data.Speaker
import com.example.red30.databinding.ViewSpeakerItemBinding

class SpeakerItemAdapter() : RecyclerView.Adapter<SpeakerItemAdapter.ViewHolder>() {

    private val items: MutableList<Speaker> = mutableListOf()

    inner class ViewHolder(val binding: ViewSpeakerItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(speaker: Speaker) {
            with(binding) {
                name.text = speaker.name
                title.text = speaker.title
                organization.text = speaker.organization
                bio.text = speaker.bio
                speakerImage.name = speaker.name
                speakerImage.imageUrl = speaker.imageUrl
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewSpeakerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<Speaker>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
