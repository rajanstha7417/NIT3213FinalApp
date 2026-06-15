package com.example.nit3213.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nit3213.data.model.Entity
import com.example.nit3213.databinding.ItemEntityBinding

/**
 * RecyclerView Adapter: connects the list of entities to the RecyclerView and
 * decides how each row looks. The ViewHolder caches the row's views so
 * scrolling stays smooth.
 */
class EntityAdapter(
    private val items: List<Entity>,
    private val onClick: (Entity) -> Unit
) : RecyclerView.Adapter<EntityAdapter.EntityViewHolder>() {

    inner class EntityViewHolder(
        private val binding: ItemEntityBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: Entity) {
            binding.tvTitle.text = entity.title
            // Show the summary (everything except the description), one per line.
            binding.tvSubtitle.text = entity.summary
                .drop(1) // first field is already the title
                .joinToString("\n") { (key, value) -> "$key: $value" }
            binding.root.setOnClickListener { onClick(entity) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val binding = ItemEntityBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EntityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
