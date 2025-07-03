package com.example.servicebooking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.servicebooking.adapters.BookingAdapter
import com.example.servicebooking.adapters.PopularServicesAdapter
import com.example.servicebooking.adapters.ProvidersAdapter
import com.example.servicebooking.databinding.ActivityMainBinding
import com.example.servicebooking.models.Booking
import com.example.servicebooking.models.BookingStatus
import com.example.servicebooking.models.Provider
import com.example.servicebooking.models.Service
import com.example.servicebooking.repository.ServiceRepository
import com.example.servicebooking.repository.BookingRepository
import com.example.servicebooking.repository.ProviderRepository
import com.example.servicebooking.data.local.DatabaseHelper
import com.example.servicebooking.data.remote.ApiClient
import com.example.servicebooking.viewmodel.MainViewModel
import com.example.servicebooking.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var servicesAdapter: PopularServicesAdapter
    private lateinit var bookingAdapter: BookingAdapter
    private lateinit var providersAdapter: ProvidersAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupViewModel()
        setupUI()
        loadData()
        setupNavigation()
    }
    
    private fun setupViewModel() {
        val databaseHelper = DatabaseHelper(this)
        val apiClient = ApiClient()
        
        val serviceRepository = ServiceRepository(apiClient, databaseHelper)
        val bookingRepository = BookingRepository(apiClient, databaseHelper)
        val providerRepository = ProviderRepository(apiClient, databaseHelper)
        
        val factory = MainViewModelFactory(serviceRepository, bookingRepository, providerRepository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }
    
    private fun setupUI() {
        // Setup Popular Services RecyclerView
        servicesAdapter = PopularServicesAdapter { service ->
            onServiceClicked(service)
        }
        binding.recyclerPopularServices.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            adapter = servicesAdapter
        }
        
        // Setup Bookings RecyclerView
        bookingAdapter = BookingAdapter { booking ->
            onBookingClicked(booking)
        }
        binding.recyclerBookings.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = bookingAdapter
        }
        
        // Setup Providers RecyclerView
        providersAdapter = ProvidersAdapter { provider ->
            onProviderClicked(provider)
        }
        binding.recyclerProviders.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = providersAdapter
        }
        
        // Setup click listeners
        binding.btnFindServices.setOnClickListener {
            // Navigate to services screen
        }
        
        binding.btnMyBusiness.setOnClickListener {
            // Navigate to business screen
        }
        
        binding.textViewAllBookings.setOnClickListener {
            // Navigate to all bookings
        }
        
        binding.textSeeAllProviders.setOnClickListener {
            // Navigate to all providers
        }
        
        // Setup bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_search -> {
                    // Navigate to search
                    true
                }
                R.id.nav_bookings -> {
                    // Navigate to bookings
                    true
                }
                R.id.nav_messages -> {
                    // Navigate to messages
                    true
                }
                R.id.nav_profile -> {
                    // Navigate to profile
                    true
                }
                else -> false
            }
        }
    }
    
    private fun loadData() {
        // Load popular services
        val services = listOf(
            Service("1", "Plumbing", R.drawable.ic_plumbing, R.color.blue_100),
            Service("2", "Electrical", R.drawable.ic_electrical, R.color.orange_100),
            Service("3", "Cleaning", R.drawable.ic_cleaning, R.color.green_100),
            Service("4", "Auto Repair", R.drawable.ic_auto_repair, R.color.red_100),
            Service("5", "Tutoring", R.drawable.ic_tutoring, R.color.purple_100),
            Service("6", "Beauty", R.drawable.ic_beauty, R.color.pink_100)
        )
        servicesAdapter.submitList(services)
        
        // Load bookings
        val bookings = listOf(
            Booking(
                id = "1",
                service = "Plumbing repair",
                provider = "Mike's Plumbing",
                time = "Today, 2:00 PM",
                status = BookingStatus.CONFIRMED,
                providerInitial = "M"
            ),
            Booking(
                id = "2",
                service = "Math Tutoring",
                provider = "Lisa Chen",
                time = "Tomorrow, 4:00 PM",
                status = BookingStatus.PENDING,
                providerInitial = "L"
            )
        )
        bookingAdapter.submitList(bookings)
        
        // Load providers
        val providers = listOf(
            Provider(
                id = "1",
                name = "Mike's Plumbing",
                service = "Plumbing",
                rating = 4.8f,
                reviewCount = 127,
                distance = "0.3km",
                isAvailable = true,
                initial = "M"
            ),
            Provider(
                id = "2",
                name = "Sarah Electronics",
                service = "Electrical Work",
                rating = 4.4f,
                reviewCount = 89,
                distance = "1.2km",
                isAvailable = true,
                initial = "S"
            )
        )
        providersAdapter.submitList(providers)
    }
    
    private fun onServiceClicked(service: Service) {
        // Handle service selection
    }
    
    private fun onBookingClicked(booking: Booking) {
        // Handle booking selection
    }
    
    private fun onProviderClicked(provider: Provider) {
        // Handle provider selection
    }
    
    private fun setupNavigation() {
        try {
            val navController = findNavController(R.id.nav_host_fragment)
            binding.bottomNavigation.setupWithNavController(navController)
        } catch (e: Exception) {
            // Handle navigation setup error
        }
    }
}
