package com.example.red30.viewbased

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.red30.databinding.ViewSessionItemBinding

class SessionItemAdapter(
    val items: List<String>,
    val itemClickListener: (String) -> Unit
) : RecyclerView.Adapter<SessionItemAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ViewSessionItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewSessionItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.binding.title.text = items[position]

        holder.binding.root.setOnClickListener {
            itemClickListener(items[position])
        }
    }

    override fun getItemCount(): Int = items.size
}
