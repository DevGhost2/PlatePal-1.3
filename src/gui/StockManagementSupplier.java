/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

package gui;

import java.sql.ResultSet;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentListener;
import model.MySQL2;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Oshadha Bhanu
 */
public class StockManagementSupplier extends javax.swing.JPanel {

    private Timer debounceTimer;

    /**
     * Creates new form StockManagementSupplier
     */
    public StockManagementSupplier() {
        initComponents();
        loadCompanyIDs();
        loadSupplierTable();
        loadActiveBox();

        SearchBar.getDocument().addDocumentListener(new DocumentListener() {
            private final int DEBOUNCE_DELAY = 300; // milliseconds

            public void insertUpdate(DocumentEvent e) {
                debounceSearch();
            }

            public void removeUpdate(DocumentEvent e) {
                debounceSearch();
            }

            public void changedUpdate(DocumentEvent e) {
                debounceSearch();
            }

            private void debounceSearch() {
                if (debounceTimer != null && debounceTimer.isRunning()) {
                    debounceTimer.restart();
                } else {
                    debounceTimer = new Timer(DEBOUNCE_DELAY, new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            searchSuppliers();
                            debounceTimer.stop();
                        }
                    });
                    debounceTimer.setRepeats(false);
                    debounceTimer.start();
                }
            }
        });

        UpdateAccount.setEnabled(false);
        CreateAccount.setEnabled(true);
        CompanyID.setEnabled(true);

        SupplierTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2 && SupplierTable.getSelectedRow() != -1) {
                    int selectedRow = SupplierTable.getSelectedRow();

                    String supplierId = SupplierTable.getValueAt(selectedRow, 0).toString();
                    String supplierName = SupplierTable.getValueAt(selectedRow, 1).toString();
                    String mobile = SupplierTable.getValueAt(selectedRow, 2).toString();
                    String email = SupplierTable.getValueAt(selectedRow, 3).toString();
                    String companyId = SupplierTable.getValueAt(selectedRow, 4).toString();

                    String[] nameParts = supplierName.split(" ", 2);
                    FNTextField.setText(nameParts[0]);
                    LNTextField.setText(nameParts.length > 1 ? nameParts[1] : "");

                    MobileTextField.setText(mobile);
                    EmailTextField.setText(email);

                    for (int i = 0; i < CompanyID.getItemCount(); i++) {
                        String item = CompanyID.getItemAt(i);
                        if (item.startsWith(companyId + " ")) {
                            CompanyID.setSelectedIndex(i);
                            break;
                        }
                    }

                    CompanyID.setEnabled(false);
                    CreateAccount.setEnabled(false);
                    UpdateAccount.setEnabled(true);
                }
            }
        });

    }

    private void loadCompanyIDs() {
        try {
            ResultSet rs = MySQL2.executeSearch("SELECT company_id, name FROM company");
            CompanyID.removeAllItems();
            while (rs.next()) {
                CompanyID.addItem(rs.getString("company_id") + " - " + rs.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading company IDs: " + e.getMessage());
        }
    }

    private void loadActiveBox() {
        try {
            ResultSet rs = MySQL2.executeSearch("SELECT status FROM status");
            StatusBox.removeAllItems();
            while (rs.next()) {
                StatusBox.addItem(rs.getString("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading Active Status Selection Box: " + e.getMessage());
        }
    }

    private void loadSupplierTable() {
        DefaultTableModel model = (DefaultTableModel) SupplierTable.getModel();
        model.setRowCount(0);

        // String query = "SELECT s.supplier_id, s.name, s.mobile, s.email,
        // c.company_id, " +
        // "FROM supplier s " +
        // "JOIN company c ON s.company_id = c.id";

        String query = "SELECT s.supplier_id, s.name, s.mobile, s.email, c.company_id, st.status " +
                "FROM supplier s " +
                "JOIN company c ON s.company_id = c.id " +
                "JOIN status st ON s.status_id = st.id";

        try {
            ResultSet rs = MySQL2.executeSearch(query);

            while (rs.next()) {
                String supplierId = rs.getString("supplier_id");
                String supplierName = rs.getString("name");
                String mobile = rs.getString("mobile");
                String email = rs.getString("email");
                String companyID = rs.getString("company_id");
                String statusID = rs.getString("status");

                model.addRow(new Object[] { supplierId,
                        supplierName,
                        mobile,
                        email,
                        companyID,
                        statusID
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading supplier table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearFields() {
        FNTextField.setText("");
        LNTextField.setText("");
        MobileTextField.setText("");
        EmailTextField.setText("");
        CompanyID.setSelectedIndex(0);

        UpdateAccount.setEnabled(false);
        CreateAccount.setEnabled(true);
        CompanyID.setEnabled(true);
    }

    private String generateNewSupplierID() {
        int maxId = 0;
        try {
            ResultSet rs = MySQL2.executeSearch("SELECT supplier_id FROM supplier");
            while (rs.next()) {
                String id = rs.getString("supplier_id").replace("SUPP", "");
                int num = Integer.parseInt(id);
                if (num > maxId) {
                    maxId = num;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "SUPP" + String.format("%04d", maxId + 1);
    }

    private boolean validateInput(String firstName, String lastName, String mobile, String email) {
        StringBuilder errorMsg = new StringBuilder();

        String nameRegex = "^[A-Za-z][A-Za-z\\s\\-']{1,}$";
        String mobileRegex = "^(07\\d{8})$";
        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,6}$";

        if (!firstName.matches(nameRegex)) {
            errorMsg.append(
                    "- First name must be at least 2 characters and contain only letters, spaces, hyphens, or apostrophes.\n");
        }

        if (!lastName.matches(nameRegex)) {
            errorMsg.append(
                    "- Last name must be at least 2 characters and contain only letters, spaces, hyphens, or apostrophes.\n");
        }

        if (!mobile.matches(mobileRegex)) {
            errorMsg.append("- Mobile must be 10 digits and start with 07.\n");
        }

        if (!email.matches(emailRegex)) {
            errorMsg.append("- Invalid email format.\n");
        }

        if (errorMsg.length() > 0) {
            JOptionPane.showMessageDialog(null, "Please fix the following:\n\n" + errorMsg.toString(),
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    private void searchSuppliers() {
        String keyword = SearchBar.getText().trim();

        DefaultTableModel model = (DefaultTableModel) SupplierTable.getModel();
        model.setRowCount(0); // Clear table

        if (keyword.isEmpty()) {
            loadSupplierTable();
            return;
        }

        String query = String.format(
                "SELECT * FROM supplier WHERE " +
                        "supplier_id LIKE '%%%s%%' OR " +
                        "name LIKE '%%%s%%' OR " +
                        "mobile LIKE '%%%s%%' OR " +
                        "email LIKE '%%%s%%' OR " +
                        "company_id LIKE '%%%s%%'",
                keyword, keyword, keyword, keyword, keyword);

        try {
            ResultSet rs = MySQL2.executeSearch(query);

            boolean hasResults = false;

            while (rs.next()) {
                hasResults = true;

                String supplierId = rs.getString("supplier_id");
                String supplierName = rs.getString("name");
                String mobile = rs.getString("mobile");
                String email = rs.getString("email");
                String companyID = rs.getString("company_id");

                model.addRow(new Object[] { supplierId, supplierName, mobile, email, companyID });
            }

            if (!hasResults) {
                JOptionPane.showMessageDialog(this, "No suppliers matched your search!", "No Results",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching supplier data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        FNTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        LNTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        MobileTextField = new javax.swing.JTextField();
        EmailTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        CreateAccount = new javax.swing.JButton();
        UpdateAccount = new javax.swing.JButton();
        ResetRegistration = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        CompanyID = new javax.swing.JComboBox<>();
        StatusBox = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        SearchBar = new javax.swing.JTextField();
        ResetSearch = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        SupplierTable = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        FNTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FNTextFieldActionPerformed(evt);
            }
        });

        jLabel1.setText("First Name");

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Supplier Registration");

        jLabel3.setText("Last Name");

        LNTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LNTextFieldActionPerformed(evt);
            }
        });

        jLabel4.setText("Mobile");

        MobileTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MobileTextFieldActionPerformed(evt);
            }
        });

        EmailTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmailTextFieldActionPerformed(evt);
            }
        });

        jLabel5.setText("Email");

        CreateAccount.setBackground(new java.awt.Color(102, 255, 102));
        CreateAccount.setText("Create Account");
        CreateAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateAccountActionPerformed(evt);
            }
        });

        UpdateAccount.setBackground(new java.awt.Color(51, 51, 255));
        UpdateAccount.setText("Update Account");
        UpdateAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateAccountActionPerformed(evt);
            }
        });

        ResetSearch.setBackground(new java.awt.Color(255, 51, 51));
        ResetSearch.setText("Reset");
        ResetSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetSearchActionPerformed(evt);
            }
        });

        ResetRegistration.setBackground(new java.awt.Color(255, 51, 51));
        ResetRegistration.setText("Reset");
        ResetRegistration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetRegistrationActionPerformed(evt);
            }
        });

        jLabel12.setText("Company_ID");

        CompanyID.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        CompanyID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CompanyIDActionPerformed(evt);
            }
        });

        StatusBox.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        StatusBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StatusBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(FNTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                        .addComponent(LNTextField)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                        .addComponent(MobileTextField)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                        .addComponent(EmailTextField)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(56, 56, 56)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(UpdateAccount)
                                                                        .addComponent(CreateAccount)))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(76, 76, 76)
                                                                .addComponent(ResetRegistration)))
                                                .addGap(0, 62, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                        .addComponent(CompanyID, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(StatusBox, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(jLabel2)
                                .addGap(30, 30, 30)
                                .addComponent(jLabel1)
                                .addGap(5, 5, 5)
                                .addComponent(FNTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(LNTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(MobileTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(EmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(CompanyID, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(68, 68, 68)
                                .addComponent(StatusBox, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(68, 68, 68)
                                .addComponent(CreateAccount)
                                .addGap(18, 18, 18)
                                .addComponent(UpdateAccount)
                                .addGap(18, 18, 18)
                                .addComponent(ResetRegistration)
                                .addContainerGap(40, Short.MAX_VALUE)));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        SearchBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchBarActionPerformed(evt);
            }
        });

        ResetSearch.setBackground(new java.awt.Color(255, 51, 51));
        ResetSearch.setText("Rest");

        jLabel10.setText("Search");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(SearchBar, javax.swing.GroupLayout.PREFERRED_SIZE, 606,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(42, 42, 42)
                                                .addComponent(ResetSearch)))
                                .addContainerGap(174, Short.MAX_VALUE)));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addContainerGap(14, Short.MAX_VALUE)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(SearchBar, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(ResetSearch))
                                .addGap(45, 45, 45)));

        SupplierTable.setBackground(new java.awt.Color(0, 0, 0));
        SupplierTable.setForeground(new java.awt.Color(255, 255, 255));
        SupplierTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null, null, null },
                        { null, null, null, null, null, null },
                        { null, null, null, null, null, null },
                        { null, null, null, null, null, null }
                },
                new String[] {
                        "Supplier ID", "Supplier Name", "Mobile", "Email", "Company ID", "Status"
                }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        jScrollPane1.setViewportView(SupplierTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addContainerGap())
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 918,
                                                Short.MAX_VALUE))));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jScrollPane1)
                                .addContainerGap()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(0, 0, 0)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, 0)));
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBox1ActionPerformed
        // tahike
        // TODO add your handling code here:
    }// GEN-LAST:event_jComboBox1ActionPerformed

    private void SearchBarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_SearchBarActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_SearchBarActionPerformed

    private void ResetSearchActionPerformed(java.awt.event.ActionEvent evt) {
        SearchBar.setText("");
    }

    private void FNTextFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_FNTextFieldActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_FNTextFieldActionPerformed

    private void LNTextFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_LNTextFieldActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_LNTextFieldActionPerformed

    private void MobileTextFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_MobileTextFieldActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_MobileTextFieldActionPerformed

    private void EmailTextFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_EmailTextFieldActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_EmailTextFieldActionPerformed

    private void CompanyIDActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_CompanyIDActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_CompanyIDActionPerformed

    private void StatusBoxActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void UpdateAccountActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_UpdateAccountActionPerformed
        // JOptionPane.showMessageDialog(this, "Error", "errorrrr",
        // JOptionPane.WARNING_MESSAGE);
        try {
            int selectedRow = SupplierTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a supplier to update.");
                return;
            }

            String supplierId = SupplierTable.getValueAt(selectedRow, 0).toString();
            String name = FNTextField.getText().trim() + " " + LNTextField.getText().trim();
            String mobile = MobileTextField.getText().trim();
            String email = EmailTextField.getText().trim();
            String selectedCompanyCombo = CompanyID.getSelectedItem().toString();
            String selectedCompanyCode = selectedCompanyCombo.split(" - ")[0];

            System.out.println("########################\n\n\n\n\n");
            System.out.println(selectedCompanyCode);
            System.out.println("\n\n\n\n\n########################");

            if (!validateInput(name, name, mobile, email)) {

                return;

            } else {
                // 🔍 Step: Find the `id` from `company` table where `company_id` =
                // selectedCompanyCode
                String companyQuery = "SELECT id FROM company WHERE company_id = '"
                        + selectedCompanyCode.replace("'", "''") + "'";
                ResultSet rs = MySQL2.executeSearch(companyQuery);
                int companyId = -1;
                if (rs.next()) {
                    companyId = rs.getInt("id");
                } else {
                    JOptionPane.showMessageDialog(this, "Error: Company not found.");
                    return;
                }

                // 👷 Build the UPDATE query
                name = name.replace("'", "''");
                mobile = mobile.replace("'", "''");
                email = email.replace("'", "''");

                String updateQuery = "UPDATE supplier SET " +
                        "name = '" + name + "', " +
                        "mobile = '" + mobile + "', " +
                        "email = '" + email + "', " +
                        "company_id = " + companyId + " " +
                        "WHERE supplier_id = '" + supplierId + "'";

                int rowsUpdated = MySQL2.executeIUD(updateQuery);

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Supplier updated successfully.");
                    loadSupplierTable(); // Refresh table
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update supplier.");
                }

                clearFields();
                UpdateAccount.setEnabled(false);
                CreateAccount.setEnabled(true);
                CompanyID.setEnabled(true);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }// GEN-LAST:event_UpdateAccountActionPerformed

    private void CreateAccountActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_CreateAccountActionPerformed
        String firstName = FNTextField.getText().trim();
        String lastName = LNTextField.getText().trim();
        String name = firstName + " " + lastName;
        String mobile = MobileTextField.getText().trim();
        String email = EmailTextField.getText().trim();
        String comboValue = (String) CompanyID.getSelectedItem();

        String SupplierID = generateNewSupplierID();

        String CompanyIDStr = (comboValue.split(" - ")[0].replaceAll("[^\\d]", ""));
        int CompanyIDInt = Integer.parseInt(CompanyIDStr);

        if (!validateInput(firstName, lastName, mobile, email)) {
            return;

        } else {
            try {
                String checkQuery = String.format("SELECT * FROM supplier WHERE supplier_id = '%s' OR email = '%s'",
                        SupplierID, email);

                ResultSet rs = MySQL2.executeSearch(checkQuery);

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "A supplier with this ID or email already exists!");
                } else {
                    String query = String.format(
                            "INSERT INTO supplier (supplier_id, name, mobile, email, company_id)" +
                                    "VALUES ( '%s', '%s', '%s', '%s', %d)",
                            SupplierID, name, mobile, email, CompanyIDInt);

                    MySQL2.executeIUD(query);
                    JOptionPane.showMessageDialog(this, "Supplier created successfully!");

                    loadSupplierTable();
                    clearFields();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error creating supplier: " + e.getMessage());
                e.printStackTrace();
            }

        }

    }// GEN-LAST:event_CreateAccountActionPerformed

    private void ResetRegistrationActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ResetRegistrationActionPerformed
        clearFields();
    }// GEN-LAST:event_ResetRegistrationActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CompanyID;
    private javax.swing.JButton CreateAccount;
    private javax.swing.JTextField EmailTextField;
    private javax.swing.JTextField FNTextField;
    private javax.swing.JTextField LNTextField;
    private javax.swing.JTextField MobileTextField;
    private javax.swing.JButton ResetRegistration;
    private javax.swing.JButton ResetSearch;
    private javax.swing.JTextField SearchBar;
    private javax.swing.JComboBox<String> StatusBox;
    private javax.swing.JTable SupplierTable;
    private javax.swing.JButton UpdateAccount;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
