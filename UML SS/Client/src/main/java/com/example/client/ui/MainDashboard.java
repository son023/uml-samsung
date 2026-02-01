package com.example.client.ui;

import com.example.client.dto.AuthResponse;
import com.example.client.service.ApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainDashboard extends JFrame {
    private static final Logger logger = LoggerFactory.getLogger(MainDashboard.class);
    
    private final ApiClient apiClient;
    private final AuthResponse authResponse;
    private JTabbedPane tabbedPane;
    private RegistrationsPanel registrationsPanel;
    
    public MainDashboard(ApiClient apiClient, AuthResponse authResponse) {
        this.apiClient = apiClient;
        this.authResponse = authResponse;
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Study System - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(33, 150, 243));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userInfoPanel.setBackground(new Color(33, 150, 243));
        
        JLabel titleLabel = new JLabel("Mentor-Mentee Study System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        userInfoPanel.add(titleLabel);
        
        JLabel separator = new JLabel(" | ");
        separator.setFont(new Font("Arial", Font.PLAIN, 18));
        separator.setForeground(Color.WHITE);
        userInfoPanel.add(separator);
        
        JLabel userLabel = new JLabel("User: " + authResponse.getUsername() + 
                " (" + authResponse.getUserType() + ")");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setForeground(Color.WHITE);
        userInfoPanel.add(userLabel);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(33, 150, 243));
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 13));
        logoutButton.setBackground(new Color(244, 67, 54));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setPreferredSize(new Dimension(100, 30));
        logoutButton.addActionListener(e -> performLogout());
        buttonPanel.add(logoutButton);
        
        headerPanel.add(userInfoPanel, BorderLayout.WEST);
        headerPanel.add(buttonPanel, BorderLayout.EAST);
        
        // Tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 13));
        
        // Registrations tab (available to all users)
        registrationsPanel = new RegistrationsPanel(apiClient, authResponse.getUserId(), authResponse.getUserType());
        tabbedPane.addTab("View All Registrations", 
                new ImageIcon(), registrationsPanel, "View all mentor-mentee registrations");
        
        // Add registration tab only for MENTEE users - hiển thị trực tiếp RegistrationPanel
        if ("MENTEE".equals(authResponse.getUserType())) {
            RegistrationPanel registrationPanel = new RegistrationPanel(apiClient, authResponse.getUserId());
            tabbedPane.addTab("Register with Mentor", 
                    new ImageIcon(), registrationPanel, "Register with a mentor");
        }
        
        // About panel
        JPanel aboutPanel = createAboutPanel();
        tabbedPane.addTab("About", new ImageIcon(), aboutPanel, "About this application");
        
        // Assemble main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createAboutPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        JLabel titleLabel = new JLabel("About Study System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(33, 150, 243));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(titleLabel);
        
        contentPanel.add(Box.createVerticalStrut(20));
        
        JTextArea aboutText = new JTextArea(
                "Mentor-Mentee Study System\n" +
                "Version 1.0.0\n\n" +
                "This application provides a client interface for the Study System,\n" +
                "which facilitates mentor-mentee relationships in educational settings.\n\n" +
                "Features:\n" +
                "• User authentication with JWT tokens\n" +
                "• View all mentor-mentee registrations\n" +
                "• Mentee registration with mentors\n" +
                "• Search and filter registrations\n" +
                "• Paginated data display\n\n" +
                "Technical Stack:\n" +
                "• Java Swing for UI\n" +
                "• OkHttp for REST API communication\n" +
                "• Gson for JSON serialization\n" +
                "• HTTPS/REST communication with backend\n\n" +
                "© 2026 Study System. All rights reserved.");
        aboutText.setFont(new Font("Arial", Font.PLAIN, 14));
        aboutText.setEditable(false);
        aboutText.setLineWrap(true);
        aboutText.setWrapStyleWord(true);
        aboutText.setBackground(panel.getBackground());
        aboutText.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(aboutText);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    
    private void performLogout() {
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to logout?", 
                "Confirm Logout", 
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    try {
                        apiClient.logout();
                    } catch (IOException e) {
                        logger.warn("Logout API call failed, but clearing token anyway", e);
                    }
                    return null;
                }
                
                @Override
                protected void done() {
                    logger.info("User logged out");
                    apiClient.clearAuthToken();
                    
                    // Return to login screen
                    SwingUtilities.invokeLater(() -> {
                        LoginFrame loginFrame = new LoginFrame(apiClient);
                        loginFrame.setVisible(true);
                        MainDashboard.this.dispose();
                    });
                }
            };
            
            worker.execute();
        }
    }
}
