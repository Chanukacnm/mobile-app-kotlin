package com.example.servicebooking.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.servicebooking.databinding.ItemServiceBinding
import com.example.servicebooking.models.Service

class PopularServicesAdapter(
    private val onServiceClick: (Service) -> Unit
) : ListAdapter<Service, PopularServicesAdapter.ServiceViewHolder>(ServiceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = ItemServiceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ServiceViewHolder(
        private val binding: ItemServiceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(service: Service) {
            binding.apply {
                textServiceName.text = service.name
                iconService.setImageResource(service.iconRes)
                
                // Set background color
                cardService.setCardBackgroundColor(
                    ContextCompat.getColor(itemView.context, service.backgroundColorRes)
                )

                root.setOnClickListener {
                    onServiceClick(service)
                }
            }
        }
    }

    private class ServiceDiffCallback : DiffUtil.ItemCallback<Service>() {
        override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
            return oldItem == newItem
        }
    }
}
