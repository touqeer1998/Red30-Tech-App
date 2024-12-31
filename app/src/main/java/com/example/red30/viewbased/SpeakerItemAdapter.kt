package com.example.red30.viewbased

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.example.red30.data.Speaker
import com.example.red30.databinding.ViewSpeakerItemBinding

class SpeakerItemAdapter(
    val items: List<Speaker>,
    val itemClickListener: (Speaker) -> Unit
) : RecyclerView.Adapter<SpeakerItemAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ViewSpeakerItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewSpeakerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val speaker = items[position]
        with(holder.binding) {
            name.text = speaker.name
            title.text = speaker.title
            organization.text = speaker.organization
            bio.text = speaker.bio

            imageView.load(speaker.imageUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }

            root.setOnClickListener {
                itemClickListener(speaker)
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
