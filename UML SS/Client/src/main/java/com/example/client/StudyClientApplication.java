package com.example.client;

import com.example.client.service.ApiClient;
import com.example.client.ui.LoginFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class StudyClientApplication {
    private static final Logger logger = LoggerFactory.getLogger(StudyClientApplication.class);
    
    // Default API base URL - can be configured
    private static final String DEFAULT_API_URL = "http://localhost:8080";
    
    public static void main(String[] args) {
        // Set Look and Feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            logger.warn("Failed to set system look and feel", e);
        }
        
        // Get API URL from command line arguments or use default
        String apiUrl = DEFAULT_API_URL;
        if (args.length > 0) {
            apiUrl = args[0];
            logger.info("Using API URL from arguments: {}", apiUrl);
        } else {
            logger.info("Using default API URL: {}", apiUrl);
        }
        
        final String finalApiUrl = apiUrl;
        
        // Start the application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                logger.info("Starting Study Client Application...");
                
                // Create API client
                ApiClient apiClient = new ApiClient(finalApiUrl);
                
                // Show splash message
                JOptionPane.showMessageDialog(null,
                        "Welcome to Mentor-Mentee Study System\n\n" +
                        "API Server: " + finalApiUrl + "\n\n" +
                        "Please login to continue.",
                        "Study System",
                        JOptionPane.INFORMATION_MESSAGE);
                
                // Create and show login frame
                LoginFrame loginFrame = new LoginFrame(apiClient);
                loginFrame.setVisible(true);
                
                logger.info("Study Client Application started successfully");
                
            } catch (Exception e) {
                logger.error("Failed to start application", e);
                JOptionPane.showMessageDialog(null,
                        "Failed to start application: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}
