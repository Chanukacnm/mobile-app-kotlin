package com.example.servicebooking.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.servicebooking.R
import com.example.servicebooking.databinding.ItemBookingBinding
import com.example.servicebooking.models.Booking
import com.example.servicebooking.models.BookingStatus

class BookingAdapter(
    private val onBookingClick: (Booking) -> Unit
) : ListAdapter<Booking, BookingAdapter.BookingViewHolder>(BookingDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val binding = ItemBookingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookingViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class BookingViewHolder(
        private val binding: ItemBookingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(booking: Booking) {
            binding.apply {
                textServiceName.text = booking.service
                textProviderName.text = booking.provider
                textBookingTime.text = booking.time
                textProviderInitial.text = booking.providerInitial
                
                // Set status badge
                when (booking.status) {
                    BookingStatus.CONFIRMED -> {
                        chipStatus.text = "confirmed"
                        chipStatus.setChipBackgroundColorResource(R.color.green_100)
                        chipStatus.setTextColor(
                            ContextCompat.getColor(root.context, R.color.green_700)
                        )
                    }
                    BookingStatus.PENDING -> {
                        chipStatus.text = "pending"
                        chipStatus.setChipBackgroundColorResource(R.color.yellow_100)
                        chipStatus.setTextColor(
                            ContextCompat.getColor(root.context, R.color.yellow_700)
                        )
                    }
                    BookingStatus.COMPLETED -> {
                        chipStatus.text = "completed"
                        chipStatus.setChipBackgroundColorResource(R.color.blue_100)
                        chipStatus.setTextColor(
                            ContextCompat.getColor(root.context, R.color.blue_700)
                        )
                    }
                    BookingStatus.CANCELLED -> {
                        chipStatus.text = "cancelled"
                        chipStatus.setChipBackgroundColorResource(R.color.red_100)
                        chipStatus.setTextColor(
                            ContextCompat.getColor(root.context, R.color.red_700)
                        )
                    }
                }
                
                root.setOnClickListener {
                    onBookingClick(booking)
                }
            }
        }
    }
    
    private class BookingDiffCallback : DiffUtil.ItemCallback<Booking>() {
        override fun areItemsTheSame(oldItem: Booking, newItem: Booking): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: Booking, newItem: Booking): Boolean {
            return oldItem == newItem
        }
    }
}
