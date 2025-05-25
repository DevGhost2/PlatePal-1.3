/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import java.awt.Image;
import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import model.MySQL2;

public class MenuBranchAdmin extends javax.swing.JPanel {

    private static MenuBranchAdmin mba;

    private String selectedImagePath = "";
    private int selectedProductId = -1;

    /**
     * Creates new form MenuBranchAdmin
     */
    public MenuBranchAdmin() {
        initComponents();
        setupInitialData();
        loadCategories();
        loadProductStatusData();
        loadProductTable();
        setupTableSelectionListener();
    }

    public static synchronized MenuBranchAdmin getInstance() {
        if (mba == null) {
            mba = new MenuBranchAdmin();
        }
        return mba;
    }

    private void setupInitialData() {
        // Clear default text and set placeholder behavior
        ProductTextField.setText("");
        ProjectIDTextField.setEditable(false);

        // Generate new product ID
        generateNewProductId();

        // Setup size and price table
        setupSizePriceTable();
    }

    private void generateNewProductId() {
    try {
        ResultSet resultSet = MySQL2.executeSearch(
            "SELECT MAX(CAST(SUBSTRING(product_id, 5) AS UNSIGNED)) FROM product WHERE product_id LIKE 'pdct%'");

        int maxId = 0;
        if (resultSet.next()) {
            maxId = resultSet.getInt(1);
        }

        String newProductId = String.format("pdct%03d", maxId + 1);
        ProjectIDTextField.setText(newProductId);
    } catch (Exception e) {
        ProjectIDTextField.setText("pdct001");
    }
}


