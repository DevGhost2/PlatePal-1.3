/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.MySQL2;

public class AttendanceReciption extends javax.swing.JPanel {

    private java.util.HashMap<String, String> employeeMap = new java.util.HashMap<>();
    public static AttendanceReciption aba;
    
    public AttendanceReciption() {
        initComponents();
        loadAttendance();
        
        startClock();
    }

    public static synchronized AttendanceReciption getInstance() {
        if (aba == null) {
            aba = new AttendanceReciption();
        }
        return aba;
    }

    private void startClock() {
        Thread clockThread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while (true) {
                String currentTime = sdf.format(new Date());

            
                SwingUtilities.invokeLater(() -> jLabel2.setText(currentTime));

                try {
                    Thread.sleep(1000); 
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    break; 
                }
            }
        });
        clockThread.setDaemon(true);
        clockThread.start();
    }

    

    private void loadAttendance() {
        try {
            ResultSet resultset = MySQL2.executeSearch(
                "SELECT e.id, e.emp_id, e.first_name, e.last_name, e.email, er.type " +
                "FROM employee e " +
                "INNER JOIN employee_role er ON e.employee_role_id = er.id " +
                "ORDER BY e.emp_id"
            );

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            
           
            if (model.getColumnCount() < 6) {
                model.addColumn("Internal ID");
            }
            
         
            if (jTable1.getColumnModel().getColumnCount() >= 6) {
                jTable1.getColumnModel().getColumn(5).setMinWidth(0);
                jTable1.getColumnModel().getColumn(5).setMaxWidth(0);
                jTable1.getColumnModel().getColumn(5).setWidth(0);
            }
            
            while (resultset.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultset.getString("emp_id"));
                vector.add(resultset.getString("first_name")); 
                vector.add(resultset.getString("email")); 
                vector.add(resultset.getString("id"));

                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading employee data: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(762, 100));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("ATTENDANCE");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Time Now :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 466, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel3.setPreferredSize(new java.awt.Dimension(5, 401));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 501, Short.MAX_VALUE)
        );

        add(jPanel3, java.awt.BorderLayout.LINE_END);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel6.setPreferredSize(new java.awt.Dimension(532, 50));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("SEARCH");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(675, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(0, 15, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jPanel7.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee ID", "Frist Name", "Last Name", "Email", "Employee Role"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel7.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton3.setText("View Attendace");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setText("Checkin");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton2.setText("Checkout");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addGap(369, 369, 369)
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        jPanel5.add(jPanel7, java.awt.BorderLayout.CENTER);

        add(jPanel5, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
         
        
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
    public void keyReleased(java.awt.event.KeyEvent evt) {
        String searchText = jTextField1.getText().trim();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        jTable1.setRowSorter(sorter);

        if (searchText.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            RowFilter<DefaultTableModel, Object> rf = RowFilter.regexFilter("(?i)" + searchText, 0, 1, 2, 3, 4);
            sorter.setRowFilter(rf);
        }
    }
});
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        
           searchattendance searchattendance  = new searchattendance();
       
      jPanel5.removeAll();
       jPanel5.add(searchattendance, BorderLayout.CENTER);
       
       jPanel5.revalidate();
       jPanel5.repaint();
    }                                        

    

   
    private class ViewAttendanceDialog extends JDialog {
        private JTable attendanceTable;
        private DefaultTableModel attendanceModel;

        public ViewAttendanceDialog(Frame parent, boolean modal) {
            super(parent, modal);
            initDialog();
            loadAttendanceRecords();
        }

        private void initDialog() {
            setTitle("Attendance Records");
            setSize(800, 600);
            setLocationRelativeTo(getParent());
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            attendanceModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Date", "Employee ID", "Employee Name", "Check In", "Check Out", "Status"}
            );
            attendanceTable = new JTable(attendanceModel);
            JScrollPane scrollPane = new JScrollPane(attendanceTable);

            add(scrollPane, BorderLayout.CENTER);
        }

        private void loadAttendanceRecords() {
            try {
                String query = "SELECT ea.date, e.emp_id, CONCAT(e.first_name, ' ', e.last_name) as full_name, " +
                              "ea.checkin_time, ea.checkout_time, at.name as status " +
                              "FROM employee_attendance ea " +
                              "INNER JOIN employee e ON ea.employee_id = e.id " +
                              "INNER JOIN attendance_type at ON ea.attendance_type_id = at.id " +
                              "ORDER BY ea.date DESC, ea.checkin_time DESC";

                ResultSet rs = MySQL2.executeSearch(query);
                attendanceModel.setRowCount(0);

                while (rs.next()) {
                    Vector<String> row = new Vector<>();
                    row.add(rs.getString("date"));
                    row.add(rs.getString("emp_id"));
                    row.add(rs.getString("full_name"));
                    row.add(rs.getString("checkin_time"));
                    row.add(rs.getString("checkout_time") != null ? rs.getString("checkout_time") : "Not checked out");
                    row.add(rs.getString("status"));
                    attendanceModel.addRow(row);
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading attendance records: " + e.getMessage());
            }
        }
        
        
        
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       int selectedRow = jTable1.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select an employee from the table.");
        return;
    }

   DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    String internalEmpId = model.getValueAt(selectedRow, model.getColumnCount() - 1).toString();

  
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
    String date = sdfDate.format(new Date());
    String time = sdfTime.format(new Date());

    try {
     
        ResultSet rs = MySQL2.executeSearch(
           "SELECT * FROM employee_attendance WHERE employee_id = '" + internalEmpId + "' AND date = '" + date + "'"
        );

        if (rs.next()) {
            JOptionPane.showMessageDialog(this, "Employee has already checked in today.");
        } else {
        
            MySQL2.executeIUD(
                "INSERT INTO employee_attendance (employee_id, date, checkin_time,attendance_type_id) VALUES ('" + internalEmpId + "', '" + date + "', '" + time + "', '" + 1 + "')"
            );
            JOptionPane.showMessageDialog(this, "Check-in successful.");
        }

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error during check-in.");
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      
    int selectedRow = jTable1.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select an employee from the table.");
        return;
    }

    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    String internalEmpId = model.getValueAt(selectedRow, model.getColumnCount() - 1).toString();

    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
    String date = sdfDate.format(new Date());
    String time = sdfTime.format(new Date());

    try {
       
        ResultSet rs = MySQL2.executeSearch(
            "SELECT * FROM employee_attendance WHERE employee_id = '" + internalEmpId + "' AND date = '" + date + "'"
        );

        if (rs.next()) {
            String attendanceId = rs.getString("id");
            String checkoutTime = rs.getString("checkout_time");

            if (checkoutTime != null && !checkoutTime.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Employee has already checked out today.");
            } else {
             
                MySQL2.executeIUD(
                    "UPDATE employee_attendance SET checkout_time = '" + time + "' WHERE id = '" + attendanceId + "'"
                );
                JOptionPane.showMessageDialog(this, "Checkout successful.");
                loadAttendance(); 
            }
        } else {
            JOptionPane.showMessageDialog(this, "Check-in not found. Please check in first.");
        }

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error during checkout: " + ex.getMessage());
    }




    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
