/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import static gui.AddEmployeeBranchAdmin.GenderMap;
import static gui.AddEmployeeBranchAdmin.branchMap;
import static gui.AddEmployeeBranchAdmin.roleMap;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import model.MySQL2;


public class BranchAdminSingleAddEmployee extends javax.swing.JFrame {

    public static HashMap<String, Integer> genderMap = new HashMap<>();
    public static HashMap<String, Integer> branchMap = new HashMap<>();
    public static HashMap<String, Integer> roleMap = new HashMap<>();

    // Callback interface for table refresh
    public interface EmployeeUpdateCallback {
        void onEmployeeAdded();
    }

    private EmployeeUpdateCallback updateCallback;

    public void setUpdateCallback(EmployeeUpdateCallback callback) {
        this.updateCallback = callback;
    }

    public BranchAdminSingleAddEmployee() {
        initComponents();
        loadBranch();
        loadGender();
        loadRole();
        
        // Update UI labels to reflect employee data
        jLabel1.setText("EMPLOYEE DETAILS");
        jLabel3.setText("Employee ID");
        jLabel10.setText("Branch");
        jButton1.setText("Update Employee");
        
        
    }
    
        

        private void loadGender() {
            try {
                ResultSet resultset = MySQL2.executeSearch("SELECT * FROM `gender`");

                Vector<String> vector = new Vector<>();
                vector.add("Select");
                genderMap.clear();

               while (resultset.next()) {
                vector.add(resultset.getString("type"));
                    genderMap.put(resultset.getString("type"), resultset.getInt("id"));
                }
                DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(vector);
                jComboBox1.setModel(comboBoxModel);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    private void loadBranch() {
        try {
            ResultSet resultset = MySQL2.executeSearch("SELECT * FROM `branch`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");
            branchMap.clear();

            while (resultset.next()) {
                vector.add(resultset.getString("name"));
                branchMap.put(resultset.getString("name"), resultset.getInt("id"));
            }
            DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(vector);
            jComboBox2.setModel(comboBoxModel);

        } catch (Exception e) {
        e.printStackTrace();
        }
    }

    private void loadRole() {
        try {
            ResultSet resultset = MySQL2.executeSearch("SELECT * FROM `employee_role`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");
            roleMap.clear();

            while (resultset.next()) {
                vector.add(resultset.getString("type"));
                roleMap.put(resultset.getString("type"), resultset.getInt("id"));
                }

                DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(vector);
                jComboBox3.setModel(comboBoxModel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField6 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Customer id");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setFocusable(false);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("ADD EMPLOYEE");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("First name");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Employee ID");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Last name");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Email ");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Gender");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Mobile");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Employee Name");

        jTextField1.setEditable(false);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTextField6.setEditable(false);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("ID");

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Role");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel7)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(138, 138, 138)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel5)
                                .addComponent(jTextField4)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel6)
                                        .addComponent(jComboBox1, 0, 204, Short.MAX_VALUE)
                                        .addComponent(jLabel11)
                                        .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10)
                                        .addComponent(jComboBox2, 0, 213, Short.MAX_VALUE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(124, 124, 124)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String id = jTextField6.getText(); // This is the ID from the database
        String empId = jTextField1.getText(); // This is the employee_id field
        String fname = jTextField2.getText();
        String lname = jTextField3.getText();
        String email = jTextField4.getText();
        String mobile = jTextField5.getText();
        String gender = String.valueOf(jComboBox1.getSelectedItem());
        String branch = String.valueOf(jComboBox2.getSelectedItem());
        
        String role = "Select";
        /* 
        if (jComboBox3 != null) {
            role = String.valueOf(jComboBox3.getSelectedItem());
        }
        */
        
        // Get password - if you have a password field
        String password = "";
        /*
        if (jPasswordField1 != null) {
            password = String.valueOf(jPasswordField1.getPassword());
        } else {
        */
        
        try {
                ResultSet rs = MySQL2.executeSearch("SELECT `password` FROM `employee` WHERE `id` = '" + id + "'");
                if (rs.next()) {
                    password = rs.getString("password");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        
        
        String salary = "0";
        /*
        if (jTextField7 != null) {
            salary = jTextField7.getText();
        } else {
        */
            // Try to get the existing salary from the database
            try {
                ResultSet rs = MySQL2.executeSearch("SELECT `selected_salary` FROM `employee` WHERE `id` = '" + id + "'");
                if (rs.next()) {
                    salary = rs.getString("selected_salary");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            
            
            
            
        
        if (fname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter first name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (lname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter last name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter email", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            JOptionPane.showMessageDialog(this, "Email address invalid, please try again.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (mobile.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter mobile", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (gender.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select gender", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (branch.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select branch", "Warning", JOptionPane.WARNING_MESSAGE);
        // } else if (jComboBox3 != null && role.equals("Select")) {
        //    JOptionPane.showMessageDialog(this, "Please select role", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            // Check if values exist in HashMaps first
                    if (!genderMap.containsKey(gender)) {
                        JOptionPane.showMessageDialog(this, "Invalid gender selection", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    if (!branchMap.containsKey(branch)) {
                        JOptionPane.showMessageDialog(this, "Invalid branch selection", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int genderId = genderMap.get(gender);
                    int branchId = branchMap.get(branch);

            int roleId = 1;
            /* if (jComboBox3 != null && !role.equals("Select")) {
                roleId = RoleMap.get(role);
            } */

            try {
                ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM `employee` WHERE (`mobile` = '" + mobile + "' OR `email` = '" + email + "') AND `id` != '" + id + "'");
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "This mobile number or email is already in use by another employee.");
                } else {
                    // Update employee with correct field names
                   String query = "UPDATE `employee` SET "
                + "`emp_id` = '" + empId + "', "
                + "`first_name` = '" + fname + "', "
                + "`last_name` = '" + lname + "', "
                + "`email` = '" + email + "', "
                + "`mobile` = '" + mobile + "', "
                + "`password` = '" + password + "', "
                + "`gender_id` = '" + genderId + "', "
                + "`branch_id` = '" + branchId + "', "  
                + "`employee_role_id` = '" + roleId + "', "
                + "`selected_salary` = '" + salary + "' "
                + "WHERE `id` = '" + id + "'";

            System.out.println("Executing query: " + query); // Debug output
            int rowsAffected = MySQL2.executeIUD(query);
            System.out.println("Rows affected: " + rowsAffected);

            if (rowsAffected > 0) {
                System.out.println("Update successful!");
            } else {
                System.out.println("Update failed - no rows affected!");
            }

                JOptionPane.showMessageDialog(this, "Employee added successfully.");

// Debug output
                    System.out.println("About to check callback...");
                    System.out.println("Callback is null: " + (updateCallback == null));

                    // Refresh the parent table
                    if (updateCallback != null) {
                        System.out.println("Calling callback...");
                        updateCallback.onEmployeeAdded();
                    } else {
                        System.out.println("No callback set!");
                    }

                    this.dispose();
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred while updating employee data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        FlatMacDarkLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BranchAdminSingleAddEmployee().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    public javax.swing.JComboBox<String> jComboBox1;
    public javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public javax.swing.JTextField jTextField1;
    public javax.swing.JTextField jTextField2;
    public javax.swing.JTextField jTextField3;
    public javax.swing.JTextField jTextField4;
    public javax.swing.JTextField jTextField5;
    public javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
