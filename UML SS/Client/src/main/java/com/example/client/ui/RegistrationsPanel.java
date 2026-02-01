package com.example.client.ui;

import com.example.client.dto.MenteeDTO;
import com.example.client.dto.MentorMenteeRegistrationDTO;
import com.example.client.dto.PageResponse;
import com.example.client.service.ApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RegistrationsPanel extends JPanel {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationsPanel.class);
    
    private final ApiClient apiClient;
    private final Long currentUserId;
    private final String userType;
    private JTable registrationsTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> statusFilterCombo;
    private JComboBox<MenteeDTO> menteeFilterCombo;
    private JButton searchButton;
    private JButton refreshButton;
    private JButton previousButton;
    private JButton nextButton;
    private JButton cancelButton;
    private JLabel pageLabel;
    
    private int currentPage = 0;
    private int pageSize = 10;
    private int totalPages = 0;
    private List<MentorMenteeRegistrationDTO> currentRegistrations = new ArrayList<>();
    
    public RegistrationsPanel(ApiClient apiClient, Long currentUserId, String userType) {
        this.apiClient = apiClient;
        this.currentUserId = currentUserId;
        this.userType = userType;
        initializeUI();
        if ("ADMIN".equals(userType) || "MENTOR".equals(userType)) {
            loadMentees();
        }
        loadRegistrations();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top panel - Search and filters
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        searchPanel.add(searchLabel);
        
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 13));
        searchPanel.add(searchField);
        
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        searchPanel.add(statusLabel);
        
        statusFilterCombo = new JComboBox<>(new String[]{"All", "PENDING", "APPROVED", "REJECTED", "COMPLETED"});
        statusFilterCombo.setFont(new Font("Arial", Font.PLAIN, 13));
        searchPanel.add(statusFilterCombo);
        
        // Mentee filter for ADMIN and MENTOR
        if ("ADMIN".equals(userType) || "MENTOR".equals(userType)) {
            JLabel menteeLabel = new JLabel("Mentee:");
            menteeLabel.setFont(new Font("Arial", Font.PLAIN, 13));
            searchPanel.add(menteeLabel);
            
            menteeFilterCombo = new JComboBox<>();
            menteeFilterCombo.setFont(new Font("Arial", Font.PLAIN, 13));
            menteeFilterCombo.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                        boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value == null) {
                        setText("All");
                    } else if (value instanceof MenteeDTO) {
                        MenteeDTO mentee = (MenteeDTO) value;
                        setText(mentee.getFullName() + " (" + (mentee.getStudentId() != null ? mentee.getStudentId() : "") + ")");
                    }
                    return this;
                }
            });
            menteeFilterCombo.addActionListener(e -> {
                currentPage = 0;
                loadRegistrations();
            });
            searchPanel.add(menteeFilterCombo);
        }
        
        searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.PLAIN, 13));
        searchButton.setBackground(new Color(33, 150, 243));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> {
            currentPage = 0;
            loadRegistrations();
        });
        searchPanel.add(searchButton);
        
        refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.PLAIN, 13));
        refreshButton.setBackground(new Color(76, 175, 80));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> {
            searchField.setText("");
            statusFilterCombo.setSelectedIndex(0);
            currentPage = 0;
            loadRegistrations();
        });
        searchPanel.add(refreshButton);
        
        topPanel.add(searchPanel, BorderLayout.CENTER);
        
        // Table - thêm cột Action nếu là MENTEE
        String[] columnNames;
        if ("MENTEE".equals(userType)) {
            columnNames = new String[]{"ID", "Mentor ID", "Mentor Name", "Mentee ID", "Mentee Name", 
                                    "Status", "Registered At", "Purpose", "Action"};
        } else {
            columnNames = new String[]{"ID", "Mentor ID", "Mentor Name", "Mentee ID", "Mentee Name", 
                                    "Status", "Registered At", "Purpose"};
        }
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        registrationsTable = new JTable(tableModel);
        registrationsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        registrationsTable.setRowHeight(25);
        registrationsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        registrationsTable.getTableHeader().setBackground(new Color(240, 240, 240));
        registrationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Set column widths
        TableColumnModel columnModel = registrationsTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);  // ID
        columnModel.getColumn(1).setPreferredWidth(80);  // Mentor ID
        columnModel.getColumn(2).setPreferredWidth(120); // Mentor Name
        columnModel.getColumn(3).setPreferredWidth(80);  // Mentee ID
        columnModel.getColumn(4).setPreferredWidth(120); // Mentee Name
        columnModel.getColumn(5).setPreferredWidth(100); // Status
        columnModel.getColumn(6).setPreferredWidth(140); // Registered At
        columnModel.getColumn(7).setPreferredWidth(200); // Purpose
        
        JScrollPane scrollPane = new JScrollPane(registrationsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        // Bottom panel - Pagination
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        previousButton = new JButton("< Previous");
        previousButton.setFont(new Font("Arial", Font.PLAIN, 13));
        previousButton.setEnabled(false);
        previousButton.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                loadRegistrations();
            }
        });
        bottomPanel.add(previousButton);
        
        pageLabel = new JLabel("Page 1 of 1");
        pageLabel.setFont(new Font("Arial", Font.BOLD, 13));
        bottomPanel.add(pageLabel);
        
        nextButton = new JButton("Next >");
        nextButton.setFont(new Font("Arial", Font.PLAIN, 13));
        nextButton.setEnabled(false);
        nextButton.addActionListener(e -> {
            if (currentPage < totalPages - 1) {
                currentPage++;
                loadRegistrations();
            }
        });
        bottomPanel.add(nextButton);
        
        // Cancel button for MENTEE
        if ("MENTEE".equals(userType)) {
            cancelButton = new JButton("Hủy Đăng Ký");
            cancelButton.setFont(new Font("Arial", Font.PLAIN, 13));
            cancelButton.setBackground(new Color(244, 67, 54));
            cancelButton.setForeground(Color.WHITE);
            cancelButton.setFocusPainted(false);
            cancelButton.setEnabled(false);
            cancelButton.addActionListener(e -> handleCancelRegistration());
            bottomPanel.add(Box.createHorizontalStrut(20));
            bottomPanel.add(cancelButton);
        }
        
        // Add all panels
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        // Add selection listener for cancel button
        if ("MENTEE".equals(userType)) {
            registrationsTable.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = registrationsTable.getSelectedRow();
                    if (selectedRow >= 0 && selectedRow < currentRegistrations.size()) {
                        MentorMenteeRegistrationDTO reg = currentRegistrations.get(selectedRow);
                        cancelButton.setEnabled(reg.getMenteeId().equals(currentUserId));
                    } else {
                        cancelButton.setEnabled(false);
                    }
                }
            });
        }
        
        // Enter key listener for search field
        searchField.addActionListener(e -> {
            currentPage = 0;
            loadRegistrations();
        });
    }
    
    private void loadMentees() {
        SwingWorker<List<MenteeDTO>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<MenteeDTO> doInBackground() throws Exception {
                return apiClient.getAllMentees();
            }
            
            @Override
            protected void done() {
                try {
                    List<MenteeDTO> mentees = get();
                    DefaultComboBoxModel<MenteeDTO> model = new DefaultComboBoxModel<>();
                    model.addElement(null); // "All" option
                    for (MenteeDTO mentee : mentees) {
                        model.addElement(mentee);
                    }
                    menteeFilterCombo.setModel(model);
                } catch (Exception ex) {
                    logger.error("Failed to load mentees", ex);
                }
            }
        };
        worker.execute();
    }
    
    public void loadRegistrations() {
        // Disable buttons during loading
        searchButton.setEnabled(false);
        refreshButton.setEnabled(false);
        previousButton.setEnabled(false);
        nextButton.setEnabled(false);
        if (cancelButton != null) {
            cancelButton.setEnabled(false);
        }
        
        SwingWorker<PageResponse<MentorMenteeRegistrationDTO>, Void> worker = new SwingWorker<>() {
            @Override
            protected PageResponse<MentorMenteeRegistrationDTO> doInBackground() throws Exception {
                String search = searchField.getText().trim();
                String status = statusFilterCombo.getSelectedIndex() == 0 ? 
                        null : (String) statusFilterCombo.getSelectedItem();
                Long menteeId = null;
                if (menteeFilterCombo != null && menteeFilterCombo.getSelectedItem() != null) {
                    MenteeDTO selectedMentee = (MenteeDTO) menteeFilterCombo.getSelectedItem();
                    if (selectedMentee != null) {
                        menteeId = selectedMentee.getId();
                    }
                }
                
                return apiClient.getAllRegistrations(
                        currentPage, pageSize, 
                        search.isEmpty() ? null : search,
                        null, menteeId, status);
            }
            
            @Override
            protected void done() {
                try {
                    PageResponse<MentorMenteeRegistrationDTO> response = get();
                    
                    // Clear existing rows
                    tableModel.setRowCount(0);
                    currentRegistrations.clear();
                    
                    // Add new rows
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    for (MentorMenteeRegistrationDTO reg : response.getContent()) {
                        currentRegistrations.add(reg);
                        Object[] row;
                        if ("MENTEE".equals(userType)) {
                            row = new Object[]{
                                reg.getId(),
                                reg.getMentorId(),
                                reg.getMentorName() != null ? reg.getMentorName() : "N/A",
                                reg.getMenteeId(),
                                reg.getMenteeName() != null ? reg.getMenteeName() : "N/A",
                                reg.getStatus(),
                                reg.getRegisteredAt() != null ? 
                                    formatDateTime(reg.getRegisteredAt()) : "N/A",
                                reg.getPurpose() != null ? 
                                    (reg.getPurpose().length() > 50 ? 
                                        reg.getPurpose().substring(0, 47) + "..." : 
                                        reg.getPurpose()) : "",
                                reg.getMenteeId().equals(currentUserId) ? "Hủy" : ""
                            };
                        } else {
                            row = new Object[]{
                                reg.getId(),
                                reg.getMentorId(),
                                reg.getMentorName() != null ? reg.getMentorName() : "N/A",
                                reg.getMenteeId(),
                                reg.getMenteeName() != null ? reg.getMenteeName() : "N/A",
                                reg.getStatus(),
                                reg.getRegisteredAt() != null ? 
                                    formatDateTime(reg.getRegisteredAt()) : "N/A",
                                reg.getPurpose() != null ? 
                                    (reg.getPurpose().length() > 50 ? 
                                        reg.getPurpose().substring(0, 47) + "..." : 
                                        reg.getPurpose()) : ""
                            };
                        }
                        tableModel.addRow(row);
                    }
                    
                    // Update pagination
                    totalPages = response.getTotalPages();
                    pageLabel.setText("Page " + (currentPage + 1) + " of " + 
                                     (totalPages == 0 ? 1 : totalPages) + 
                                     " (Total: " + response.getTotalElements() + ")");
                    
                    previousButton.setEnabled(currentPage > 0);
                    nextButton.setEnabled(currentPage < totalPages - 1);
                    
                    logger.info("Loaded {} registrations", response.getContent().size());
                    
                } catch (Exception ex) {
                    logger.error("Failed to load registrations", ex);
                    JOptionPane.showMessageDialog(RegistrationsPanel.this, 
                        "Failed to load registrations: " + ex.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                } finally {
                    searchButton.setEnabled(true);
                    refreshButton.setEnabled(true);
                }
            }
        };
        
        worker.execute();
    }
    
    private void handleCancelRegistration() {
        int selectedRow = registrationsTable.getSelectedRow();
        if (selectedRow < 0 || selectedRow >= currentRegistrations.size()) {
            return;
        }
        
        MentorMenteeRegistrationDTO reg = currentRegistrations.get(selectedRow);
        if (!reg.getMenteeId().equals(currentUserId)) {
            JOptionPane.showMessageDialog(this, 
                "Bạn chỉ có thể hủy đăng ký của chính mình", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn hủy đăng ký này?", 
            "Xác nhận hủy đăng ký", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            cancelButton.setEnabled(false);
            cancelButton.setText("Đang xử lý...");
            
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    apiClient.deleteRegistration(reg.getId());
                    return null;
                }
                
                @Override
                protected void done() {
                    try {
                        get();
                        JOptionPane.showMessageDialog(RegistrationsPanel.this, 
                            "Hủy đăng ký thành công!", 
                            "Thành công", 
                            JOptionPane.INFORMATION_MESSAGE);
                        loadRegistrations();
                    } catch (Exception ex) {
                        logger.error("Failed to cancel registration", ex);
                        JOptionPane.showMessageDialog(RegistrationsPanel.this, 
                            "Hủy đăng ký thất bại: " + ex.getMessage(), 
                            "Lỗi", 
                            JOptionPane.ERROR_MESSAGE);
                    } finally {
                        cancelButton.setText("Hủy Đăng Ký");
                    }
                }
            };
            worker.execute();
        }
    }
    
    private String formatDateTime(String dateTimeStr) {
        try {
            // Remove microseconds if present and handle ISO format
            if (dateTimeStr.contains(".")) {
                dateTimeStr = dateTimeStr.substring(0, dateTimeStr.indexOf('.'));
            }
            if (dateTimeStr.contains("T")) {
                dateTimeStr = dateTimeStr.replace("T", " ");
            }
            return dateTimeStr;
        } catch (Exception e) {
            return dateTimeStr;
        }
    }
}
