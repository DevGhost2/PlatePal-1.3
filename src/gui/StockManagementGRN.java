/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.Timer;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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
public class StockManagementGRN extends javax.swing.JPanel {

        // don't touch these.
        private Timer debounceTimer;
        private int selectedRow = -1;
        private double totalAmount;

        // ⚠️Whutto, Bota ona pamkak me pahala thiyena variable ekata koraam.⚠️
        private static int branchID = 1; // ⬅️ employee id eka kohomahari me variable ekata load karaam. SQL query wenas
        private static int employeeID = 126; // koranda yannepa. query wenas korala kela unoth api na.

        /**
         * Creates new form StockManagementGRN
         */
        public StockManagementGRN() {
                initComponents();
                loadGRNTable();
                totalAmount = calculateTotalFromSixthColumn(grnTable);
                String totalAmountString = String.valueOf(totalAmount);
                totalBill.setText(totalAmountString);

                selectedSupplierName.setEnabled(false);
                selectedSupplierID.setEnabled(false);
                selectedStockProduct.setEnabled(false);
                selectedStockProductID.setEnabled(false);
                totalBill.setEnabled(false);
                balance.setEnabled(false);

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
                                                        searchGRN();
                                                        debounceTimer.stop();
                                                }
                                        });
                                        debounceTimer.setRepeats(false);
                                        debounceTimer.start();
                                }
                        }
                });

        }

        private void clearFields() {
                selectedStockProduct.setText("");
                selectedStockProductID.setText("");
                price.setText("");
                quantity.setText("");
                totalBill.setText("");
                paidAmount.setText("");
                balance.setText("");
                selectedSupplierName.setText("");
                selectedSupplierID.setText("");
                searchBar.setText("");
        }

        private double calculateTotalFromSixthColumn(JTable table) {
                double total = 0.0;
                DefaultTableModel model = (DefaultTableModel) table.getModel();

                for (int row = 0; row < model.getRowCount(); row++) {
                        Object value = model.getValueAt(row, 5);
                        if (value != null) {
                                try {
                                        total += Double.parseDouble(value.toString());
                                } catch (NumberFormatException e) {
                                        System.err.println("Invalid number at row " + row + ": " + value);
                                }
                        }
                }

                return total;
        }

        private String generateID(String column, String table, String Prefix) {
                int maxId = 0;
                Prefix = Prefix.trim();
                try {
                        ResultSet rs = MySQL2.executeSearch(String.format("SELECT %s FROM %s", column, table));
                        while (rs.next()) {
                                String id = rs.getString(column).replace(Prefix, "");
                                int num = Integer.parseInt(id);
                                if (num > maxId) {
                                        maxId = num;
                                }
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return Prefix + String.format("%04d", maxId + 1);
        }

        private boolean validateInput(String quantity, String price, String amountPaid) {
                StringBuilder errorMsg = new StringBuilder();

                // Quantity: whole number only, no decimals
                String quantityRegex = "^\\d+$";

                // Price and Amount Paid: number with optional decimal part
                String decimalRegex = "^\\d+(\\.\\d+)?$";

                if (!quantity.matches(quantityRegex)) {
                        errorMsg.append("- Quantity must be a whole number (no decimals allowed).\n");
                }

                if (!price.matches(decimalRegex)) {
                        errorMsg.append("- Price must be a valid number (decimals allowed).\n");
                }

                if (!amountPaid.matches(decimalRegex)) {
                        errorMsg.append("- Amount Paid must be a valid number (decimals allowed).\n");
                }

                if (errorMsg.length() > 0) {
                        JOptionPane.showMessageDialog(null,
                                        "Please fix the following:\n\n" + errorMsg.toString(),
                                        "Validation Error", JOptionPane.WARNING_MESSAGE);
                        return false;
                }

                return true;
        }

        private void searchGRN() {
                String keyword = searchBar.getText().trim();

                DefaultTableModel model = (DefaultTableModel) grnTable.getModel();
                model.setRowCount(0); // Clear table

                if (keyword.isEmpty()) {
                        loadGRNTable(); // fallback to full table load
                        return;
                }

                String query = String.format(
                                "SELECT gi.grn_id, sp.stock_product_id, sp.title, gi.quantity, gi.price " +
                                                "FROM grn_item gi " +
                                                "JOIN stock_product sp ON gi.stock_id = sp.id " +
                                                "WHERE gi.grn_id LIKE '%%%s%%' OR " +
                                                "sp.stock_product_id LIKE '%%%s%%' OR " +
                                                "sp.title LIKE '%%%s%%' OR " +
                                                "gi.quantity LIKE '%%%s%%' OR " +
                                                "gi.price LIKE '%%%s%%'",
                                keyword, keyword, keyword, keyword, keyword);

                try {
                        ResultSet rs = MySQL2.executeSearch(query);
                        boolean hasResults = false;

                        while (rs.next()) {
                                hasResults = true;

                                String grnId = rs.getString("grn_id");
                                String stockProductId = rs.getString("stock_product_id");
                                String stockProductName = rs.getString("title");
                                int quantity = rs.getInt("quantity");
                                double price = rs.getDouble("price");
                                double itemTotal = quantity * price;

                                model.addRow(new Object[] {
                                                grnId,
                                                stockProductId,
                                                stockProductName,
                                                quantity,
                                                price,
                                                itemTotal
                                });
                        }

                        if (!hasResults) {
                                JOptionPane.showMessageDialog(this, "No GRN items matched your search!", "No Results",
                                                JOptionPane.INFORMATION_MESSAGE);
                        }

                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Error searching GRN data: " + e.getMessage());
                        e.printStackTrace();
                }
        }

        private void loadGRNTable() {
                DefaultTableModel model = (DefaultTableModel) grnTable.getModel();
                model.setRowCount(0);

                String query = "SELECT gi.grn_id, sp.stock_product_id, sp.title, gi.quantity, gi.price " +
                                "FROM grn_item gi " +
                                "JOIN stock_product sp ON gi.stock_id = sp.id ";

                try {
                        ResultSet rs = MySQL2.executeSearch(query);

                        while (rs.next()) {
                                String grnId = rs.getString("grn_id");
                                String stockProductId = rs.getString("stock_product_id");
                                String stockProductName = rs.getString("title");
                                int quantity = rs.getInt("quantity");
                                double price = rs.getDouble("price");
                                double itemTotal = quantity * price;

                                model.addRow(new Object[] {
                                                grnId,
                                                stockProductId,
                                                stockProductName,
                                                quantity,
                                                price,
                                                itemTotal
                                });
                        }
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Error loading the GRN Table. " + e.getMessage());
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
                selectStockProduct = new javax.swing.JButton();
                jLabel1 = new javax.swing.JLabel();
                quantity = new javax.swing.JTextField();
                jLabel2 = new javax.swing.JLabel();
                price = new javax.swing.JTextField();
                selectedStockProduct = new javax.swing.JTextField();
                jLabel4 = new javax.swing.JLabel();
                selectedStockProductID = new javax.swing.JTextField();
                jLabel5 = new javax.swing.JLabel();
                ResetFields = new javax.swing.JButton();
                searchBar = new javax.swing.JTextField();
                resetSearch = new javax.swing.JButton();
                selectedSupplierName = new javax.swing.JTextField();
                jLabel6 = new javax.swing.JLabel();
                selectedSupplierID = new javax.swing.JTextField();
                jLabel7 = new javax.swing.JLabel();
                selectSupplier = new javax.swing.JButton();
                jPanel2 = new javax.swing.JPanel();
                jLabel8 = new javax.swing.JLabel();
                jLabel9 = new javax.swing.JLabel();
                jLabel10 = new javax.swing.JLabel();
                paidAmount = new javax.swing.JTextField();
                totalBill = new javax.swing.JTextField();
                balance = new javax.swing.JTextField();
                createGRN = new javax.swing.JButton();
                jScrollPane1 = new javax.swing.JScrollPane();
                grnTable = new javax.swing.JTable();

                jPanel1.setBackground(new java.awt.Color(0, 0, 0));
                jPanel1.setForeground(new java.awt.Color(255, 255, 255));

                selectStockProduct.setBackground(new java.awt.Color(0, 153, 51));
                selectStockProduct.setForeground(new java.awt.Color(255, 255, 255));
                selectStockProduct.setText("Select Stock Product");
                selectStockProduct.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                selectStockProductActionPerformed(evt);
                        }
                });

                jLabel1.setText("Quantity");

                quantity.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                quantityActionPerformed(evt);
                        }
                });

                jLabel2.setText("Price");

                price.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                priceActionPerformed(evt);
                        }
                });

                selectedStockProduct.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                selectedStockProductActionPerformed(evt);
                        }
                });

                jLabel4.setText("Stock Product");

                selectedStockProductID.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                selectedStockProductIDActionPerformed(evt);
                        }
                });

                jLabel5.setText("Stock Product ID");

                ResetFields.setBackground(new java.awt.Color(204, 0, 0));
                ResetFields.setForeground(new java.awt.Color(255, 255, 255));
                ResetFields.setText("Reset Fields");
                ResetFields.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                ResetFieldsActionPerformed(evt);
                        }
                });

                searchBar.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                searchBarActionPerformed(evt);
                        }
                });

                resetSearch.setBackground(new java.awt.Color(204, 0, 0));
                resetSearch.setForeground(new java.awt.Color(255, 255, 255));
                resetSearch.setText("Reset");
                resetSearch.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                resetSearchActionPerformed(evt);
                        }
                });

                selectedSupplierName.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                selectedSupplierNameActionPerformed(evt);
                        }
                });

                jLabel6.setText("Supplier Name");

                jLabel7.setText("Supplier ID");

                selectSupplier.setBackground(new java.awt.Color(0, 0, 204));
                selectSupplier.setForeground(new java.awt.Color(255, 255, 255));
                selectSupplier.setText("Select Supplier");
                selectSupplier.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                selectSupplierActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
                                                                .createSequentialGroup()
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                false)
                                                                                .addComponent(selectStockProduct,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                146, Short.MAX_VALUE)
                                                                                .addComponent(jLabel5)
                                                                                .addComponent(jLabel4)
                                                                                .addComponent(selectedStockProduct)
                                                                                .addComponent(selectedStockProductID))
                                                                .addGap(31, 31, 31)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                false)
                                                                                .addComponent(jLabel2)
                                                                                .addComponent(jLabel1)
                                                                                .addComponent(quantity,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                146, Short.MAX_VALUE)
                                                                                .addComponent(price))
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(searchBar,
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                319,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(ResetFields,
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                101,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addGap(18, 18, 18)
                                                                                                .addComponent(resetSearch)
                                                                                                .addGap(10, 10, 10))
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(108, 108, 108)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(jLabel7)
                                                                                                                                .addContainerGap(
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                Short.MAX_VALUE))
                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                jPanel1Layout.createSequentialGroup()
                                                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                                                                .addComponent(selectedSupplierID,
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                .addComponent(selectSupplier,
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                149,
                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                                jPanel1Layout.createSequentialGroup()
                                                                                                                                                                                                .addComponent(jLabel6)
                                                                                                                                                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                                                                                                                                .addComponent(selectedSupplierName,
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING))
                                                                                                                                                .addGap(411, 411,
                                                                                                                                                                411)))))));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addComponent(resetSearch)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(selectStockProduct,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                36,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(selectSupplier,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                36,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addGap(14, 14, 14)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(jLabel5)
                                                                                                                .addComponent(jLabel2)
                                                                                                                .addComponent(jLabel6))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(selectedStockProductID,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                .addComponent(price,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addComponent(selectedSupplierName,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                                .addComponent(jLabel4)
                                                                                                                                                .addComponent(jLabel1))
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                                                .addComponent(selectedStockProduct,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addComponent(quantity,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                jPanel1Layout.createSequentialGroup()
                                                                                                                                                .addGap(78, 78, 78)
                                                                                                                                                .addComponent(searchBar,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                jPanel1Layout.createSequentialGroup()
                                                                                                                .addGap(64, 64, 64)
                                                                                                                .addComponent(ResetFields)
                                                                                                                .addGap(19, 19, 19)
                                                                                                                .addComponent(jLabel7)
                                                                                                                .addPreferredGap(
                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                .addComponent(selectedSupplierID,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(23, 23, 23)));

                jPanel2.setBackground(new java.awt.Color(0, 0, 0));
                jPanel2.setForeground(new java.awt.Color(255, 255, 255));

                jLabel8.setText("Total Bill");

                jLabel9.setText("Paid Amount");

                jLabel10.setText("Balance");

                paidAmount.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                paidAmountActionPerformed(evt);
                        }
                });

                totalBill.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                totalBillActionPerformed(evt);
                        }
                });

                balance.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                balanceActionPerformed(evt);
                        }
                });

                createGRN.setBackground(new java.awt.Color(102, 0, 204));
                createGRN.setForeground(new java.awt.Color(255, 255, 255));
                createGRN.setText("Create GRN");
                createGRN.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                createGRNActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel10)
                                                                                .addComponent(jLabel9)
                                                                                .addComponent(jLabel8))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(totalBill,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                119,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(balance,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                119,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGroup(jPanel2Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(paidAmount,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                119,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(createGRN,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                146,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addContainerGap()));
                jPanel2Layout.setVerticalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel8)
                                                                                .addComponent(totalBill,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel9)
                                                                                .addComponent(paidAmount,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(createGRN,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                36,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(4, 4, 4)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel10)
                                                                                .addComponent(balance,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap(15, Short.MAX_VALUE)));

                grnTable.setBackground(new java.awt.Color(0, 0, 0));
                grnTable.setForeground(new java.awt.Color(255, 255, 255));
                grnTable.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {
                                                { null, null, null, null, null, null },
                                                { null, null, null, null, null, null },
                                                { null, null, null, null, null, null },
                                                { null, null, null, null, null, null }
                                },
                                new String[] {
                                                "GoodReceive ID", "Stock Product ID", "Stock Product", "Quantity",
                                                "Price", "Item Total"
                                }));
                jScrollPane1.setViewportView(grnTable);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jScrollPane1)
                                                                                .addComponent(jPanel2,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jPanel1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, 0)
                                                                .addComponent(jScrollPane1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                311,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, 0)
                                                                .addComponent(jPanel2,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)));
        }// </editor-fold>//GEN-END:initComponents

        private void totalBillActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_totalBillActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_totalBillActionPerformed

        private void paidAmountActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_paidAmountActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_paidAmountActionPerformed

        private void balanceActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_balanceActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_balanceActionPerformed

        private void selectStockProduct1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_selectStockProduct1ActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_selectStockProduct1ActionPerformed

        private void createGRNActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_createGRNActionPerformed
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                String paidamntText = paidAmount.getText().trim();
                String supplier = selectedSupplierID.getText().trim();
                String quantityText = quantity.getText().trim();
                String pString = price.getText().trim();

                String stockID = generateID("stock_id", "stock", "STK");
                String grnID = generateID("grn_id", "grn", "GRN");

                if (!validateInput(quantityText, pString, paidamntText)) {
                        return;
                } else {
                        try {
                                String stockQuery = String.format(
                                                "INSERT INTO stock (stock_id, added_date, branch_id) " +
                                                                "VALUES ('%s', '%s', %d)",
                                                stockID.replace("'", "''"),
                                                formattedDateTime.replace("'", "''"),
                                                branchID);

                                MySQL2.executeIUD(stockQuery);

                                String supplierIDQuery = "SELECT id FROM supplier WHERE supplier_id = '"
                                                + supplier.replace("'", "''") + "'";
                                ResultSet supplierIdRs = MySQL2.executeSearch(supplierIDQuery);
                                int supplierId = -1;
                                if (supplierIdRs.next()) {
                                        supplierId = supplierIdRs.getInt("id");
                                } else {
                                        JOptionPane.showMessageDialog(this, "Error: supplier id not found.");
                                        return;
                                }
                                double paidamnt = Double.parseDouble(paidamntText);

                                String grnQuery = String.format(
                                                "INSERT INTO grn (grn_id, paid_amount, date, branch_id, employee_id, supplier_id) "
                                                                +
                                                                "VALUES ('%s', %f, '%s', %d, %d, %d)",
                                                grnID.replace("'", "''"),
                                                paidamnt,
                                                formattedDateTime,
                                                branchID,
                                                employeeID,
                                                supplierId);

                                MySQL2.executeIUD(grnQuery);

                                String grnIdQuery = "SELECT id FROM grn WHERE grn_id = '" + grnID.replace("'", "''")
                                                + "'";
                                ResultSet grnIdResult = MySQL2.executeSearch(grnIdQuery);
                                int grnIdINT = -1;

                                if (grnIdResult.next()) {
                                        grnIdINT = grnIdResult.getInt("id");
                                } else {
                                        JOptionPane.showMessageDialog(this, "error GRN ID not found.");
                                        return;
                                }

                                String stockIdQuery = "SELECT id FROM stock WHERE stock_id = '"
                                                + stockID.replace("'", "''")
                                                + "'";
                                ResultSet stockIdResult = MySQL2.executeSearch(stockIdQuery);
                                int stockIdINT = -1;

                                if (stockIdResult.next()) {
                                        stockIdINT = stockIdResult.getInt("id");
                                } else {
                                        JOptionPane.showMessageDialog(this, "error Stock ID not found.");
                                        return;
                                }

                                double priceDouble = Double.parseDouble(pString);
                                int quantityInt = Integer.parseInt(quantityText);

                                String grnItemQuery = String.format(
                                                "INSERT INTO grn_item (quantity, price, grn_id, stock_id) " +
                                                                "VALUES (%d, %f, %d, %d)",
                                                quantityInt, priceDouble, grnIdINT, stockIdINT);

                                MySQL2.executeIUD(grnItemQuery);

                                loadGRNTable();
                                clearFields();

                        } catch (Exception e) {
                                JOptionPane.showMessageDialog(this, "Operation Error: " + e.getMessage(),
                                                "Error: ", JOptionPane.WARNING_MESSAGE);
                                e.printStackTrace();
                        }
                }

        }// GEN-LAST:event_createGRNActionPerformed

        private void selectedStockProductActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_selectedStockProductActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_selectedStockProductActionPerformed

        private void ResetFieldsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ResetFieldsActionPerformed
                clearFields();
        }// GEN-LAST:event_ResetFieldsActionPerformed

        private void selectStockProductActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_selectStockProductActionPerformed
                StockManagementSelectStockProduct SMSSP = new StockManagementSelectStockProduct();
                SMSSP.setSelectionListener(new SelectionListener<model.SelectProduct>() {
                        @Override
                        public void onItemSelected(model.SelectProduct product) {
                                selectedStockProduct.setText(product.getName());
                                selectedStockProductID.setText(product.getId());
                        }

                });

                SMSSP.setVisible(true);

        }// GEN-LAST:event_selectStockProductActionPerformed

        private void selectedStockProductIDActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_selectedStockProductIDActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_selectedStockProductIDActionPerformed

        private void selectSupplierActionPerformed(java.awt.event.ActionEvent evt) {
                StockManagementSelectSupplier SMSS = new StockManagementSelectSupplier();

                // Set the callback to receive the selected supplier (model.SelectSupplier)
                SMSS.setSelectionListener(new SelectionListener<model.SelectSupplier>() {
                        @Override
                        public void onItemSelected(model.SelectSupplier supplier) {
                                selectedSupplierName.setText(supplier.getName());
                                selectedSupplierID.setText(supplier.getId());
                                // Optionally set other fields here
                                // selectedSupplierID.setText(supplier.getId());
                        }
                });

                SMSS.setVisible(true);
        }

        private void selectedSupplierActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_selectedSupplierActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_selectedSupplierActionPerformed

        private void selectedSupplierNameActionPerformed(java.awt.event.ActionEvent evt) {
                // TODO and Your handling logic goes here
        }

        private void priceActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_priceActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_priceActionPerformed

        private void quantityActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_quantityActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_quantityActionPerformed

        private void searchBarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchBarActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_searchBarActionPerformed

        private void resetSearchActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_resetSearchActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_resetSearchActionPerformed

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton ResetFields;
        private javax.swing.JTextField balance;
        private javax.swing.JButton createGRN;
        private javax.swing.JTable grnTable;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel10;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JLabel jLabel7;
        private javax.swing.JLabel jLabel8;
        private javax.swing.JLabel jLabel9;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JTextField paidAmount;
        private javax.swing.JTextField price;
        private javax.swing.JTextField quantity;
        private javax.swing.JButton resetSearch;
        private javax.swing.JTextField searchBar;
        private javax.swing.JButton selectStockProduct;
        private javax.swing.JButton selectSupplier;
        private javax.swing.JTextField selectedStockProduct;
        private javax.swing.JTextField selectedStockProductID;
        private javax.swing.JTextField selectedSupplierID;
        private javax.swing.JTextField selectedSupplierName;
        private javax.swing.JTextField totalBill;
        // End of variables declaration//GEN-END:variables
}
