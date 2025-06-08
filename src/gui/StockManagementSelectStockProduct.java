/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import java.sql.ResultSet;

import java.sql.ResultSet;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentListener;
import model.MySQL2;
import model.SelectProduct;
import model.SelectSupplier;

import javax.swing.table.DefaultTableModel;

import callbacks.SelectionListener;

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
public class StockManagementSelectStockProduct extends javax.swing.JFrame {

    private SelectionListener<SelectProduct> selectionListener;
    private Timer debounceTimer;
    private int selectedRow = -1;

    public void setSelectionListener(SelectionListener<SelectProduct> listener) {
        this.selectionListener = listener;
    }

    /**
     * Creates new form StockManagementSelectStockProduct
     */
    public StockManagementSelectStockProduct() {
        initComponents();
        loadStockProductTable();

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
                            searchProducts();
                            debounceTimer.stop();
                        }
                    });
                    debounceTimer.setRepeats(false);
                    debounceTimer.start();
                }
            }
        });

        stockProductTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2 && stockProductTable.getSelectedRow() != -1) {
                    selectedRow = stockProductTable.getSelectedRow();

                    String prodId = stockProductTable.getValueAt(selectedRow, 0).toString();
                    String prodName = stockProductTable.getValueAt(selectedRow, 1).toString();
                    String qtytype = stockProductTable.getValueAt(selectedRow, 2).toString();
                    String status = stockProductTable.getValueAt(selectedRow, 3).toString();

                    SelectProduct product = new SelectProduct(prodId, prodName, status, qtytype);

                    if (selectionListener != null) {
                        selectionListener.onItemSelected(product);
                    }

                    dispose();
                }
            }
        });
    }

    private void searchProducts() {
        String keyword = searchBar.getText().trim();

        DefaultTableModel model = (DefaultTableModel) stockProductTable.getModel();
        model.setRowCount(0);

        if (keyword.isEmpty()) {
            loadStockProductTable();
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

    private void loadStockProductTable() {
        DefaultTableModel model = (DefaultTableModel) stockProductTable.getModel();
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

        jPanel1 = new javax.swing.JPanel();
        searchBar = new javax.swing.JTextField();
        resetSearch = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        stockProductTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        resetSearch.setBackground(new java.awt.Color(204, 0, 0));
        resetSearch.setForeground(new java.awt.Color(0, 0, 0));
        resetSearch.setText("Reset");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Stock Products");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(634, Short.MAX_VALUE)
                                .addComponent(searchBar, javax.swing.GroupLayout.PREFERRED_SIZE, 230,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(resetSearch)
                                .addGap(11, 11, 11))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(376, 376, 376)
                                .addComponent(jLabel1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(searchBar, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(resetSearch))
                                .addContainerGap()));

        stockProductTable.setBackground(new java.awt.Color(0, 0, 0));
        stockProductTable.setForeground(new java.awt.Color(255, 255, 255));
        stockProductTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null }
                },
                new String[] {
                        "Product ID", "Name", "Quantity Type", "Status"
                }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        jScrollPane1.setViewportView(stockProductTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 959, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 523,
                                        Short.MAX_VALUE)));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(StockManagementSelectStockProduct.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StockManagementSelectStockProduct.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StockManagementSelectStockProduct.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StockManagementSelectStockProduct.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StockManagementSelectStockProduct().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton resetSearch;
    private javax.swing.JTextField searchBar;
    private javax.swing.JTable stockProductTable;
    // End of variables declaration//GEN-END:variables
}