    private void setupSizePriceTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        model.addRow(new Object[] { "M", "" });
        model.addRow(new Object[] { "Lg", "" });
        model.addRow(new Object[] { "Xl", "" });
    }

    private void loadCategories() {
        try {
            ResultSet categoryResult = MySQL2.executeSearch("SELECT * FROM category WHERE id > 0");
    
            CategoryComboBox.removeAllItems();
            CategoryComboBox2.removeAllItems();
            CategoryComboBox2.addItem("All");
    
            while (categoryResult.next()) {
                String categoryName = categoryResult.getString("name");
                CategoryComboBox.addItem(categoryName);
                CategoryComboBox2.addItem(categoryName);
            }
    
            loadSubCategories();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading categories: " + e.getMessage());
        }
    }
    

    private void loadSubCategories() {
        try {
            String selectedCategory = (String) CategoryComboBox.getSelectedItem();
            if (selectedCategory != null) {
                ResultSet resultSet = MySQL2.executeSearch(
                    "SELECT sc.* FROM sub_category sc " +
                    "INNER JOIN category c ON sc.category_id = c.id " +
                    "WHERE c.name = '" + selectedCategory + "' AND sc.id != 0"
                );
    
                SubCategoryComboBox.removeAllItems();
                while (resultSet.next()) {
                    SubCategoryComboBox.addItem(resultSet.getString("subCatTitle"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    private void loadProductStatusData() {
        try {
            ResultSet resultSet = MySQL2.executeSearch("SELECT * FROM product_status");
    
            StatusComboBox.removeAllItems();
            StatusComboBox2.removeAllItems();
            StatusComboBox2.addItem("All");
    
            while (resultSet.next()) {
                String statusName = resultSet.getString("name");
                StatusComboBox.addItem(statusName);
                StatusComboBox2.addItem(statusName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    private void loadProductTable() {
        try {
            String query = "SELECT p.product_id, p.title, c.name as category, " +
                    "sc.subCatTitle as subcategory, ps.name as status, p.sellingCount " +
                    "FROM product p " +
                    "INNER JOIN sub_category sc ON p.sub_category_id = sc.id " +
                    "INNER JOIN category c ON sc.category_id = c.id " +
                    "INNER JOIN product_status ps ON p.product_status_id = ps.id " +
                    "ORDER BY p.id DESC";
    
            ResultSet resultSet = MySQL2.executeSearch(query);
    
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);
    
            while (resultSet.next()) {
                Vector<String> row = new Vector<>();
                row.add(resultSet.getString("product_id"));
                row.add(resultSet.getString("title"));
                row.add(resultSet.getString("category"));
                row.add(resultSet.getString("subcategory"));
                row.add(resultSet.getString("status"));
                row.add(String.valueOf(resultSet.getInt("sellingCount")));
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    private void setupTableSelectionListener() {
        jTable2.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = jTable2.getSelectedRow();
                if (selectedRow >= 0) {
                    loadProductForEdit(selectedRow);
                }
            }
        });
    }

    private void loadProductForEdit(int row) {
        try {
            String productId = (String) jTable2.getValueAt(row, 0);
    
            ResultSet resultSet = MySQL2.executeSearch(
                    "SELECT p.*, c.name as category, sc.subCatTitle as subcategory, ps.name as status " +
                    "FROM product p " +
                    "INNER JOIN sub_category sc ON p.sub_category_id = sc.id " +
                    "INNER JOIN category c ON sc.category_id = c.id " +
                    "INNER JOIN product_status ps ON p.product_status_id = ps.id " +
                    "WHERE p.product_id = '" + productId + "'"
            );
    
            if (resultSet.next()) {
                selectedProductId = resultSet.getInt("id");
                ProductTextField.setText(resultSet.getString("title"));
                ProjectIDTextField.setText(resultSet.getString("product_id"));
                jTextPane1.setText(resultSet.getString("description"));
                CategoryComboBox.setSelectedItem(resultSet.getString("category"));
                loadSubCategories();
                SubCategoryComboBox.setSelectedItem(resultSet.getString("subcategory"));
                StatusComboBox.setSelectedItem(resultSet.getString("status"));
                jLabel9.setText(String.valueOf(resultSet.getInt("sellingCount")));
                loadProductSizes(selectedProductId);
                loadProductImage(selectedProductId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    private void loadProductSizes(int productId) {
        try {
            ResultSet resultSet = MySQL2.executeSearch(
                    "SELECT s.size_type, phs.price FROM product_has_size phs " +
                    "INNER JOIN size s ON phs.size_id = s.id " +
                    "WHERE phs.product_id = " + productId
            );
    
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
    
            while (resultSet.next()) {
                model.addRow(new Object[] {
                        resultSet.getString("size_type"),
                        resultSet.getString("price")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    private void loadProductImage(int productId) {
        try {
            ResultSet resultSet = MySQL2.executeSearch(
                    "SELECT url FROM product_images WHERE product_id = " + productId + " LIMIT 1"
            );
    
            if (resultSet.next()) {
                String imagePath = resultSet.getString("url");
                selectedImagePath = imagePath;
                displayImage(imagePath);
            } else {
                jLabel10.setIcon(null);
                jLabel10.setText("No Image");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    private void displayImage(String imagePath) {
        try {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                ImageIcon imageIcon = new ImageIcon(imagePath);
                Image image = imageIcon.getImage();
                Image scaledImage = image.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                jLabel10.setIcon(new ImageIcon(scaledImage));
                jLabel10.setText("");
            } else {
                jLabel10.setIcon(null);
                jLabel10.setText("Image not found");
            }
        } catch (Exception e) {
            jLabel10.setIcon(null);
            jLabel10.setText("Error loading image");
        }
    }

    private void clearForm() {
        ProductTextField.setText("");
        jTextPane1.setText("");
        CategoryComboBox.setSelectedIndex(0);
        SubCategoryComboBox.setSelectedIndex(0);
        StatusComboBox.setSelectedIndex(0);
        selectedImagePath = "";
        selectedProductId = -1;
        jLabel9.setText("0");
        jLabel10.setIcon(null);
        jLabel10.setText("No Image");
        generateNewProductId();
        setupSizePriceTable();
    }

    private boolean validateForm() {
        if (ProductTextField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter product title.");
            return false;
        }

        if (jTextPane1.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter product description.");
            return false;
        }

        if (CategoryComboBox.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Please select a category.");
            return false;
        }

        if (SubCategoryComboBox.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Please select a subcategory.");
            return false;
        }

        // Validate size and price table
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        boolean hasValidPrices = false;

        for (int i = 0; i < model.getRowCount(); i++) {
            String size = (String) model.getValueAt(i, 0);
            String price = (String) model.getValueAt(i, 1);

            if (size != null && !size.trim().isEmpty() &&
                    price != null && !price.trim().isEmpty()) {
                try {
                    Double.parseDouble(price);
                    hasValidPrices = true;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid price format for size: " + size);
                    return false;
                }
            }
        }

        if (!hasValidPrices) {
            JOptionPane.showMessageDialog(this, "Please add at least one size with a valid price.");
            return false;
        }

        return true;
    }

    private void addProduct() {
        try {
            MySQL2.createConnection();
    
            int categoryId = getCategoryIdFromComboBox();
            int subCategoryId = getSubCategoryIdFromComboBox();
            int statusId = getStatusIdFromComboBox();
    
            String insertProductQuery = "INSERT INTO product (product_id, title, description, sub_category_id, product_status_id, sellingCount, added_date) VALUES (?, ?, ?, ?, ?, 0, NOW())";
    
            PreparedStatement productStatement = MySQL2.connection.prepareStatement(insertProductQuery, java.sql.Statement.RETURN_GENERATED_KEYS);
            productStatement.setString(1, ProjectIDTextField.getText());
            productStatement.setString(2, ProductTextField.getText());
            productStatement.setString(3, jTextPane1.getText());
            productStatement.setInt(4, subCategoryId);
            productStatement.setInt(5, statusId);
    
            productStatement.executeUpdate();
    
            ResultSet generatedKeys = productStatement.getGeneratedKeys();
            int productId = 0;
            if (generatedKeys.next()) {
                productId = generatedKeys.getInt(1);
            }
    
            insertProductSizes(MySQL2.connection, productId);
    
            if (!selectedImagePath.isEmpty()) {
                insertProductImage(MySQL2.connection, productId, selectedImagePath);
            }
    
            JOptionPane.showMessageDialog(this, "Product added successfully!");
            clearForm();
            loadProductTable();
    
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding product: " + e.getMessage());
        }
    }
    

    private void updateProduct() {
        try {
            MySQL2.createConnection();
    
            int categoryId = getCategoryIdFromComboBox();
            int subCategoryId = getSubCategoryIdFromComboBox();
            int statusId = getStatusIdFromComboBox();
    
            String updateProductQuery = "UPDATE product SET title = ?, description = ?, sub_category_id = ?, product_status_id = ? WHERE id = ?";
            PreparedStatement productStatement = MySQL2.connection.prepareStatement(updateProductQuery);
    
            productStatement.setString(1, ProductTextField.getText());
            productStatement.setString(2, jTextPane1.getText());
            productStatement.setInt(3, subCategoryId);
            productStatement.setInt(4, statusId);
            productStatement.setInt(5, selectedProductId);
    
            productStatement.executeUpdate();
    
            PreparedStatement deleteSizesStatement = MySQL2.connection.prepareStatement(
                    "DELETE FROM product_has_size WHERE product_id = ?");
            deleteSizesStatement.setInt(1, selectedProductId);
            deleteSizesStatement.executeUpdate();
    
            insertProductSizes(MySQL2.connection, selectedProductId);
    
            if (!selectedImagePath.isEmpty()) {
                PreparedStatement deleteImageStatement = MySQL2.connection.prepareStatement(
                        "DELETE FROM product_images WHERE product_id = ?");
                deleteImageStatement.setInt(1, selectedProductId);
                deleteImageStatement.executeUpdate();
    
                insertProductImage(MySQL2.connection, selectedProductId, selectedImagePath);
            }
    
            JOptionPane.showMessageDialog(this, "Product updated successfully!");
            loadProductTable();
    
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating product: " + e.getMessage());
        }
    }
    

    private void deleteProduct() {
        try {
            MySQL2.createConnection();
    
            PreparedStatement deleteImagesStatement = MySQL2.connection.prepareStatement(
                    "DELETE FROM product_images WHERE product_id = ?");
            deleteImagesStatement.setInt(1, selectedProductId);
            deleteImagesStatement.executeUpdate();
    
            PreparedStatement deleteSizesStatement = MySQL2.connection.prepareStatement(
                    "DELETE FROM product_has_size WHERE product_id = ?");
            deleteSizesStatement.setInt(1, selectedProductId);
            deleteSizesStatement.executeUpdate();
    
            PreparedStatement deleteProductStatement = MySQL2.connection.prepareStatement(
                    "DELETE FROM product WHERE id = ?");
            deleteProductStatement.setInt(1, selectedProductId);
            deleteProductStatement.executeUpdate();
    
            JOptionPane.showMessageDialog(this, "Product deleted successfully!");
            clearForm();
            loadProductTable();
    
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting product: " + e.getMessage());
        }
    }
    

    private int getCategoryIdFromComboBox() {
        String categoryName = (String) CategoryComboBox.getSelectedItem();
        if ("Foods".equals(categoryName))
            return 1;
        if ("Drinks".equals(categoryName))
            return 2;
        return 1; // Default to Foods
    }

    private int getSubCategoryIdFromComboBox() {
        String subCategoryName = (String) SubCategoryComboBox.getSelectedItem();
        if ("Rice".equals(subCategoryName))
            return 1;
        if ("Kottu".equals(subCategoryName))
            return 2;
        if ("Biriyani".equals(subCategoryName))
            return 3;
        if ("Nasiguran".equals(subCategoryName))
            return 4;
        if ("Burger".equals(subCategoryName))
            return 5;
        if ("Pizza".equals(subCategoryName))
            return 6;
        if ("Noodless".equals(subCategoryName))
            return 7;
        if ("Devil".equals(subCategoryName))
            return 8;
        if ("Soup".equals(subCategoryName))
            return 9;
        if ("Shorteats".equals(subCategoryName))
            return 10;
        if ("Drinks".equals(subCategoryName))
            return 11;
        return 1; // Default to Rice
    }

    private int getStatusIdFromComboBox() {
        String statusName = (String) StatusComboBox.getSelectedItem();
        if ("Active".equals(statusName))
            return 1;
        if ("Inactive".equals(statusName))
            return 2;
        return 1; // Default to Active
    }

    private void insertProductSizes(Connection connection, int productId) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            String size = (String) model.getValueAt(i, 0);
            String price = (String) model.getValueAt(i, 1);

            if (size != null && !size.trim().isEmpty() &&
                    price != null && !price.trim().isEmpty()) {

                // Get size ID from your existing data
                int sizeId = getSizeIdFromType(size.trim());

                // Insert product_has_size
                PreparedStatement sizeStatement = connection.prepareStatement(
                        "INSERT INTO product_has_size (product_id, size_id, price) VALUES (?, ?, ?)");
                sizeStatement.setInt(1, productId);
                sizeStatement.setInt(2, sizeId);
                sizeStatement.setString(3, price);
                sizeStatement.executeUpdate();
            }
        }
    }

    private int getSizeIdFromType(String sizeType) {
        if ("M".equals(sizeType))
            return 1;
        if ("Lg".equals(sizeType))
            return 2;
        if ("Xl".equals(sizeType))
            return 3;
        return 1; // Default to M
    }

    private void insertProductImage(Connection connection, int productId, String imagePath) throws SQLException {
        PreparedStatement imageStatement = connection.prepareStatement(
                "INSERT INTO product_images (product_id, url) VALUES (?, ?)");
        imageStatement.setInt(1, productId);
        imageStatement.setString(2, imagePath);
        imageStatement.executeUpdate();
    }

    private void filterProducts() {
        try {
            MySQL2.createConnection();
    
            StringBuilder query = new StringBuilder(
                    "SELECT p.product_id, p.title, c.name as category, " +
                    "sc.subCatTitle as subcategory, ps.name as status, p.sellingCount " +
                    "FROM product p " +
                    "INNER JOIN sub_category sc ON p.sub_category_id = sc.id " +
                    "INNER JOIN category c ON sc.category_id = c.id " +
                    "INNER JOIN product_status ps ON p.product_status_id = ps.id WHERE 1=1");
    
            String searchText = StatusTextfield.getText().trim();
            if (!searchText.isEmpty()) {
                query.append(" AND (p.title LIKE ? OR p.product_id LIKE ?)");
            }
    
            String selectedCategory = (String) CategoryComboBox2.getSelectedItem();
            if (selectedCategory != null && !selectedCategory.equals("All")) {
                query.append(" AND c.name = ?");
            }
    
            String selectedStatus = (String) StatusComboBox2.getSelectedItem();
            if (selectedStatus != null && !selectedStatus.equals("All")) {
                query.append(" AND ps.name = ?");
            }
    
            query.append(" ORDER BY p.id DESC");
    
            PreparedStatement statement = MySQL2.connection.prepareStatement(query.toString());
    
            int paramIndex = 1;
    
            if (!searchText.isEmpty()) {
                String searchPattern = "%" + searchText + "%";
                statement.setString(paramIndex++, searchPattern);
                statement.setString(paramIndex++, searchPattern);
            }
    
            if (selectedCategory != null && !selectedCategory.equals("All")) {
                statement.setString(paramIndex++, selectedCategory);
            }

            if (selectedStatus != null && !selectedStatus.equals("All")) {
                statement.setString(paramIndex++, selectedStatus);
            }
    
            ResultSet resultSet = statement.executeQuery();
    
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);
    
            while (resultSet.next()) {
                Vector<String> row = new Vector<>();
                row.add(resultSet.getString("product_id"));
                row.add(resultSet.getString("title"));
                row.add(resultSet.getString("category"));
                row.add(resultSet.getString("subcategory"));
                row.add(resultSet.getString("status"));
                row.add(String.valueOf(resultSet.getInt("sellingCount")));
                model.addRow(row);
            }
    
        } catch (Exception e) {
            e.printStackTrace();
            loadProductTable();
        }
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ProductTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        ProjectIDTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        CategoryComboBox = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        SubCategoryComboBox = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        StatusComboBox = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        UploadProductButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        RemoveProductButton = new javax.swing.JButton();
        ClearProductButton = new javax.swing.JButton();
        AddProductButton = new javax.swing.JButton();
        UpdateProductButton = new javax.swing.JButton();
        DeleteProductButton = new javax.swing.JButton();
        AddPriceSize = new javax.swing.JButton();
        RemovePriceSize = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        EditButton = new javax.swing.JButton();
        DeleteButton = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        StatusTextfield = new javax.swing.JTextField();
        CategoryComboBox2 = new javax.swing.JComboBox<>();
        StatusComboBox2 = new javax.swing.JComboBox<>();

        jLabel1.setText("Product Title");

        ProductTextField.setText("jTextField1");
        ProductTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProductTextFieldActionPerformed(evt);
            }
        });

        jLabel2.setText("Project ID");

        ProjectIDTextField.setText("jTextField2");
        ProjectIDTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProjectIDTextFieldActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jTextPane1);

        jLabel3.setText("Description");

        jLabel4.setText("Category");

        CategoryComboBox.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        CategoryComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CategoryComboBoxActionPerformed(evt);
            }
        });

        jLabel5.setText("SubCategory");

        SubCategoryComboBox.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        SubCategoryComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubCategoryComboBoxActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null },
                        { null, null },
                        { null, null },
                        { null, null }
                },
                new String[] {
                        "Size", "Price"
                }));
        jScrollPane2.setViewportView(jTable1);

        jLabel6.setText("Size and Pricing");

        jLabel7.setText("Status");

        StatusComboBox.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        StatusComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StatusComboBoxActionPerformed(evt);
            }
        });

        UploadProductButton.setText("Upload");
        UploadProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UploadProductButtonActionPerformed(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(204, 204, 204));
        jLabel10.setText("jLabel10");

        RemoveProductButton.setText("Remove");
        RemoveProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveProductButtonActionPerformed(evt);
            }
        });

        ClearProductButton.setText("Clear");
        ClearProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearProductButtonActionPerformed(evt);
            }
        });

        AddProductButton.setText("Add Product");
        AddProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddProductButtonActionPerformed(evt);
            }
        });

        UpdateProductButton.setText("Update Product");
        UpdateProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateProductButtonActionPerformed(evt);
            }
        });

        DeleteProductButton.setText("Delete");
        DeleteProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteProductButtonActionPerformed(evt);
            }
        });

        AddPriceSize.setText("Add");
        AddPriceSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddPriceSizeActionPerformed(evt);
            }
        });

        RemovePriceSize.setText("Remove");
        RemovePriceSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemovePriceSizeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 199,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(SubCategoryComboBox,
                                                        javax.swing.GroupLayout.Alignment.LEADING, 0, 193,
                                                        Short.MAX_VALUE)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.TRAILING,
                                                                        false)
                                                                .addComponent(jLabel3,
                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE)
                                                                .addComponent(jLabel1,
                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 156,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(ProductTextField,
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabel2,
                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE)
                                                                .addComponent(ProjectIDTextField,
                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 183,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jScrollPane1,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 261,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel10,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 238,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(93, 93, 93))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(StatusComboBox,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 276,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(105, 105, 105))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                jPanel1Layout.createSequentialGroup()
                                                                        .addComponent(jLabel7,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                171,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addContainerGap())))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addComponent(AddProductButton,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        160,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(
                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(UpdateProductButton,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        165,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(
                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(ClearProductButton,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        173,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addGap(12, 12, 12)
                                                                                .addComponent(AddPriceSize,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        170,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(RemovePriceSize,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        170,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(DeleteProductButton,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 176,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(CategoryComboBox,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 193,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(UploadProductButton)
                                                        .addComponent(jScrollPane2,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 417,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(RemoveProductButton)
                                                .addGap(0, 0, Short.MAX_VALUE)))));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(ProductTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(StatusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(ProjectIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel9))
                                .addGroup(jPanel1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(17, 17, 17)
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 162,
                                                        Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(UploadProductButton)
                                        .addComponent(RemoveProductButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(SubCategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 270,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(AddPriceSize)
                                        .addComponent(RemovePriceSize))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42,
                                        Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(AddProductButton)
                                        .addComponent(UpdateProductButton)
                                        .addComponent(ClearProductButton)
                                        .addComponent(DeleteProductButton))
                                .addGap(26, 26, 26)));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null, null, null },
                        { null, null, null, null, null, null },
                        { null, null, null, null, null, null },
                        { null, null, null, null, null, null }
                },
                new String[] {
                        "Product ID", "Title", "Category", "Sub-Category", "Status", "Selling Count"
                }));
        jScrollPane3.setViewportView(jTable2);

        EditButton.setText("Edit");
        EditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditButtonActionPerformed(evt);
            }
        });

        DeleteButton.setText("Delete");
        DeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButtonActionPerformed(evt);
            }
        });

        jLabel11.setText("Search");

        jLabel12.setText("Category");

        jLabel13.setText("Status");

        StatusTextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StatusTextfieldActionPerformed(evt);
            }
        });

        CategoryComboBox2.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        CategoryComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CategoryComboBox2ActionPerformed(evt);
            }
        });

        StatusComboBox2.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        StatusComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StatusComboBox2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(EditButton, javax.swing.GroupLayout.PREFERRED_SIZE, 141,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 176,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(StatusTextfield))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 176,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(CategoryComboBox2, 0,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 176,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(StatusComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE))
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 431,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel11)
                                        .addComponent(StatusTextfield, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel12)
                                        .addComponent(CategoryComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel13)
                                        .addComponent(StatusComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34,
                                        Short.MAX_VALUE)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 612,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(EditButton)
                                        .addComponent(DeleteButton))));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 603,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    }// </editor-fold>//GEN-END:initComponents

    private void UploadProductButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_UploadProductButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedImagePath = selectedFile.getAbsolutePath();
            displayImage(selectedImagePath);
        }
    }// GEN-LAST:event_UploadProductButtonActionPerformed

    private void UpdateProductButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_UpdateProductButtonActionPerformed
        if (selectedProductId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to update.");
            return;
        }

        if (validateForm()) {
            updateProduct();
        }
    }// GEN-LAST:event_UpdateProductButtonActionPerformed

    private void CategoryComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_CategoryComboBox2ActionPerformed
        filterProducts();
    }// GEN-LAST:event_CategoryComboBox2ActionPerformed

    private void StatusComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_StatusComboBox2ActionPerformed
        filterProducts();
    }// GEN-LAST:event_StatusComboBox2ActionPerformed

    private void ProductTextFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ProductTextFieldActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_ProductTextFieldActionPerformed

    private void ProjectIDTextFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ProjectIDTextFieldActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_ProjectIDTextFieldActionPerformed

    private void CategoryComboBoxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_CategoryComboBoxActionPerformed
        loadSubCategories();
    }// GEN-LAST:event_CategoryComboBoxActionPerformed

    private void SubCategoryComboBoxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_SubCategoryComboBoxActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_SubCategoryComboBoxActionPerformed

    private void StatusComboBoxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_StatusComboBoxActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_StatusComboBoxActionPerformed

    private void RemoveProductButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_RemoveProductButtonActionPerformed
        selectedImagePath = "";
        jLabel10.setIcon(null);
        jLabel10.setText("No Image");
    }// GEN-LAST:event_RemoveProductButtonActionPerformed

    private void AddPriceSizeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_AddPriceSizeActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.addRow(new Object[] { "", "" });
    }// GEN-LAST:event_AddPriceSizeActionPerformed

    private void RemovePriceSizeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_RemovePriceSizeActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow >= 0) {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to remove.");
        }
    }// GEN-LAST:event_RemovePriceSizeActionPerformed

    private void AddProductButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_AddProductButtonActionPerformed
        if (validateForm()) {
            addProduct();
        }
    }// GEN-LAST:event_AddProductButtonActionPerformed

    private void ClearProductButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ClearProductButtonActionPerformed
        clearForm();
    }// GEN-LAST:event_ClearProductButtonActionPerformed

    private void DeleteProductButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_DeleteProductButtonActionPerformed
        if (selectedProductId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this product?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            deleteProduct();
        }
    }// GEN-LAST:event_DeleteProductButtonActionPerformed

    private void EditButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_EditButtonActionPerformed
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow >= 0) {
            loadProductForEdit(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to edit.");
        }
    }// GEN-LAST:event_EditButtonActionPerformed

    private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_DeleteButtonActionPerformed
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow >= 0) {
            loadProductForEdit(selectedRow);
            DeleteProductButtonActionPerformed(evt);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to delete.");
        }
    }// GEN-LAST:event_DeleteButtonActionPerformed

    private void StatusTextfieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_StatusTextfieldActionPerformed
        filterProducts();
    }// GEN-LAST:event_StatusTextfieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddPriceSize;
    private javax.swing.JButton AddProductButton;
    private javax.swing.JComboBox<String> CategoryComboBox;
    private javax.swing.JComboBox<String> CategoryComboBox2;
    private javax.swing.JButton ClearProductButton;
    private javax.swing.JButton DeleteButton;
    private javax.swing.JButton DeleteProductButton;
    private javax.swing.JButton EditButton;
    private javax.swing.JTextField ProductTextField;
    private javax.swing.JTextField ProjectIDTextField;
    private javax.swing.JButton RemovePriceSize;
    private javax.swing.JButton RemoveProductButton;
    private javax.swing.JComboBox<String> StatusComboBox;
    private javax.swing.JComboBox<String> StatusComboBox2;
    private javax.swing.JTextField StatusTextfield;
    private javax.swing.JComboBox<String> SubCategoryComboBox;
    private javax.swing.JButton UpdateProductButton;
    private javax.swing.JButton UploadProductButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
