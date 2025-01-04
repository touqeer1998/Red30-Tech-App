package com.example.red30.viewbased

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.example.red30.data.Speaker
import com.example.red30.data.initial
import com.example.red30.databinding.ViewSpeakerItemBinding

class SpeakerItemAdapter(
    val itemClickListener: (Speaker) -> Unit
) : RecyclerView.Adapter<SpeakerItemAdapter.ViewHolder>() {

    private val items: MutableList<Speaker> = mutableListOf()

    inner class ViewHolder(val binding: ViewSpeakerItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(speaker: Speaker) {
            with(binding) {
                name.text = speaker.name
                title.text = speaker.title
                organization.text = speaker.organization
                bio.text = speaker.bio

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
                    itemClickListener(speaker)
                }
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
