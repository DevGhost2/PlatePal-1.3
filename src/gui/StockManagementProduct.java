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

    /**
     * Creates new form StockManagementProduct
     */
    public StockManagementProduct() {
        initComponents();
        loadProductTable();
        loadStatusBox();

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
    }

    private void loadProductTable() {
        DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
        model.setRowCount(0);

        String query = "SELECT sp.stock_product_id, sp.title, s.status AS status " +
                "FROM stock_product sp " +
                "JOIN status s ON sp.status_id = s.id";

        try {
            ResultSet rs = MySQL2.executeSearch(query);

            while (rs.next()) {
                String productID = rs.getString("stock_product_id");
                String title = rs.getString("title");
                String status = rs.getString("status");

                model.addRow(new Object[] {
                        productID,
                        title,
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

    private void searchProducts() {
        String keyword = productSearch.getText().trim();

        DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
        model.setRowCount(0);

        if (keyword.isEmpty()) {
            loadProductTable();
            return;
        }

        String query = String.format(
                "SELECT p.stock_product_id, p.title, st.status " +
                        "FROM stock_product p " +
                        "JOIN status st ON p.status_id = st.id " +
                        "WHERE p.stock_product_id LIKE '%%%s%%' OR " +
                        "p.title LIKE '%%%s%%' OR " +
                        "st.status LIKE '%%%s%%'",
                keyword, keyword, keyword);

        try {
            ResultSet rs = MySQL2.executeSearch(query);

            boolean hasResults = false;

            while (rs.next()) {
                hasResults = true;

                String stockProductID = rs.getString("stock_product_id");
                String title = rs.getString("title");
                String status = rs.getString("status");

                model.addRow(new Object[] { stockProductID, title, status });

            }

            if (!hasResults) {
                JOptionPane.showMessageDialog(this, "No Stock Products matched your search!", "No Results",
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
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ProductTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        productName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        productCreate = new javax.swing.JButton();
        productUpdate = new javax.swing.JButton();
        productStatus = new javax.swing.JComboBox<>();
        resetName = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        productSearch = new javax.swing.JTextField();
        searchReset = new javax.swing.JButton();

        setBackground(new java.awt.Color(51, 51, 51));

        ProductTable.setBackground(new java.awt.Color(0, 0, 0));
        ProductTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null },
                        { null, null, null },
                        { null, null, null },
                        { null, null, null }
                },
                new String[] {
                        "Product ID", "Name", "Status"
                }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        jScrollPane1.setViewportView(ProductTable);

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        productName.setText("Enter Product Name");
        productName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productNameActionPerformed(evt);
            }
        });

        jLabel1.setText("Stock Product Item Name");

        productCreate.setBackground(new java.awt.Color(0, 153, 0));
        productCreate.setText("Create Product");
        productCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productCreateActionPerformed(evt);
            }
        });

        productUpdate.setBackground(new java.awt.Color(0, 0, 255));
        productUpdate.setText("Update Product");
        productUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productUpdateActionPerformed(evt);
            }
        });

        productStatus.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        productStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productStatusActionPerformed(evt);
            }
        });

        resetName.setBackground(new java.awt.Color(204, 0, 0));
        resetName.setText("Reset");
        resetName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(17, 17, 17)
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1)
                                                        .addComponent(productName,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 346,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(productStatus,
                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 113,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(112, 112, 112)
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(productUpdate,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(resetName, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(productCreate,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 113,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(18, Short.MAX_VALUE)));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(productName, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(productStatus, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(productCreate)
                                .addGap(18, 18, 18)
                                .addComponent(productUpdate)
                                .addGap(18, 18, 18)
                                .addComponent(resetName)
                                .addGap(101, 101, 101)));

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));

        productSearch.setText("Search Product");
        productSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productSearchActionPerformed(evt);
            }
        });

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
                                .addComponent(productSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 392,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)
                                .addComponent(searchReset)
                                .addContainerGap(119, Short.MAX_VALUE)));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(productSearch, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(searchReset))
                                .addContainerGap(56, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 683,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 387,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, 0)));
    }// </editor-fold>//GEN-END:initComponents

    private void productNameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_productNameActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_productNameActionPerformed

    private void productUpdateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_productUpdateActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_productUpdateActionPerformed

    private void resetNameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_resetNameActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_resetNameActionPerformed

    private void productSearchActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_productSearchActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_productSearchActionPerformed

    private void searchResetActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchResetActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_searchResetActionPerformed

    private void productCreateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_productCreateActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_productCreateActionPerformed

    private void productStatusActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_productStatusActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_productStatusActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ProductTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton productCreate;
    private javax.swing.JTextField productName;
    private javax.swing.JTextField productSearch;
    private javax.swing.JComboBox<String> productStatus;
    private javax.swing.JButton productUpdate;
    private javax.swing.JButton resetName;
    private javax.swing.JButton searchReset;
    // End of variables declaration//GEN-END:variables
}
