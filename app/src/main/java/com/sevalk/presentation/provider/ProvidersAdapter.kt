package com.sevalk.presentation.provider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sevalk.R
import com.sevalk.databinding.ItemProviderBinding
import com.sevalk.data.models.Provider

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
                textRating.text = String.format("%.1f", provider.rating)
                textReviewCount.text = "(${provider.reviewCount})"
                textDistance.text = provider.distance
                textProviderInitial.text = provider.initial
                
                // Set availability indicator
                indicatorAvailable.setColorFilter(
                    ContextCompat.getColor(
                        itemView.context,
                        if (provider.isAvailable) R.color.green_500 else R.color.gray_400
                    )
                )

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
