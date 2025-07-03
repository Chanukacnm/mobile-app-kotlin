package com.sevalk.presentation.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sevalk.R
import com.sevalk.databinding.ItemBookingBinding
import com.sevalk.data.models.Booking
import com.sevalk.data.models.BookingStatus

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
                
                // Set status color and text
                when (booking.status) {
                    BookingStatus.PENDING -> {
                        chipStatus.text = "Pending"
                        chipStatus.setChipBackgroundColorResource(R.color.orange_100)
                        chipStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.orange_600))
                    }
                    BookingStatus.CONFIRMED -> {
                        chipStatus.text = "Confirmed"
                        chipStatus.setChipBackgroundColorResource(R.color.green_100)
                        chipStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.green_600))
                    }
                    BookingStatus.IN_PROGRESS -> {
                        chipStatus.text = "In Progress"
                        chipStatus.setChipBackgroundColorResource(R.color.blue_100)
                        chipStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.blue_600))
                    }
                    BookingStatus.COMPLETED -> {
                        chipStatus.text = "Completed"
                        chipStatus.setChipBackgroundColorResource(R.color.green_100)
                        chipStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.green_600))
                    }
                    BookingStatus.CANCELLED -> {
                        chipStatus.text = "Cancelled"
                        chipStatus.setChipBackgroundColorResource(R.color.red_100)
                        chipStatus.setTextColor(ContextCompat.getColor(itemView.context, R.color.red_600))
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
