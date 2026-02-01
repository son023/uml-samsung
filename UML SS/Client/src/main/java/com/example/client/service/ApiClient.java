package com.example.client.service;

import com.example.client.dto.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

public class ApiClient {
    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    
    private final String baseUrl;
    private final OkHttpClient client;
    private final Gson gson;
    private String authToken;
    
    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        this.gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
    }
    
    public void setAuthToken(String token) {
        this.authToken = token;
    }
    
    public String getAuthToken() {
        return authToken;
    }
    
    public void clearAuthToken() {
        this.authToken = null;
    }
    
    // Login
    public AuthResponse login(LoginRequest loginRequest) throws IOException {
        String url = baseUrl + "/api/auth/login";
        String json = gson.toJson(loginRequest);
        
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            
            if (!response.isSuccessful()) {
                ErrorResponse error = gson.fromJson(responseBody, ErrorResponse.class);
                throw new IOException(error != null && error.getMessage() != null ? 
                        error.getMessage() : "Login failed: " + response.code());
            }
            
            AuthResponse authResponse = gson.fromJson(responseBody, AuthResponse.class);
            this.authToken = authResponse.getToken();
            return authResponse;
        }
    }
    
    // Create registration (Mentee registers with Mentor)
    public MentorMenteeRegistrationDTO createRegistration(MentorMenteeRegistrationDTO dto) throws IOException {
        String url = baseUrl + "/api/registrations";
        String json = gson.toJson(dto);
        
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", "Bearer " + authToken)
                .build();
        
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            
            if (!response.isSuccessful()) {
                ErrorResponse error = gson.fromJson(responseBody, ErrorResponse.class);
                throw new IOException(error != null && error.getMessage() != null ? 
                        error.getMessage() : "Registration failed: " + response.code());
            }
            
            return gson.fromJson(responseBody, MentorMenteeRegistrationDTO.class);
        }
    }
    
    // Get all registrations with pagination
    public PageResponse<MentorMenteeRegistrationDTO> getAllRegistrations(
            int page, int size, String search, Long mentorId, Long menteeId, String status) throws IOException {
        
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl + "/api/registrations").newBuilder();
        urlBuilder.addQueryParameter("page", String.valueOf(page));
        urlBuilder.addQueryParameter("size", String.valueOf(size));
        
        if (search != null && !search.isEmpty()) {
            urlBuilder.addQueryParameter("search", search);
        }
        if (mentorId != null) {
            urlBuilder.addQueryParameter("mentorId", String.valueOf(mentorId));
        }
        if (menteeId != null) {
            urlBuilder.addQueryParameter("menteeId", String.valueOf(menteeId));
        }
        if (status != null && !status.isEmpty()) {
            urlBuilder.addQueryParameter("status", status);
        }
        
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .addHeader("Authorization", "Bearer " + authToken)
                .build();
        
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            
            if (!response.isSuccessful()) {
                ErrorResponse error = gson.fromJson(responseBody, ErrorResponse.class);
                throw new IOException(error != null && error.getMessage() != null ? 
                        error.getMessage() : "Failed to fetch registrations: " + response.code());
            }
            
            Type type = new TypeToken<PageResponse<MentorMenteeRegistrationDTO>>(){}.getType();
            return gson.fromJson(responseBody, type);
        }
    }
    
    // Get registration by ID
    public MentorMenteeRegistrationDTO getRegistrationById(Long id) throws IOException {
        String url = baseUrl + "/api/registrations/" + id;
        
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization", "Bearer " + authToken)
                .build();
        
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            
            if (!response.isSuccessful()) {
                ErrorResponse error = gson.fromJson(responseBody, ErrorResponse.class);
                throw new IOException(error != null && error.getMessage() != null ? 
                        error.getMessage() : "Failed to fetch registration: " + response.code());
            }
            
            return gson.fromJson(responseBody, MentorMenteeRegistrationDTO.class);
        }
    }
    
    // Delete registration
    public void deleteRegistration(Long id) throws IOException {
        String url = baseUrl + "/api/registrations/" + id;
        
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .addHeader("Authorization", "Bearer " + authToken)
                .build();
        
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String responseBody = response.body() != null ? response.body().string() : "";
                ErrorResponse error = gson.fromJson(responseBody, ErrorResponse.class);
                throw new IOException(error != null && error.getMessage() != null ? 
                        error.getMessage() : "Failed to delete registration: " + response.code());
            }
        }
    }
    
    // Get all mentors (chưa đăng ký nếu có menteeId)
    public java.util.List<MentorDTO> getAllMentors(Long menteeId) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl + "/api/mentors").newBuilder();
        if (menteeId != null) {
            urlBuilder.addQueryParameter("menteeId", String.valueOf(menteeId));
        }
        
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .addHeader("Authorization", "Bearer " + authToken)
                .build();
        
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            
            if (!response.isSuccessful()) {
                ErrorResponse error = gson.fromJson(responseBody, ErrorResponse.class);
                throw new IOException(error != null && error.getMessage() != null ? 
                        error.getMessage() : "Failed to fetch mentors: " + response.code());
            }
            
            Type type = new TypeToken<java.util.List<MentorDTO>>(){}.getType();
            return gson.fromJson(responseBody, type);
        }
    }
    
    // Get all mentees
    public java.util.List<MenteeDTO> getAllMentees() throws IOException {
        String url = baseUrl + "/api/mentees";
        
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization", "Bearer " + authToken)
                .build();
        
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            
            if (!response.isSuccessful()) {
                ErrorResponse error = gson.fromJson(responseBody, ErrorResponse.class);
                throw new IOException(error != null && error.getMessage() != null ? 
                        error.getMessage() : "Failed to fetch mentees: " + response.code());
            }
            
            Type type = new TypeToken<java.util.List<MenteeDTO>>(){}.getType();
            return gson.fromJson(responseBody, type);
        }
    }
    
    // Logout
    public void logout() throws IOException {
        String url = baseUrl + "/api/auth/logout";
        
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create("", null))
                .addHeader("Authorization", "Bearer " + authToken)
                .build();
        
        try (Response response = client.newCall(request).execute()) {
            clearAuthToken();
        }
    }
}
