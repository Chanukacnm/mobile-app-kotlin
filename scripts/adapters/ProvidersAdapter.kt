package com.example.servicebooking.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.servicebooking.R
import com.example.servicebooking.databinding.ItemProviderBinding
import com.example.servicebooking.models.Provider

class ProvidersAdapter(
    private val onProviderClick: (Provider) -> Unit
) : ListAdapter<Provider, ProvidersAdapter.ProviderViewHolder>(ProviderDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProviderViewHolder {
        val binding = ItemProviderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProviderViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ProviderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class ProviderViewHolder(
        private val binding: ItemProviderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(provider: Provider) {
            binding.apply {
                textProviderName.text = provider.name
                textServiceType.text = provider.service
                textRating.text = provider.rating.toString()
                textReviewCount.text = "(${provider.reviewCount})"
                textDistance.text = provider.distance
                textProviderInitial.text = provider.initial
                
                // Set availability status
                if (provider.isAvailable) {
                    chipAvailability.text = "Available"
                    chipAvailability.setChipBackgroundColorResource(R.color.green_100)
                    chipAvailability.setTextColor(
                        ContextCompat.getColor(root.context, R.color.green_700)
                    )
                } else {
                    chipAvailability.text = "Busy"
                    chipAvailability.setChipBackgroundColorResource(R.color.red_100)
                    chipAvailability.setTextColor(
                        ContextCompat.getColor(root.context, R.color.red_700)
                    )
                }
                
                root.setOnClickListener {
                    onProviderClick(provider)
                }
            }
        }
    }
    
    private class ProviderDiffCallback : DiffUtil.ItemCallback<Provider>() {
        override fun areItemsTheSame(oldItem: Provider, newItem: Provider): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: Provider, newItem: Provider): Boolean {
            return oldItem == newItem
        }
    }
}
