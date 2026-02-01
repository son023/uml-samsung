package com.example.client.ui;

import com.example.client.dto.MentorDTO;
import com.example.client.dto.MentorMenteeRegistrationDTO;
import com.example.client.service.ApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RegistrationPanel extends JPanel {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationPanel.class);
    
    private final ApiClient apiClient;
    private final Long currentUserId;
    private JTable mentorsTable;
    private DefaultTableModel tableModel;
    private JButton registerButton;
    private JButton refreshButton;
    private List<MentorDTO> mentors;
    
    public RegistrationPanel(ApiClient apiClient, Long currentUserId) {
        this.apiClient = apiClient;
        this.currentUserId = currentUserId;
        initializeUI();
        loadMentors();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Danh sách Mentor chưa đăng ký");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(33, 150, 243));
        titlePanel.add(titleLabel);
        
        // Refresh button
        refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.PLAIN, 12));
        refreshButton.setBackground(new Color(76, 175, 80));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> loadMentors());
        titlePanel.add(Box.createHorizontalStrut(10));
        titlePanel.add(refreshButton);
        
        // Table
        String[] columnNames = {"ID", "Full Name", "Email", "Phone", "Expertise", "Years of Experience", "Subjects", "Bio"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        mentorsTable = new JTable(tableModel);
        mentorsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        mentorsTable.setRowHeight(25);
        mentorsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        mentorsTable.getTableHeader().setBackground(new Color(240, 240, 240));
        mentorsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Set column widths
        mentorsTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        mentorsTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Full Name
        mentorsTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Email
        mentorsTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Phone
        mentorsTable.getColumnModel().getColumn(4).setPreferredWidth(150); // Expertise
        mentorsTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Years
        mentorsTable.getColumnModel().getColumn(6).setPreferredWidth(200); // Subjects
        mentorsTable.getColumnModel().getColumn(7).setPreferredWidth(200); // Bio
        
        JScrollPane scrollPane = new JScrollPane(mentorsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        // Bottom panel - Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        registerButton = new JButton("Đăng Ký");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBackground(new Color(76, 175, 80));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setPreferredSize(new Dimension(150, 35));
        registerButton.addActionListener(e -> handleRegistration());
        
        buttonPanel.add(registerButton);
        
        // Assemble main panel
        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public void loadMentors() {
        refreshButton.setEnabled(false);
        registerButton.setEnabled(false);
        
        SwingWorker<List<MentorDTO>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<MentorDTO> doInBackground() throws Exception {
                return apiClient.getAllMentors(currentUserId);
            }
            
            @Override
            protected void done() {
                try {
                    mentors = get();
                    
                    // Clear existing rows
                    tableModel.setRowCount(0);
                    
                    // Add new rows
                    for (MentorDTO mentor : mentors) {
                        // Format subjects
                        String subjectsStr = "N/A";
                        if (mentor.getSubjects() != null && !mentor.getSubjects().isEmpty()) {
                            subjectsStr = mentor.getSubjects().stream()
                                    .map(s -> s.getSubjectName() != null ? s.getSubjectName() : s.getSubjectCode())
                                    .reduce((a, b) -> a + ", " + b)
                                    .orElse("N/A");
                            if (subjectsStr.length() > 50) {
                                subjectsStr = subjectsStr.substring(0, 47) + "...";
                            }
                        }
                        
                        Object[] row = {
                            mentor.getId(),
                            mentor.getFullName() != null ? mentor.getFullName() : "N/A",
                            mentor.getEmail() != null ? mentor.getEmail() : "N/A",
                            mentor.getPhone() != null ? mentor.getPhone() : "N/A",
                            mentor.getExpertise() != null ? mentor.getExpertise() : "N/A",
                            mentor.getYearsOfExperience() != null ? mentor.getYearsOfExperience() : 0,
                            subjectsStr,
                            mentor.getBio() != null ? 
                                (mentor.getBio().length() > 50 ? 
                                    mentor.getBio().substring(0, 47) + "..." : 
                                    mentor.getBio()) : ""
                        };
                        tableModel.addRow(row);
                    }
                    
                    logger.info("Loaded {} mentors", mentors.size());
                    registerButton.setEnabled(true);
                    
                } catch (Exception ex) {
                    logger.error("Failed to load mentors", ex);
                    JOptionPane.showMessageDialog(RegistrationPanel.this, 
                        "Failed to load mentors: " + ex.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                } finally {
                    refreshButton.setEnabled(true);
                }
            }
        };
        
        worker.execute();
    }
    
    private void handleRegistration() {
        int selectedRow = mentorsTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn một mentor từ danh sách", 
                "Chưa chọn mentor", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (mentors == null || selectedRow >= mentors.size()) {
            JOptionPane.showMessageDialog(this, 
                "Dữ liệu mentor không hợp lệ", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        MentorDTO selectedMentor = mentors.get(selectedRow);
        
        // Show confirmation dialog with purpose input
        showConfirmationDialog(selectedMentor);
    }
    
    private void showConfirmationDialog(MentorDTO mentor) {
        JDialog confirmDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Xác nhận đăng ký", true);
        confirmDialog.setSize(450, 300);
        confirmDialog.setLocationRelativeTo(this);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        JLabel mentorInfoLabel = new JLabel("<html><b>Mentor được chọn:</b></html>");
        mentorInfoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        infoPanel.add(mentorInfoLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        
        JTextArea mentorInfo = new JTextArea(
            "ID: " + mentor.getId() + "\n" +
            "Tên: " + (mentor.getFullName() != null ? mentor.getFullName() : "N/A") + "\n" +
            "Email: " + (mentor.getEmail() != null ? mentor.getEmail() : "N/A") + "\n" +
            "Chuyên môn: " + (mentor.getExpertise() != null ? mentor.getExpertise() : "N/A") + "\n" +
            "Kinh nghiệm: " + (mentor.getYearsOfExperience() != null ? mentor.getYearsOfExperience() + " năm" : "N/A")
        );
        mentorInfo.setFont(new Font("Arial", Font.PLAIN, 13));
        mentorInfo.setEditable(false);
        mentorInfo.setBackground(mainPanel.getBackground());
        infoPanel.add(mentorInfo);
        
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        JButton confirmButton = new JButton("Xác Nhận Đăng Ký");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmButton.setBackground(new Color(76, 175, 80));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);
        confirmButton.setPreferredSize(new Dimension(180, 35));
        confirmButton.addActionListener(e -> {
            confirmDialog.dispose();
            submitRegistration(mentor.getId(), null);
        });
        
        JButton cancelConfirmButton = new JButton("Hủy");
        cancelConfirmButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelConfirmButton.setBackground(new Color(158, 158, 158));
        cancelConfirmButton.setForeground(Color.WHITE);
        cancelConfirmButton.setFocusPainted(false);
        cancelConfirmButton.setPreferredSize(new Dimension(120, 35));
        cancelConfirmButton.addActionListener(e -> confirmDialog.dispose());
        
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelConfirmButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        confirmDialog.add(mainPanel);
        confirmDialog.setVisible(true);
    }
    
    private void submitRegistration(Long mentorId, String purpose) {
        // Disable buttons during submission
        registerButton.setEnabled(false);
        registerButton.setText("Đang xử lý...");
        
        // Submit in background thread
        SwingWorker<MentorMenteeRegistrationDTO, Void> worker = new SwingWorker<>() {
            @Override
            protected MentorMenteeRegistrationDTO doInBackground() throws Exception {
                MentorMenteeRegistrationDTO dto = new MentorMenteeRegistrationDTO();
                dto.setMentorId(mentorId);
                dto.setMenteeId(currentUserId);
                dto.setPurpose(purpose != null ? purpose : ""); // Set empty string if null
                
                return apiClient.createRegistration(dto);
            }
            
            @Override
            protected void done() {
                try {
                    MentorMenteeRegistrationDTO result = get();
                    logger.info("Registration created successfully: {}", result.getId());
                    
                    JOptionPane.showMessageDialog(RegistrationPanel.this, 
                        "Đăng ký thành công!\n" +
                        "Mã đăng ký: " + result.getId() + "\n" +
                        "Trạng thái: " + result.getStatus(), 
                        "Thành công", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Reload mentors to update the list
                    loadMentors();
                    
                } catch (Exception ex) {
                    logger.error("Registration failed", ex);
                    JOptionPane.showMessageDialog(RegistrationPanel.this, 
                        "Đăng ký thất bại: " + ex.getMessage(), 
                        "Lỗi", 
                        JOptionPane.ERROR_MESSAGE);
                } finally {
                    registerButton.setEnabled(true);
                    registerButton.setText("Đăng Ký");
                }
            }
        };
        
        worker.execute();
    }
}
