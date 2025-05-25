/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import callbacks.SelectionListener;
import model.SelectSupplier;
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
public class StockManagementSelectSupplier extends javax.swing.JFrame {

    private Timer debounceTimer;
    private int selectedRow = -1;
    private SelectionListener<SelectSupplier> selectionListener;

    public void setSelectionListener(SelectionListener<SelectSupplier> listener) {
        this.selectionListener = listener;
    }

    /**
     * Creates new form StockManagementSelectSupplier
     */
    public StockManagementSelectSupplier() {
        initComponents();
        loadSupplierTable();

        searchBar.getDocument().addDocumentListener(new DocumentListener() {
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

        supplierTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2 && supplierTable.getSelectedRow() != -1) {
                    selectedRow = supplierTable.getSelectedRow();

                    String supplierId = supplierTable.getValueAt(selectedRow, 0).toString();
                    String supplierName = supplierTable.getValueAt(selectedRow, 1).toString();
                    String mobile = supplierTable.getValueAt(selectedRow, 2).toString();
                    String email = supplierTable.getValueAt(selectedRow, 3).toString();
                    String companyId = supplierTable.getValueAt(selectedRow, 4).toString();
                    String status = supplierTable.getValueAt(selectedRow, 5).toString();

                    SelectSupplier supplier = new SelectSupplier(supplierId, supplierName, mobile, email, companyId,
                            status);

                    if (selectionListener != null) {
                        selectionListener.onItemSelected(supplier);
                    }

                    dispose();
                    ;
                }
            }
        });

    }

    private void loadSupplierTable() {
        DefaultTableModel model = (DefaultTableModel) supplierTable.getModel();
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

    private void searchSuppliers() {
        String keyword = searchBar.getText().trim();

        DefaultTableModel model = (DefaultTableModel) supplierTable.getModel();
        model.setRowCount(0); // Clear table

        if (keyword.isEmpty()) {
            loadSupplierTable(); // fallback to full table load
            return;
        }

        String query = String.format(
                "SELECT s.supplier_id, s.name, s.mobile, s.email, c.company_id, st.status " +
                        "FROM supplier s " +
                        "JOIN company c ON s.company_id = c.id " +
                        "JOIN status st ON s.status_id = st.id " +
                        "WHERE s.supplier_id LIKE '%%%s%%' OR " +
                        "s.name LIKE '%%%s%%' OR " +
                        "s.mobile LIKE '%%%s%%' OR " +
                        "s.email LIKE '%%%s%%' OR " +
                        "c.company_id LIKE '%%%s%%' OR " +
                        "st.status LIKE '%%%s%%'",
                keyword, keyword, keyword, keyword, keyword, keyword);

        try {
            ResultSet rs = MySQL2.executeSearch(query);
            boolean hasResults = false;

            while (rs.next()) {
                hasResults = true;

                String supplierId = rs.getString("supplier_id");
                String supplierName = rs.getString("name");
                String mobile = rs.getString("mobile");
                String email = rs.getString("email");
                String companyId = rs.getString("company_id"); // from company table
                String status = rs.getString("status"); // from status table

                model.addRow(new Object[] { supplierId, supplierName, mobile, email, companyId, status });
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
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        searchBar = new javax.swing.JTextField();
        resetSearch = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        supplierTable = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null }
                },
                new String[] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jScrollPane2.setViewportView(jTextPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        searchBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // searchBarActionPerformed(evt);
            }
        });

        resetSearch.setBackground(new java.awt.Color(255, 0, 0));
        resetSearch.setForeground(new java.awt.Color(0, 0, 0));
        resetSearch.setText("Reset");

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Stock Suppliers");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(searchBar, javax.swing.GroupLayout.PREFERRED_SIZE, 251,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(resetSearch)
                                .addGap(24, 24, 24))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(295, 295, 295)
                                .addComponent(jLabel1)
                                .addContainerGap(357, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(44, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(29, 29, 29)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(searchBar, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(resetSearch))
                                .addGap(20, 20, 20)));

        supplierTable.setBackground(new java.awt.Color(0, 0, 0));
        supplierTable.setForeground(new java.awt.Color(255, 255, 255));
        supplierTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(supplierTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 443,
                                        Short.MAX_VALUE)));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StockManagementSelectSupplier.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StockManagementSelectSupplier.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StockManagementSelectSupplier.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StockManagementSelectSupplier.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StockManagementSelectSupplier().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JButton resetSearch;
    private javax.swing.JTextField searchBar;
    private javax.swing.JTable supplierTable;
    // End of variables declaration//GEN-END:variables
}
