package com.example.servicebooking.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.servicebooking.models.Service;
import com.example.servicebooking.models.Booking;
import com.example.servicebooking.models.Provider;
import com.example.servicebooking.models.BookingStatus;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    
    private static final String DATABASE_NAME = "service_booking.db";
    private static final int DATABASE_VERSION = 1;
    
    // Table names
    private static final String TABLE_SERVICES = "services";
    private static final String TABLE_BOOKINGS = "bookings";
    private static final String TABLE_PROVIDERS = "providers";
    
    // Services table columns
    private static final String SERVICES_ID = "id";
    private static final String SERVICES_NAME = "name";
    private static final String SERVICES_ICON_RES = "icon_res";
    private static final String SERVICES_BACKGROUND_COLOR_RES = "background_color_res";
    private static final String SERVICES_DESCRIPTION = "description";
    private static final String SERVICES_PRICE_RANGE = "price_range";
    
    // Bookings table columns
    private static final String BOOKINGS_ID = "id";
    private static final String BOOKINGS_SERVICE = "service";
    private static final String BOOKINGS_PROVIDER = "provider";
    private static final String BOOKINGS_TIME = "time";
    private static final String BOOKINGS_STATUS = "status";
    private static final String BOOKINGS_PROVIDER_INITIAL = "provider_initial";
    private static final String BOOKINGS_USER_ID = "user_id";
    private static final String BOOKINGS_CREATED_AT = "created_at";
    
    // Providers table columns
    private static final String PROVIDERS_ID = "id";
    private static final String PROVIDERS_NAME = "name";
    private static final String PROVIDERS_SERVICE = "service";
    private static final String PROVIDERS_RATING = "rating";
    private static final String PROVIDERS_REVIEW_COUNT = "review_count";
    private static final String PROVIDERS_DISTANCE = "distance";
    private static final String PROVIDERS_IS_AVAILABLE = "is_available";
    private static final String PROVIDERS_INITIAL = "initial";
    private static final String PROVIDERS_PROFILE_IMAGE_URL = "profile_image_url";
    private static final String PROVIDERS_PHONE = "phone";
    private static final String PROVIDERS_EMAIL = "email";
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create services table
        String createServicesTable = "CREATE TABLE " + TABLE_SERVICES + "("
                + SERVICES_ID + " TEXT PRIMARY KEY,"
                + SERVICES_NAME + " TEXT NOT NULL,"
                + SERVICES_ICON_RES + " INTEGER,"
                + SERVICES_BACKGROUND_COLOR_RES + " INTEGER,"
                + SERVICES_DESCRIPTION + " TEXT,"
                + SERVICES_PRICE_RANGE + " TEXT"
                + ")";
        db.execSQL(createServicesTable);
        
        // Create bookings table
        String createBookingsTable = "CREATE TABLE " + TABLE_BOOKINGS + "("
                + BOOKINGS_ID + " TEXT PRIMARY KEY,"
                + BOOKINGS_SERVICE + " TEXT NOT NULL,"
                + BOOKINGS_PROVIDER + " TEXT NOT NULL,"
                + BOOKINGS_TIME + " TEXT NOT NULL,"
                + BOOKINGS_STATUS + " TEXT NOT NULL,"
                + BOOKINGS_PROVIDER_INITIAL + " TEXT,"
                + BOOKINGS_USER_ID + " TEXT,"
                + BOOKINGS_CREATED_AT + " INTEGER"
                + ")";
        db.execSQL(createBookingsTable);
        
        // Create providers table
        String createProvidersTable = "CREATE TABLE " + TABLE_PROVIDERS + "("
                + PROVIDERS_ID + " TEXT PRIMARY KEY,"
                + PROVIDERS_NAME + " TEXT NOT NULL,"
                + PROVIDERS_SERVICE + " TEXT NOT NULL,"
                + PROVIDERS_RATING + " REAL,"
                + PROVIDERS_REVIEW_COUNT + " INTEGER,"
                + PROVIDERS_DISTANCE + " TEXT,"
                + PROVIDERS_IS_AVAILABLE + " INTEGER,"
                + PROVIDERS_INITIAL + " TEXT,"
                + PROVIDERS_PROFILE_IMAGE_URL + " TEXT,"
                + PROVIDERS_PHONE + " TEXT,"
                + PROVIDERS_EMAIL + " TEXT"
                + ")";
        db.execSQL(createProvidersTable);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROVIDERS);
        onCreate(db);
    }
    
    // Services CRUD operations
    public void insertServices(List<Service> services) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (Service service : services) {
                ContentValues values = new ContentValues();
                values.put(SERVICES_ID, service.getId());
                values.put(SERVICES_NAME, service.getName());
                values.put(SERVICES_ICON_RES, service.getIconRes());
                values.put(SERVICES_BACKGROUND_COLOR_RES, service.getBackgroundColorRes());
                values.put(SERVICES_DESCRIPTION, service.getDescription());
                values.put(SERVICES_PRICE_RANGE, service.getPriceRange());
                
                db.insertOrThrow(TABLE_SERVICES, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
    
    public List<Service> getPopularServices() {
        List<Service> services = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        String selectQuery = "SELECT * FROM " + TABLE_SERVICES + " LIMIT 6";
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
                Service service = new Service(
                    cursor.getString(cursor.getColumnIndexOrThrow(SERVICES_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(SERVICES_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(SERVICES_ICON_RES)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(SERVICES_BACKGROUND_COLOR_RES)),
                    cursor.getString(cursor.getColumnIndexOrThrow(SERVICES_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(SERVICES_PRICE_RANGE))
                );
                services.add(service);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return services;
    }
    
    public Service getServiceById(String serviceId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Service service = null;
        
        String selectQuery = "SELECT * FROM " + TABLE_SERVICES + " WHERE " + SERVICES_ID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{serviceId});
        
        if (cursor.moveToFirst()) {
            service = new Service(
                cursor.getString(cursor.getColumnIndexOrThrow(SERVICES_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(SERVICES_NAME)),
                cursor.getInt(cursor.getColumnIndexOrThrow(SERVICES_ICON_RES)),
                cursor.getInt(cursor.getColumnIndexOrThrow(SERVICES_BACKGROUND_COLOR_RES)),
                cursor.getString(cursor.getColumnIndexOrThrow(SERVICES_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(SERVICES_PRICE_RANGE))
            );
        }
        
        cursor.close();
        db.close();
        return service;
    }
    
    // Bookings CRUD operations
    public void insertBookings(List<Booking> bookings) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (Booking booking : bookings) {
                insertBooking(booking, db);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
    
    public void insertBooking(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        insertBooking(booking, db);
        db.close();
    }
    
    private void insertBooking(Booking booking, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(BOOKINGS_ID, booking.getId());
        values.put(BOOKINGS_SERVICE, booking.getService());
        values.put(BOOKINGS_PROVIDER, booking.getProvider());
        values.put(BOOKINGS_TIME, booking.getTime());
        values.put(BOOKINGS_STATUS, booking.getStatus().name());
        values.put(BOOKINGS_PROVIDER_INITIAL, booking.getProviderInitial());
        values.put(BOOKINGS_USER_ID, booking.getUserId());
        values.put(BOOKINGS_CREATED_AT, booking.getCreatedAt());
        
        db.insertOrThrow(TABLE_BOOKINGS, null, values);
    }
    
    public List<Booking> getUserBookings() {
        List<Booking> bookings = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        String selectQuery = "SELECT * FROM " + TABLE_BOOKINGS + " ORDER BY " + BOOKINGS_CREATED_AT + " DESC";
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
                Booking booking = new Booking(
                    cursor.getString(cursor.getColumnIndexOrThrow(BOOKINGS_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(BOOKINGS_SERVICE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(BOOKINGS_PROVIDER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(BOOKINGS_TIME)),
                    BookingStatus.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(BOOKINGS_STATUS))),
                    cursor.getString(cursor.getColumnIndexOrThrow(BOOKINGS_PROVIDER_INITIAL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(BOOKINGS_USER_ID)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(BOOKINGS_CREATED_AT))
                );
                bookings.add(booking);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return bookings;
    }
    
    // Providers CRUD operations
    public void insertProviders(List<Provider> providers) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (Provider provider : providers) {
                ContentValues values = new ContentValues();
                values.put(PROVIDERS_ID, provider.getId());
                values.put(PROVIDERS_NAME, provider.getName());
                values.put(PROVIDERS_SERVICE, provider.getService());
                values.put(PROVIDERS_RATING, provider.getRating());
                values.put(PROVIDERS_REVIEW_COUNT, provider.getReviewCount());
                values.put(PROVIDERS_DISTANCE, provider.getDistance());
                values.put(PROVIDERS_IS_AVAILABLE, provider.isAvailable() ? 1 : 0);
                values.put(PROVIDERS_INITIAL, provider.getInitial());
                values.put(PROVIDERS_PROFILE_IMAGE_URL, provider.getProfileImageUrl());
                values.put(PROVIDERS_PHONE, provider.getPhone());
                values.put(PROVIDERS_EMAIL, provider.getEmail());
                
                db.insertOrThrow(TABLE_PROVIDERS, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
    
    public List<Provider> getNearbyProviders() {
        List<Provider> providers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        String selectQuery = "SELECT * FROM " + TABLE_PROVIDERS + " ORDER BY " + PROVIDERS_RATING + " DESC";
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
                Provider provider = new Provider(
                    cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_SERVICE)),
                    cursor.getFloat(cursor.getColumnIndexOrThrow(PROVIDERS_RATING)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(PROVIDERS_REVIEW_COUNT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_DISTANCE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(PROVIDERS_IS_AVAILABLE)) == 1,
                    cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_INITIAL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_PROFILE_IMAGE_URL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_PHONE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_EMAIL))
                );
                providers.add(provider);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return providers;
    }
    
    public List<Service> searchServices(String query) {
        List<Service> services = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        String selectQuery = "SELECT * FROM " + TABLE_SERVICES + 
                           " WHERE " + SERVICES_NAME + " LIKE ? OR " + SERVICES_DESCRIPTION + " LIKE ?";
        String searchPattern = "%" + query + "%";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{searchPattern, searchPattern});
        
        if (cursor.moveToFirst()) {
            do {
                Service service = new Service(
                    cursor.getString(cursor.getColumnIndexOrThrow(SERVICES_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(SERVICES_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(SERVICES_ICON_RES)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(SERVICES_BACKGROUND_COLOR_RES)),
                    cursor.getString(cursor.getColumnIndexOrThrow(SERVICES_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(SERVICES_PRICE_RANGE))
                );
                services.add(service);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return services;
    }
    
    public List<Provider> searchProviders(String query, String serviceType) {
        List<Provider> providers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM " + TABLE_PROVIDERS + " WHERE ");
        List<String> args = new ArrayList<>();
        
        queryBuilder.append("(").append(PROVIDERS_NAME).append(" LIKE ? OR ")
                   .append(PROVIDERS_SERVICE).append(" LIKE ?)");
        String searchPattern = "%" + query + "%";
        args.add(searchPattern);
        args.add(searchPattern);
        
        if (serviceType != null && !serviceType.isEmpty()) {
            queryBuilder.append(" AND ").append(PROVIDERS_SERVICE).append(" = ?");
            args.add(serviceType);
        }
        
        Cursor cursor = db.rawQuery(queryBuilder.toString(), args.toArray(new String[0]));
        
        if (cursor.moveToFirst()) {
            do {
                Provider provider = new Provider(
                    cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_SERVICE)),
                    cursor.getFloat(cursor.getColumnIndexOrThrow(PROVIDERS_RATING)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(PROVIDERS_REVIEW_COUNT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_DISTANCE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(PROVIDERS_IS_AVAILABLE)) == 1,
                    cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_INITIAL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_PROFILE_IMAGE_URL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_PHONE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_EMAIL))
                );
                providers.add(provider);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return providers;
    }
    
    public Provider getProviderById(String providerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Provider provider = null;
        
        String selectQuery = "SELECT * FROM " + TABLE_PROVIDERS + " WHERE " + PROVIDERS_ID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{providerId});
        
        if (cursor.moveToFirst()) {
            provider = new Provider(
                cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_SERVICE)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(PROVIDERS_RATING)),
                cursor.getInt(cursor.getColumnIndexOrThrow(PROVIDERS_REVIEW_COUNT)),
                cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_DISTANCE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(PROVIDERS_IS_AVAILABLE)) == 1,
                cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_INITIAL)),
                cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_PROFILE_IMAGE_URL)),
                cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_PHONE)),
                cursor.getString(cursor.getColumnIndexOrThrow(PROVIDERS_EMAIL))
            );
        }
        
        cursor.close();
        db.close();
        return provider;
    }
    
    public void updateBooking(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(BOOKINGS_SERVICE, booking.getService());
        values.put(BOOKINGS_PROVIDER, booking.getProvider());
        values.put(BOOKINGS_TIME, booking.getTime());
        values.put(BOOKINGS_STATUS, booking.getStatus().name());
        values.put(BOOKINGS_PROVIDER_INITIAL, booking.getProviderInitial());
        
        db.update(TABLE_BOOKINGS, values, BOOKINGS_ID + " = ?", new String[]{booking.getId()});
        db.close();
    }
    
    public void deleteBooking(String bookingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOKINGS, BOOKINGS_ID + " = ?", new String[]{bookingId});
        db.close();
    }
}
