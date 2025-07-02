package com.example.servicebooking.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.servicebooking.adapters.PopularServicesAdapter
import com.example.servicebooking.adapters.BookingAdapter
import com.example.servicebooking.adapters.ProvidersAdapter
import com.example.servicebooking.databinding.FragmentHomeBinding
import com.example.servicebooking.viewmodel.MainViewModel
import com.example.servicebooking.utils.LocationHelper
import com.example.servicebooking.utils.PermissionHelper

class HomeFragment : Fragment() {
    
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: MainViewModel by activityViewModels()
    
    private lateinit var servicesAdapter: PopularServicesAdapter
    private lateinit var bookingAdapter: BookingAdapter
    private lateinit var providersAdapter: ProvidersAdapter
    
    private lateinit var locationHelper: LocationHelper
    private lateinit var permissionHelper: PermissionHelper
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupHelpers()
        setupRecyclerViews()
        setupClickListeners()
        observeViewModel()
        loadData()
    }
    
    private fun setupHelpers() {
        locationHelper = LocationHelper(requireContext())
        permissionHelper = PermissionHelper(this)
    }
    
    private fun setupRecyclerViews() {
        // Popular Services
        servicesAdapter = PopularServicesAdapter { service ->
            viewModel.onServiceSelected(service)
        }
        binding.recyclerPopularServices.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = servicesAdapter
        }
        
        // Bookings
        bookingAdapter = BookingAdapter { booking ->
            viewModel.onBookingSelected(booking)
        }
        binding.recyclerBookings.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bookingAdapter
        }
        
        // Providers
        providersAdapter = ProvidersAdapter { provider ->
            viewModel.onProviderSelected(provider)
        }
        binding.recyclerProviders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = providersAdapter
        }
    }
    
    private fun setupClickListeners() {
        binding.apply {
            btnFindServices.setOnClickListener {
                viewModel.navigateToServices()
            }
            
            btnMyBusiness.setOnClickListener {
                viewModel.navigateToBusiness()
            }
            
            textViewAllBookings.setOnClickListener {
                viewModel.navigateToAllBookings()
            }
            
            textSeeAllProviders.setOnClickListener {
                viewModel.navigateToAllProviders()
            }
            
            btnNotifications.setOnClickListener {
                viewModel.navigateToNotifications()
            }
            
            btnProfile.setOnClickListener {
                viewModel.navigateToProfile()
            }
        }
    }
    
    private fun observeViewModel() {
        viewModel.apply {
            popularServices.observe(viewLifecycleOwner, Observer { services ->
                servicesAdapter.submitList(services)
            })
            
            userBookings.observe(viewLifecycleOwner, Observer { bookings ->
                bookingAdapter.submitList(bookings)
            })
            
            nearbyProviders.observe(viewLifecycleOwner, Observer { providers ->
                providersAdapter.submitList(providers)
            })
            
            userLocation.observe(viewLifecycleOwner, Observer { location ->
                binding.textLocation.text = location
            })
            
            isLoading.observe(viewLifecycleOwner, Observer { loading ->
                binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            })
            
            errorMessage.observe(viewLifecycleOwner, Observer { error ->
                error?.let {
                    // Show error message
                }
            })
        }
    }
    
    private fun loadData() {
        viewModel.loadHomeData()
        requestLocationPermission()
    }
    
    private fun requestLocationPermission() {
        permissionHelper.requestLocationPermission { granted ->
            if (granted) {
                locationHelper.getCurrentLocation { location ->
                    viewModel.updateUserLocation(location)
                }
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
