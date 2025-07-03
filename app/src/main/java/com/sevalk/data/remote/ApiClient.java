package com.sevalk.data.remote;

import com.sevalk.data.models.Service;
import com.sevalk.data.models.Booking;
import com.sevalk.data.models.Provider;
import com.sevalk.data.models.BookingRequest;
import com.sevalk.data.models.BookingStatus;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class ApiClient {
    
    private static final String BASE_URL = "https://api.servicebooking.com/v1/";
    
    // Mock API methods - Replace with actual HTTP calls using Retrofit or OkHttp
    
    public List<Service> getPopularServices() throws Exception {
        // Simulate API call delay
        Thread.sleep(1000);
        
        // Mock data - replace with actual API call
        List<Service> services = new ArrayList<>();
        services.add(new Service("1", "Plumbing", android.R.drawable.ic_menu_manage, android.R.color.holo_blue_light, "Professional plumbing services", "$50-150"));
        services.add(new Service("2", "Electrical", android.R.drawable.ic_menu_manage, android.R.color.holo_orange_light, "Electrical repairs and installations", "$60-200"));
        services.add(new Service("3", "Cleaning", android.R.drawable.ic_menu_manage, android.R.color.holo_green_light, "Home and office cleaning", "$30-100"));
        services.add(new Service("4", "Auto Repair", android.R.drawable.ic_menu_manage, android.R.color.holo_red_light, "Car maintenance and repairs", "$80-300"));
        services.add(new Service("5", "Tutoring", android.R.drawable.ic_menu_manage, android.R.color.holo_purple, "Academic tutoring services", "$25-75"));
        services.add(new Service("6", "Beauty", android.R.drawable.ic_menu_manage, android.R.color.holo_orange_dark, "Beauty and grooming services", "$40-120"));
        
        return services;
    }
    
    public Service getServiceById(String serviceId) throws Exception {
        Thread.sleep(500);
        
        // Mock implementation
        List<Service> services = getPopularServices();
        for (Service service : services) {
            if (service.getId().equals(serviceId)) {
                return service;
            }
        }
        throw new Exception("Service not found");
    }
    
    public List<Service> searchServices(String query) throws Exception {
        Thread.sleep(800);
        
        List<Service> allServices = getPopularServices();
        List<Service> filteredServices = new ArrayList<>();
        
        for (Service service : allServices) {
            if (service.getName().toLowerCase().contains(query.toLowerCase()) ||
                service.getDescription().toLowerCase().contains(query.toLowerCase())) {
                filteredServices.add(service);
            }
        }
        
        return filteredServices;
    }
    
    public List<Booking> getUserBookings() throws Exception {
        Thread.sleep(1000);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking(
            UUID.randomUUID().toString(),
            "Plumbing repair",
            "Mike's Plumbing",
            "Today, 2:00 PM",
            BookingStatus.CONFIRMED,
            "M",
            "user123",
            System.currentTimeMillis()
        ));
        bookings.add(new Booking(
            UUID.randomUUID().toString(),
            "Math Tutoring",
            "Lisa Chen",
            "Tomorrow, 4:00 PM",
            BookingStatus.PENDING,
            "L",
            "user123",
            System.currentTimeMillis()
        ));
        
        return bookings;
    }
    
    public Booking createBooking(BookingRequest request) throws Exception {
        Thread.sleep(1500);
        
        // Mock booking creation
        return new Booking(
            UUID.randomUUID().toString(),
            request.getServiceName(),
            request.getProviderName(),
            request.getScheduledTime(),
            BookingStatus.PENDING,
            request.getProviderName().substring(0, 1).toUpperCase(),
            request.getUserId(),
            System.currentTimeMillis()
        );
    }
    
    public Booking updateBookingStatus(String bookingId, String status) throws Exception {
        Thread.sleep(800);
        
        // Mock status update
        List<Booking> bookings = getUserBookings();
        for (Booking booking : bookings) {
            if (booking.getId().equals(bookingId)) {
                return new Booking(
                    booking.getId(),
                    booking.getService(),
                    booking.getProvider(),
                    booking.getTime(),
                    BookingStatus.valueOf(status.toUpperCase()),
                    booking.getProviderInitial(),
                    booking.getUserId(),
                    booking.getCreatedAt()
                );
            }
        }
        throw new Exception("Booking not found");
    }
    
    public boolean cancelBooking(String bookingId) throws Exception {
        Thread.sleep(1000);
        
        // Mock cancellation
        return true;
    }
    
    public List<Provider> getNearbyProviders() throws Exception {
        return getNearbyProviders(null);
    }
    
    public List<Provider> getNearbyProviders(String location) throws Exception {
        Thread.sleep(1200);
        
        List<Provider> providers = new ArrayList<>();
        providers.add(new Provider(
            "p1",
            "Mike's Plumbing",
            "Plumbing",
            4.8f,
            127,
            "0.3km",
            true,
            "M",
            null,
            "+1234567890",
            "mike@plumbing.com"
        ));
        providers.add(new Provider(
            "p2",
            "Sarah Electronics",
            "Electrical Work",
            4.4f,
            89,
            "1.2km",
            true,
            "S",
            null,
            "+1234567891",
            "sarah@electronics.com"
        ));
        
        return providers;
    }
    
    public Provider getProviderById(String providerId) throws Exception {
        Thread.sleep(600);
        
        List<Provider> providers = getNearbyProviders();
        for (Provider provider : providers) {
            if (provider.getId().equals(providerId)) {
                return provider;
            }
        }
        throw new Exception("Provider not found");
    }
    
    public List<Provider> searchProviders(String query, String serviceType) throws Exception {
        Thread.sleep(900);
        
        List<Provider> allProviders = getNearbyProviders();
        List<Provider> filteredProviders = new ArrayList<>();
        
        for (Provider provider : allProviders) {
            boolean matchesQuery = provider.getName().toLowerCase().contains(query.toLowerCase()) ||
                                 provider.getService().toLowerCase().contains(query.toLowerCase());
            boolean matchesServiceType = serviceType == null || 
                                       provider.getService().equalsIgnoreCase(serviceType);
            
            if (matchesQuery && matchesServiceType) {
                filteredProviders.add(provider);
            }
        }
        
        return filteredProviders;
    }
}
