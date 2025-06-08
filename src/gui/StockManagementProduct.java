/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import java.sql.ResultSet;

import java.sql.ResultSet;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentListener;
import model.MySQL2;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.awt.JobAttributes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Oshadha Bhanu
 */
public class StockManagementProduct extends javax.swing.JPanel {

    private Timer debounceTimer;
    private int selectedRow = -1;

    /**
     * Creates new form StockManagementProduct
     */
    public StockManagementProduct() {
        initComponents();
        loadProductTable();
        loadQuantityTypeBox();
        loadStatusBox();

        productCreate.setEnabled(true);
        productUpdate.setEnabled(false);
        resetName.setEnabled(true);

        productSearch.getDocument().addDocumentListener(new DocumentListener() {
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
                            searchProducts();
                            debounceTimer.stop();
                        }
                    });
                    debounceTimer.setRepeats(false);
                    debounceTimer.start();
                }
            }
        });
        ProductTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2 && ProductTable.getSelectedRow() != -1) {
                    selectedRow = ProductTable.getSelectedRow();

                    String prodId = ProductTable.getValueAt(selectedRow, 0).toString();
                    String prodName = ProductTable.getValueAt(selectedRow, 1).toString();
                    String qtytype = ProductTable.getValueAt(selectedRow, 2).toString();
                    String status = ProductTable.getValueAt(selectedRow, 3).toString();

                    productStatus.setSelectedItem(status);
                    quantityType.setSelectedItem(qtytype);
                    productName.setText(prodName);

                    productCreate.setEnabled(false);
                    productUpdate.setEnabled(true);
                    resetName.setEnabled(false);
                }
            }
        });
    }

    private boolean validateInput(String name) {
        StringBuilder errorMsg = new StringBuilder();

        String nameRegex = "^[A-Za-z0-9][A-Za-z0-9\\s\\-'.%]{2,}$";

        if (!name.matches(nameRegex)) {
            errorMsg.append(
                    "- First name must be at least 2 characters and contain only letters, spaces, hyphens, or apostrophes.\n");
        }

        if (errorMsg.length() > 0) {
            JOptionPane.showMessageDialog(null, "Please fix the following:\n\n" + errorMsg.toString(),
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    private String generateProductId() {
        int maxId = 0;
        try {
            ResultSet rs = MySQL2.executeSearch("SELECT stock_product_id FROM stock_product");
            while (rs.next()) {
                String id = rs.getString("stock_product_id").replace("STPRO", "");
                int num = Integer.parseInt(id);
                if (num > maxId) {
                    maxId = num;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "STPRO" + String.format("%04d", maxId + 1);

    }

    private void loadProductTable() {
        DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
        model.setRowCount(0);

        // String query = "SELECT sp.stock_product_id, sp.title, s.status AS status,
        // tp.type AS quantityType " +
        // "FROM stock_product sp " +
        // "JOIN status s ON sp.status_id = s.id";

        String query = "SELECT sp.stock_product_id, sp.title, s.status AS status, qt.type AS quantityType " +
                "FROM stock_product sp " +
                "JOIN status s ON sp.status_id = s.id " +
                "JOIN qty_type qt ON sp.qty_type_id = qt.id";

        try {
            ResultSet rs = MySQL2.executeSearch(query);

            while (rs.next()) {
                String productID = rs.getString("stock_product_id");
                String title = rs.getString("title");
                String qtytype = rs.getString("quantityType");
                String status = rs.getString("status");

                model.addRow(new Object[] {
                        productID,
                        title,
                        qtytype,
                        status
                });

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading Product Table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadStatusBox() {
        try {
            ResultSet rs = MySQL2.executeSearch("SELECT status FROM status");
            productStatus.removeAllItems();
            while (rs.next()) {
                productStatus.addItem(rs.getString("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading Active Status Selection Box: " + e.getMessage());
        }
    }

    private void loadQuantityTypeBox() {
        try {
            ResultSet rs = MySQL2.executeSearch("SELECT type FROM qty_type");
            quantityType.removeAllItems();
            while (rs.next()) {
                quantityType.addItem(rs.getString("type"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading Quantity Type Selection Box: " + e.getMessage());
        }
    }

    private void searchProducts() {
        String keyword = productSearch.getText().trim();

        DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
        model.setRowCount(0);

        if (keyword.isEmpty()) {
            loadProductTable();
            return;
        }

        String query = String.format(
                "SELECT p.stock_product_id, p.title, st.status, qt.type " +
                        "FROM stock_product p " +
                        "JOIN status st ON p.status_id = st.id " +
                        "JOIN qty_type qt ON p.qty_type_id = qt.id " +
                        "WHERE p.stock_product_id LIKE '%%%s%%' OR " +
                        "p.title LIKE '%%%s%%' OR " +
                        "qt.type LIKE '%%%s%%' OR " +
                        "st.status LIKE '%%%s%%'",
                keyword, keyword, keyword, keyword);

        try {
            ResultSet rs = MySQL2.executeSearch(query);

            boolean hasResults = false;

            while (rs.next()) {
                hasResults = true;

                String stockProductID = rs.getString("stock_product_id");
                String title = rs.getString("title");
                String qttype = rs.getString("type");
                String status = rs.getString("status");

                model.addRow(new Object[] { stockProductID, title, qttype, status });

            }

            if (!hasResults) {
                JOptionPane.showMessageDialog(this, "No Stock Products matched your search!", "No Results",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching Stock Products data: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private void clearFields() {
        productName.setText("");
        productStatus.setSelectedIndex(0);
        productSearch.setText("");

        productCreate.setEnabled(true);
        productUpdate.setEnabled(false);
        resetName.setEnabled(true);
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel8 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        productName = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        productStatus = new javax.swing.JComboBox<>();
        quantityType = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        productCreate = new javax.swing.JButton();
        productUpdate = new javax.swing.JButton();
        resetName = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        productSearch = new javax.swing.JTextField();
        searchReset = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ProductTable = new javax.swing.JTable();

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 66, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 585, Short.MAX_VALUE)
        );

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jPanel2.setMinimumSize(new java.awt.Dimension(200, 278));
        jPanel2.setPreferredSize(new java.awt.Dimension(312, 300));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setLayout(new java.awt.GridLayout(2, 1, 0, 10));

        jPanel6.setLayout(new java.awt.GridLayout(0, 1));

        jLabel1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel1.setText("Stock Product Item Name");
        jPanel6.add(jLabel1);

        productName.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        productName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productNameActionPerformed(evt);
            }
        });
        jPanel6.add(productName);

        jPanel5.add(jPanel6);

        jPanel7.setLayout(new java.awt.GridLayout(0, 1, 0, 10));

        productStatus.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        productStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        productStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productStatusActionPerformed(evt);
            }
        });
        jPanel7.add(productStatus);

        quantityType.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        quantityType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel7.add(quantityType);

        jPanel5.add(jPanel7);

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, -10, 312, 160));

        jPanel4.setLayout(new java.awt.GridLayout(0, 1, 10, 5));

        productCreate.setBackground(new java.awt.Color(0, 153, 0));
        productCreate.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        productCreate.setText("Create Product");
        productCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productCreateActionPerformed(evt);
            }
        });
        jPanel4.add(productCreate);

        productUpdate.setBackground(new java.awt.Color(0, 0, 255));
        productUpdate.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        productUpdate.setText("Update Product");
        productUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productUpdateActionPerformed(evt);
            }
        });
        jPanel4.add(productUpdate);

        resetName.setBackground(new java.awt.Color(204, 0, 0));
        resetName.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        resetName.setText("Reset");
        resetName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetNameActionPerformed(evt);
            }
        });
        jPanel4.add(resetName);

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 260, 140));

        add(jPanel2);

        jPanel9.setPreferredSize(new java.awt.Dimension(20, 585));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 585, Short.MAX_VALUE)
        );

        add(jPanel9);

        jPanel1.setLayout(new java.awt.BorderLayout());

        productSearch.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        productSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productSearchActionPerformed(evt);
            }
        });

        searchReset.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        searchReset.setText("Reset Search");
        searchReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(productSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(searchReset)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(productSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchReset))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        ProductTable.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        ProductTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Product ID", "Name", "Quantity Type", "Status"
            }
        ));
        jScrollPane1.setViewportView(ProductTable);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(jPanel1);
    }// </editor-fold>//GEN-END:initComponents

    private void productNameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_productNameActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_productNameActionPerformed

    private void productUpdateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_productUpdateActionPerformed
        try {
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please Select a Product to update.");
                return;
            }

            String productId = ProductTable.getValueAt(selectedRow, 0).toString();
            String name = productName.getText().trim();
            String qtyType = (String) quantityType.getSelectedItem();
            String status = (String) productStatus.getSelectedItem();

            if (!validateInput(name)) {
                return;
            }

            String qtTypeQuery = "SELECT id FROM qty_type WHERE type = '" + qtyType.replace("'", "''") + "'";
            ResultSet qtTypeRs = MySQL2.executeSearch(qtTypeQuery);
            int qtTypeID = -1;
            if (qtTypeRs.next()) {
                qtTypeID = qtTypeRs.getInt("id");
            } else {
                JOptionPane.showMessageDialog(this, "Error: Quantity Type ID not found.");
                return;
            }

            String statusQuery = "SELECT id FROM status WHERE status = '" + status.replace("'", "''") + "'";
            ResultSet statusRs = MySQL2.executeSearch(statusQuery);
            int statusId = -1;
            if (statusRs.next()) {
                statusId = statusRs.getInt("id");
            } else {
                JOptionPane.showMessageDialog(this, "Error: Status not found.");
                return;
            }

            name = name.replace("'", "''");
            productId = productId.replace("'", "''");

            String updateQuery = "UPDATE stock_product SET " +
                    "title = '" + name + "', " +
                    "qty_type_id = " + qtTypeID + ", " + // Added comma here
                    "status_id = " + statusId + " " +
                    "WHERE stock_product_id = '" + productId + "'";

            int rowsUpdated = MySQL2.executeIUD(updateQuery);

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Supplier updated successfully.");
                loadProductTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update supplier.");
            }

            selectedRow = -1;
            clearFields();
            loadProductTable();
            productCreate.setEnabled(true);
            productUpdate.setEnabled(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }// GEN-LAST:event_productUpdateActionPerformed

    private void resetNameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_resetNameActionPerformed
        clearFields();
    }// GEN-LAST:event_resetNameActionPerformed

    private void productSearchActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_productSearchActionPerformed

    }

    private void searchResetActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchResetActionPerformed
        productSearch.setText("");
    }// GEN-LAST:event_searchResetActionPerformed

    private void productCreateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_productCreateActionPerformed
        String prodname = productName.getText().trim();
        String status = (String) productStatus.getSelectedItem();
        String qtyType = (String) quantityType.getSelectedItem();
        String productID = generateProductId();

        if (!validateInput(prodname)) {
            return;
        } else {
            try {
                String checkQuery = String.format(
                        "SELECT * FROM stock_product WHERE stock_product_id = '%s' OR title = '%s'",
                        productID, prodname);

                ResultSet rs = MySQL2.executeSearch(checkQuery);

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "A product with this ID or Name already exists!");
                } else {
                    String statusQuery = "SELECT id FROM status WHERE status = '" + status.replace("'", "''") + "'";
                    ResultSet statusRs = MySQL2.executeSearch(statusQuery);
                    int statusId = -1;

                    if (statusRs.next()) {
                        statusId = statusRs.getInt("id");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error: Status not found.");
                        return;
                    }

                    String qtTypeQuery = "SELECT id FROM qty_type WHERE type = '" + qtyType.replace("'", "''") + "'";
                    ResultSet qtTypeRs = MySQL2.executeSearch(qtTypeQuery);
                    int qtTypeID = -1;
                    if (qtTypeRs.next()) {
                        qtTypeID = qtTypeRs.getInt("id");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error: Quantity Type ID not found.");
                        return;
                    }

                    String query = String.format(
                            "INSERT INTO stock_product (stock_product_id, title, status_id, qty_type_id) " +
                                    "VALUES ('%s', '%s', %d, %d)",
                            productID.replace("'", "''"),
                            prodname.replace("'", "''"),
                            statusId,
                            qtTypeID);

                    MySQL2.executeIUD(query);
                    JOptionPane.showMessageDialog(this, "Product Created Successfully!");

                    loadProductTable();
                    clearFields();

                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error Creating New Product " + e.getMessage(), "Error: ",
                        JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void productStatusActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_productStatusActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_productStatusActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ProductTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton productCreate;
    private javax.swing.JTextField productName;
    private javax.swing.JTextField productSearch;
    private javax.swing.JComboBox<String> productStatus;
    private javax.swing.JButton productUpdate;
    private javax.swing.JComboBox<String> quantityType;
    private javax.swing.JButton resetName;
    private javax.swing.JButton searchReset;
    // End of variables declaration//GEN-END:variables
}
