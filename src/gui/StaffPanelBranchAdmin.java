/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import gui.StaffDAOBranchAdmin;
import gui.StaffDataBranchAdmin;
import java.sql.ResultSet;
import model.MySQL2;
public class StaffPanelBranchAdmin extends javax.swing.JPanel {

    private StaffDAOBranchAdmin staffDAO;
    private List<String> branches;
    private List<String> addressCategories;
    public StaffPanelBranchAdmin() {
        initComponents();
        
        staffDAO = new StaffDAOBranchAdmin();
        
        // Load reference data
        loadReferenceData();
        
        // Setup table
        setupTable();
        
        // Load initial data
        loadStaffData();
        
        // Add action listeners
        CreateAccountButton.addActionListener(evt -> createAccountButtonActionPerformed(evt));
        UpdateAccountButton.addActionListener(evt -> updateAccountButtonActionPerformed(evt));
        ResetButton.addActionListener(evt -> resetButtonActionPerformed(evt));
        jTextField1.addActionListener(evt -> searchButtonActionPerformed(evt));
        EmployeeSearchResetButton.addActionListener(evt -> resetSearchButtonActionPerformed(evt));
        
        // Add selection listener to table
        EmployeeTable.getSelectionModel().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting()) {
                loadSelectedEmployee();
            }
        });
    }
    
    private void loadReferenceData() {
        // Load branches for dropdown
        branches = staffDAO.getBranches();
        
        // Load address categories for dropdown
        addressCategories = staffDAO.getAddressCategories();
        AddressCategoryComboBox.removeAllItems();
        AddressCategoryComboBox.addItem("Item 1"); // Default first item
        for (String category : addressCategories) {
            AddressCategoryComboBox.addItem(category);
        }
        
        // Also populate search dropdown
        jComboBox2.removeAllItems();
        jComboBox2.addItem("Item 1"); // Default first item
        for (String branch : branches) {
            jComboBox2.addItem(branch);
        }
    }
    
    private void setupTable() {
        // Set table model
        DefaultTableModel model = new DefaultTableModel(
            new Object [][] {},
            new String [] {"EmployeeName", "EmployeeID", "TelephoneNo", "Date", "Attendance"}
        ) {
            // Make cells non-editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        EmployeeTable.setModel(model);
        
        // Adjust column widths
        TableColumnModel columnModel = EmployeeTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150);   // Employee Name
        columnModel.getColumn(1).setPreferredWidth(80);    // Employee ID
        columnModel.getColumn(2).setPreferredWidth(100);   // Telephone
        columnModel.getColumn(3).setPreferredWidth(100);   // Date
        columnModel.getColumn(4).setPreferredWidth(80);    // Attendance
    }
    
    private void loadStaffData() {
        // Use SwingWorker to avoid freezing the UI
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            private List<StaffDataBranchAdmin> staffList;
            
            @Override
            protected Void doInBackground() throws Exception {
                staffList = staffDAO.getAllStaff();
                return null;
            }
            
            @Override
            protected void done() {
                updateStaffTable(staffList);
            }
        };
        worker.execute();
    }
    
    private void updateStaffTable(List<StaffDataBranchAdmin> staffList) {
        DefaultTableModel model = (DefaultTableModel) EmployeeTable.getModel();
        
        // Clear table
        model.setRowCount(0);
        
        // Add data to table
        if (staffList != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            
            for (StaffDataBranchAdmin staff : staffList) {
                model.addRow(new Object[]{
                    staff.getFullName(),
                    staff.getEmployeeID(),
                    staff.getMobile(),
                    staff.getDate() != null ? dateFormat.format(staff.getDate()) : "",
                    staff.getAttendance() != null ? staff.getAttendance() : ""
                });
            }
        }
    }
    
    private void loadSelectedEmployee() {
        int selectedRow = EmployeeTable.getSelectedRow();
        
        if (selectedRow >= 0) {
            // Get employee ID from table
            int employeeID = (int) EmployeeTable.getValueAt(selectedRow, 1);
            
            // Load full employee details
            try {
                ResultSet rs = MySQL2.executeSearch("SELECT * FROM employees WHERE EmployeeID = " + employeeID);
                
                if (rs.next()) {
                    // Populate form fields
                    FirstNameLabel.setText(rs.getString("FirstName"));
                    LastNameLabel.setText(rs.getString("LastName"));
                    MobileLabel.setText(rs.getString("Mobile"));
                    EmailLabel.setText(rs.getString("Email"));
                    CityLabel.setText(rs.getString("City"));
                    AddressLabel.setText(rs.getString("Address"));
                    
                    // Set dropdown
                    String addressCategory = rs.getString("AddressCategory");
                    for (int i = 0; i < AddressCategoryComboBox.getItemCount(); i++) {
                        if (AddressCategoryComboBox.getItemAt(i).equals(addressCategory)) {
                            AddressCategoryComboBox.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Error loading employee: " + e.getMessage());
            }
        }
    }
    
    private void createAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // Get form values
        String firstName = FirstNameLabel.getText().trim();
        String lastName = LastNameLabel.getText().trim();
        String mobile = MobileLabel.getText().trim();
        String email = EmailLabel.getText().trim();
        String city = CityLabel.getText().trim();
        String address = AddressLabel.getText().trim();
        String addressCategory = AddressCategoryComboBox.getSelectedItem().toString();
        String branch = "Main Branch"; // Since you don't have a branch combo box in form
        
        // Validate inputs
        if (firstName.isEmpty() || lastName.isEmpty() || mobile.isEmpty()) {
            JOptionPane.showMessageDialog(this, "First Name, Last Name and Mobile are required fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if default values are selected
        if (addressCategory.equals("Item 1")) {
            JOptionPane.showMessageDialog(this, "Please select a valid Address Category", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create account
        boolean success = staffDAO.createStaff(firstName, lastName, mobile, email, city, address, addressCategory, branch);
        
        if (success) {
            JOptionPane.showMessageDialog(this, "Staff account created successfully");
            resetForm();
            loadStaffData(); // Refresh table
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create staff account", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = EmployeeTable.getSelectedRow();
        
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an employee to update", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Get employee ID from table
        int employeeID = (int) EmployeeTable.getValueAt(selectedRow, 1);
        
        // Get form values
        String firstName = FirstNameLabel.getText().trim();
        String lastName = LastNameLabel.getText().trim();
        String mobile = MobileLabel.getText().trim();
        String email = EmailLabel.getText().trim();
        String city = CityLabel.getText().trim();
        String address = AddressLabel.getText().trim();
        String addressCategory = AddressCategoryComboBox.getSelectedItem().toString();
        String branch = "Main Branch"; // Since you don't have a branch combo box in form
        
        // Validate inputs
        if (firstName.isEmpty() || lastName.isEmpty() || mobile.isEmpty()) {
            JOptionPane.showMessageDialog(this, "First Name, Last Name and Mobile are required fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if default values are selected
        if (addressCategory.equals("Item 1")) {
            JOptionPane.showMessageDialog(this, "Please select a valid Address Category", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Update account
        boolean success = staffDAO.updateStaff(employeeID, firstName, lastName, mobile, email, city, address, addressCategory, branch);
        
        if (success) {
            JOptionPane.showMessageDialog(this, "Staff account updated successfully");
            loadStaffData(); // Refresh table
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update staff account", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {
        resetForm();
    }
    
    private void resetForm() {
        FirstNameLabel.setText("");
        LastNameLabel.setText("");
        MobileLabel.setText("");
        EmailLabel.setText("");
        CityLabel.setText("");
        AddressLabel.setText("");
        AddressCategoryComboBox.setSelectedIndex(0);
        EmployeeTable.clearSelection();
    }
    
    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String employeeID = jTextField1.getText().trim();
        String branch = jComboBox2.getSelectedItem().toString();
        
        // Search staff
        List<StaffDataBranchAdmin> searchResults = staffDAO.searchStaff(employeeID, branch);
        updateStaffTable(searchResults);
    }
    
    private void resetSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        jTextField1.setText("");
        jComboBox2.setSelectedIndex(0);
        loadStaffData();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        FirstNameLabel = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        LastNameLabel = new javax.swing.JTextField();
        MobileLabel = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        EmailLabel = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        CityLabel = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        AddressLabel = new javax.swing.JTextField();
        CreateAccountButton = new javax.swing.JButton();
        UpdateAccountButton = new javax.swing.JButton();
        ResetButton = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        AddressCategoryComboBox = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        EmployeeSearchResetButton = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        EmployeeTable = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Staff Registration");

        FirstNameLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FirstNameLabelActionPerformed(evt);
            }
        });

        jLabel5.setText("First Name");

        jLabel6.setText("Last Name");

        LastNameLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LastNameLabelActionPerformed(evt);
            }
        });

        MobileLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MobileLabelActionPerformed(evt);
            }
        });

        jLabel7.setText("Mobile");

        jLabel8.setText("Email");

        EmailLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmailLabelActionPerformed(evt);
            }
        });

        jLabel9.setText("City");

        CityLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CityLabelActionPerformed(evt);
            }
        });

        jLabel10.setText("Address");

        AddressLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddressLabelActionPerformed(evt);
            }
        });

        CreateAccountButton.setText("Create Account");
        CreateAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateAccountButtonActionPerformed(evt);
            }
        });

        UpdateAccountButton.setText("Update Account");
        UpdateAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateAccountButtonActionPerformed(evt);
            }
        });

        ResetButton.setText("Reset");
        ResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetButtonActionPerformed(evt);
            }
        });

        jLabel11.setText("Address Category");

        AddressCategoryComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        AddressCategoryComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddressCategoryComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(AddressCategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5)
                        .addComponent(FirstNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(LastNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(MobileLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(EmailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(CityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)
                        .addComponent(AddressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(CreateAccountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel11))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(ResetButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(UpdateAccountButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)))
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel4)
                .addGap(30, 30, 30)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FirstNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LastNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MobileLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EmailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AddressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AddressCategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109)
                .addComponent(CreateAccountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(UpdateAccountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ResetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel1.setText("EmployeeID");

        jLabel2.setText("Branch");

        EmployeeSearchResetButton.setText("RESET");
        EmployeeSearchResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmployeeSearchResetButtonActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(EmployeeSearchResetButton)
                .addGap(14, 14, 14))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmployeeSearchResetButton)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        EmployeeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "EmployeeName", "EmployeeID", "TelephoneNo", "Date", "Attendance"
            }
        ));
        jScrollPane1.setViewportView(EmployeeTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void EmployeeSearchResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmployeeSearchResetButtonActionPerformed
        private void EmployeeSearchResetButtonActionPerformed(java.awt.event.ActionEvent evt) {
    // Clear search field and reset dropdown
    jTextField1.setText("");
    jComboBox2.setSelectedIndex(0);
    
    // Reload all staff data
    loadStaffData();
}
    }//GEN-LAST:event_EmployeeSearchResetButtonActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
    // This is called when user presses Enter in the search field
    String employeeID = jTextField1.getText().trim();
    String branch = jComboBox2.getSelectedItem().toString();
    
    // If search field is empty and default branch is selected, show all staff
    if (employeeID.isEmpty() && branch.equals("Item 1")) {
        loadStaffData();
        return;
    }
    
    // Search staff based on criteria
    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
        private List<StaffDataBranchAdmin> staffList;
        
        @Override
        protected Void doInBackground() throws Exception {
            staffList = staffDAO.searchStaff(employeeID, branch);
            return null;
        }
        
        @Override
        protected void done() {
            updateStaffTable(staffList);
        }
    };
    worker.execute();
}
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void CreateAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateAccountButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CreateAccountButtonActionPerformed

    private void UpdateAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateAccountButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UpdateAccountButtonActionPerformed

    private void ResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ResetButtonActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void FirstNameLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FirstNameLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FirstNameLabelActionPerformed

    private void LastNameLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LastNameLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LastNameLabelActionPerformed

    private void MobileLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MobileLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MobileLabelActionPerformed

    private void EmailLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmailLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmailLabelActionPerformed

    private void CityLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CityLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CityLabelActionPerformed

    private void AddressLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddressLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddressLabelActionPerformed

    private void AddressCategoryComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddressCategoryComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddressCategoryComboBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> AddressCategoryComboBox;
    private javax.swing.JTextField AddressLabel;
    private javax.swing.JTextField CityLabel;
    private javax.swing.JButton CreateAccountButton;
    private javax.swing.JTextField EmailLabel;
    private javax.swing.JButton EmployeeSearchResetButton;
    private javax.swing.JTable EmployeeTable;
    private javax.swing.JTextField FirstNameLabel;
    private javax.swing.JTextField LastNameLabel;
    private javax.swing.JTextField MobileLabel;
    private javax.swing.JButton ResetButton;
    private javax.swing.JButton UpdateAccountButton;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
