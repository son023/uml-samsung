package com.example.client.ui;

import com.example.client.dto.AuthResponse;
import com.example.client.dto.LoginRequest;
import com.example.client.service.ApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoginFrame extends JFrame {
    private static final Logger logger = LoggerFactory.getLogger(LoginFrame.class);
    
    private final ApiClient apiClient;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton exitButton;
    
    public LoginFrame(ApiClient apiClient) {
        this.apiClient = apiClient;
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Study System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Mentor-Mentee Study System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(33, 150, 243));
        titlePanel.add(titleLabel);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(usernameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(usernameField, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(passwordLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(passwordField, gbc);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(76, 175, 80));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(120, 35));
        loginButton.addActionListener(e -> performLogin());
        
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 14));
        exitButton.setBackground(new Color(244, 67, 54));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setPreferredSize(new Dimension(120, 35));
        exitButton.addActionListener(e -> System.exit(0));
        
        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);
        
        // Add panels to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Enter key listener for password field
        passwordField.addActionListener(e -> performLogin());
        usernameField.addActionListener(e -> passwordField.requestFocus());
        
        // Set initial focus
        SwingUtilities.invokeLater(() -> usernameField.requestFocus());
    }
    
    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        // Validation
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter username", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            usernameField.requestFocus();
            return;
        }
        
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter password", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            passwordField.requestFocus();
            return;
        }
        
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, 
                "Password must be at least 6 characters", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            passwordField.requestFocus();
            return;
        }
        
        // Disable buttons during login
        loginButton.setEnabled(false);
        loginButton.setText("Logging in...");
        
        // Perform login in background thread
        SwingWorker<AuthResponse, Void> worker = new SwingWorker<>() {
            @Override
            protected AuthResponse doInBackground() throws Exception {
                LoginRequest loginRequest = new LoginRequest(username, password);
                return apiClient.login(loginRequest);
            }
            
            @Override
            protected void done() {
                try {
                    AuthResponse response = get();
                    logger.info("Login successful for user: {}", response.getUsername());
                    
                    JOptionPane.showMessageDialog(LoginFrame.this, 
                        "Welcome, " + response.getUsername() + "!\nUser Type: " + response.getUserType(), 
                        "Login Successful", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Open main dashboard
                    SwingUtilities.invokeLater(() -> {
                        MainDashboard dashboard = new MainDashboard(apiClient, response);
                        dashboard.setVisible(true);
                        LoginFrame.this.dispose();
                    });
                    
                } catch (Exception ex) {
                    logger.error("Login failed", ex);
                    JOptionPane.showMessageDialog(LoginFrame.this, 
                        "Login failed: " + ex.getMessage(), 
                        "Login Error", 
                        JOptionPane.ERROR_MESSAGE);
                    
                    // Clear password field
                    passwordField.setText("");
                    passwordField.requestFocus();
                } finally {
                    loginButton.setEnabled(true);
                    loginButton.setText("Login");
                }
            }
        };
        
        worker.execute();
    }
}
