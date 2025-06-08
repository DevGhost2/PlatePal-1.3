/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.MySQL2; 


public class ViewRequestAdminPanel extends javax.swing.JPanel {

    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;
    private int selectedRow = -1;
    private boolean isInitializing = true;
    
    public static ViewRequestAdminPanel vrap;
    
    public ViewRequestAdminPanel() {
        initComponents();
        setupTable();
        setupComboBoxes();
        setupSearchAndFilters();
        loadRequests();
        isInitializing = false;
    }
    
    public static synchronized ViewRequestAdminPanel getInstance(){
        if(vrap==null){
            vrap=new ViewRequestAdminPanel();
        }
        return vrap;
    }
    
    private void setupTable() {
        model = new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Request ID", "Employee Name", "Request Type", "Date Submitted", "Status"
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        jTable1.setModel(model);
        sorter = new TableRowSorter<>(model);
        jTable1.setRowSorter(sorter);
        
        
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
    
    }
    
    
    private void setupSearchAndFilters() {
        // Search functionality
        EmployeeNameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                 if (!isInitializing) {
                    filterTable();
                }
                filterTable();
            }
        });
    }
    
    private void setupComboBoxes() {
        try {
            RequestTypeComboBox.removeAllItems();
            RequestTypeComboBox.addItem("All Types");
            
            RequestTypeComboBox.addItem("Leave Request");
            RequestTypeComboBox.addItem("Equipment Request");
            RequestTypeComboBox.addItem("Overtime Request");
            RequestTypeComboBox.addItem("Schedule Change");
            RequestTypeComboBox.addItem("Training Request");
            RequestTypeComboBox.addItem("Other");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Setup Request Status ComboBox
        RequestStatusComboBox.removeAllItems();
        RequestStatusComboBox.addItem("All Status");
        RequestStatusComboBox.addItem("Pending");
        RequestStatusComboBox.addItem("Approved");
        RequestStatusComboBox.addItem("Rejected");
    }
    
    private void filterTable() {
        
        if (isInitializing) {
            return;
        }
        String employeeName = EmployeeNameField.getText().trim();
        Object requestTypeObj = RequestTypeComboBox.getSelectedItem();
        Object requestStatusObj = RequestStatusComboBox.getSelectedItem();
        
        // Null checks to prevent NullPointerException
        String requestType = (requestTypeObj != null) ? requestTypeObj.toString() : "All Types";
        String requestStatus = (requestStatusObj != null) ? requestStatusObj.toString() : "All Status";
        
        if (employeeName.isEmpty() && 
            requestType.equals("All Types") && 
            requestStatus.equals("All Status")) {
            sorter.setRowFilter(null);
        } else {
            RowFilter<DefaultTableModel, Object> rf = RowFilter.andFilter(
                java.util.Arrays.asList(
                    employeeName.isEmpty() ? RowFilter.regexFilter(".*") : 
                        RowFilter.regexFilter("(?i)" + employeeName, 1), // Employee Name column
                    requestType.equals("All Types") ? RowFilter.regexFilter(".*") : 
                        RowFilter.regexFilter("^" + requestType + "$", 2), // Request Type column
                    requestStatus.equals("All Status") ? RowFilter.regexFilter(".*") : 
                        RowFilter.regexFilter("^" + requestStatus + "$", 4) // Status column
                )
            );
            sorter.setRowFilter(rf);
        }
    }
    
    private void loadRequests() {
        try {
            String query = "SELECT er.*, CONCAT(e.first_name, ' ', e.last_name) AS employee_name " +
                           "FROM employee_requests er " +
                           "INNER JOIN employee e ON er.employee_id = e.id " +
                           "ORDER BY er.date_submitted DESC";
            
            ResultSet rs = MySQL2.executeSearch(query);
            
            model.setRowCount(0);
            
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("request_id"));
                row.add(rs.getString("employee_name"));
                row.add(rs.getString("request_type"));
                row.add(rs.getString("date_submitted"));
                row.add(rs.getString("status"));
                
                model.addRow(row);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            // If table doesn't exist or no data, show empty table
            model.setRowCount(0);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        EmployeeNameField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        SearchButton = new javax.swing.JButton();
        RefreshButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        RequestStatusComboBox = new javax.swing.JComboBox<>();
        RequestType = new javax.swing.JLabel();
        RequestTypeComboBox = new javax.swing.JComboBox<>();

        EmployeeNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmployeeNameFieldActionPerformed(evt);
            }
        });

        jLabel1.setText("Employee Name");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("                                                                 Employee Requests");

        SearchButton.setText("Search");
        SearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchButtonActionPerformed(evt);
            }
        });

        RefreshButton.setText("Refresh");
        RefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshButtonActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "RequestID", "Employee Name", "Request Type", "Date Submitted", "Status"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel3.setText("Request Status");

        RequestStatusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        RequestStatusComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RequestStatusComboBoxActionPerformed(evt);
            }
        });

        RequestType.setText("Request Type");

        RequestTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        RequestTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RequestTypeComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 852, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(198, 198, 198))
                    .addComponent(EmployeeNameField)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(RequestStatusComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(RequestType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(RequestTypeComboBox, 0, 396, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SearchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                    .addComponent(RefreshButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EmployeeNameField)
                .addGap(1, 1, 1)
                .addComponent(RequestType)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(RequestTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(RefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(RequestStatusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(SearchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                        .addGap(104, 104, 104)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void EmployeeNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmployeeNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmployeeNameFieldActionPerformed

    private void SearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchButtonActionPerformed
         filterTable();
    }//GEN-LAST:event_SearchButtonActionPerformed

    private void RefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshButtonActionPerformed
        loadRequests();
        // Clear filters
        isInitializing = true; // Prevent filtering during refresh
        EmployeeNameField.setText("");
        RequestTypeComboBox.setSelectedIndex(0);
        RequestStatusComboBox.setSelectedIndex(0);
        isInitializing = false; // Re-enable filtering
    
    }//GEN-LAST:event_RefreshButtonActionPerformed

    private void RequestTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RequestTypeComboBoxActionPerformed
        filterTable();
    }//GEN-LAST:event_RequestTypeComboBoxActionPerformed

    private void RequestStatusComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RequestStatusComboBoxActionPerformed
        filterTable();
    }//GEN-LAST:event_RequestStatusComboBoxActionPerformed
private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {                                     
        selectedRow = jTable1.getSelectedRow();
        
         if (selectedRow >= 0 && evt.getClickCount() == 2) { 
            try {
                int modelRow = jTable1.convertRowIndexToModel(selectedRow);
            
            
            String requestId = model.getValueAt(modelRow, 0).toString();
                
                System.out.println("Double-clicked on request: " + requestId); 
                
                javax.swing.SwingUtilities.invokeLater(() -> {
                    RequestDetailsDialog dialog = new RequestDetailsDialog(
                        (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), 
                        true, 
                        requestId
                    );
                    dialog.setVisible(true);
                    
                    loadRequests();
                });
                
            } catch (Exception e) {
                e.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(this, 
                    "Error opening request details: " + e.getMessage(),
                    "Error", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    } 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField EmployeeNameField;
    private javax.swing.JButton RefreshButton;
    private javax.swing.JComboBox<String> RequestStatusComboBox;
    private javax.swing.JLabel RequestType;
    private javax.swing.JComboBox<String> RequestTypeComboBox;
    private javax.swing.JButton SearchButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
