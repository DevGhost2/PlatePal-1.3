/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import model.MySQL2;

/**
 *
 * @author hasit
 */
public class DashboardPanelBranchAdmin extends javax.swing.JPanel {

    private static DashboardPanelBranchAdmin dpba;
    public DashboardPanelBranchAdmin() {
        initComponents();
        loadDashboardData();
    }
    
    public static synchronized DashboardPanelBranchAdmin getInstance(){
        if(dpba==null){
            dpba=new DashboardPanelBranchAdmin();
        
        }
        return dpba;
    }

    private void loadDashboardData() {
    try {
        // Load Active Customers Count
        ResultSet activeCustomersRS = MySQL2.executeSearch("SELECT COUNT(*) as count FROM `customer`");
        if (activeCustomersRS.next()) {
            ActiveCustomersTotalNoLabel.setText(activeCustomersRS.getString("count"));
        } else {
            ActiveCustomersTotalNoLabel.setText("0");
        }
        
        // Load Complete Orders Count
        ResultSet completeOrdersRS = MySQL2.executeSearch(
            "SELECT COUNT(*) as count FROM `orders` " +
            "INNER JOIN `order_status` ON `orders`.`order_status_id` = `order_status`.`id` " +
            "WHERE `order_status`.`type` = 'Complete'");
        if (completeOrdersRS.next()) {
            TotalOrdersTotalNoLabel.setText(completeOrdersRS.getString("count"));
        } else {
            TotalOrdersTotalNoLabel.setText("0");
        }
        
        // Load Failed Orders Count
        ResultSet failedOrdersRS = MySQL2.executeSearch(
            "SELECT COUNT(*) as count FROM `orders` " +
            "INNER JOIN `order_status` ON `orders`.`order_status_id` = `order_status`.`id` " +
            "WHERE `order_status`.`type` = 'Failed'");
        if (failedOrdersRS.next()) {
            FailedOrdersTotalNoLabel.setText(failedOrdersRS.getString("count"));
        } else {
            FailedOrdersTotalNoLabel.setText("0");
        }
        
        // Load Today's Revenue
        LocalDate today = LocalDate.now();
        ResultSet todayRevenueRS = MySQL2.executeSearch(
            "SELECT SUM(payment) as total FROM `invoice` " +
            "WHERE DATE(date) = '" + today + "'");
        if (todayRevenueRS.next() && todayRevenueRS.getString("total") != null) {
            TodaysRevenueTotalNoLabel.setText(todayRevenueRS.getString("total"));
        } else {
            TodaysRevenueTotalNoLabel.setText("0");
        }
        
        // Load Pending Orders Count
        ResultSet pendingOrdersRS = MySQL2.executeSearch(
            "SELECT COUNT(*) as count FROM `orders` " +
            "INNER JOIN `order_status` ON `orders`.`order_status_id` = `order_status`.`id` " +
            "WHERE `order_status`.`type` = 'Pending'");
        if (pendingOrdersRS.next()) {
            PendingOrdersTotalNoLabel.setText(pendingOrdersRS.getString("count"));
        } else {
            PendingOrdersTotalNoLabel.setText("0");
        }
        
        // Load Active Employees Count
        ResultSet activeEmployeesRS = MySQL2.executeSearch("SELECT COUNT(*) as count FROM `employee`");
        if (activeEmployeesRS.next()) {
            ActiveEmployeesTotalNoLabel.setText(activeEmployeesRS.getString("count"));
        } else {
            ActiveEmployeesTotalNoLabel.setText("0");
        }
        
        // Load Progress Table
        loadProgressTable();
        
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void loadProgressTable() {
    try {
        ResultSet progressRS = MySQL2.executeSearch(
            "SELECT o.order_id as OrderID, os.type as Status, " +
            "DATE_FORMAT(o.order_on, '%Y-%m-%d') as OrderDate, " +
            "CASE " +
            "  WHEN os.type = 'Pending' THEN 'In Progress' " +
            "  WHEN os.type = 'Processing' THEN '50%' " +
            "  WHEN os.type = 'Complete' THEN '100%' " +
            "  WHEN os.type = 'Failed' THEN 'Failed' " +
            "  ELSE 'Unknown' " +
            "END as Progress " +
            "FROM `orders` o " +
            "INNER JOIN `order_status` os ON o.order_status_id = os.id " +
            "ORDER BY o.order_on DESC LIMIT 10");
        
        DefaultTableModel model = (DefaultTableModel) progressTable.getModel();
        model.setRowCount(0);
        
        // Set column names if needed
        if (model.getColumnCount() == 0) {
            model.addColumn("Status");
            model.addColumn("Name");
            model.addColumn("Start Date");
            model.addColumn("Progress");
        }
        
        while (progressRS.next()) {
            Vector<String> row = new Vector<>();
            row.add(progressRS.getString("Status"));
            row.add(progressRS.getString("OrderID"));
            row.add(progressRS.getString("OrderDate"));
            row.add(progressRS.getString("Progress"));
            model.addRow(row);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    // This method should be called when you want to refresh the dashboard data
    public void refreshDashboard() {
        loadDashboardData();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // Your generated code here - DO NOT MODIFY
    // </editor-fold>
    

    // Variables declaration - do not modify                     
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        ActiveCustomersPanel = new javax.swing.JPanel();
        ActiveCustomersLabel = new javax.swing.JLabel();
        ActiveCustomersTotalNoLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        CompleteOrdersPanel = new javax.swing.JPanel();
        CompleteOrdersLabel = new javax.swing.JLabel();
        TotalOrdersTotalNoLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        FailedOrdersPanel = new javax.swing.JPanel();
        FailedOrdersLabel = new javax.swing.JLabel();
        FailedOrdersTotalNoLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        TodaysRevenuePanel = new javax.swing.JPanel();
        TodaysRevenueLabel = new javax.swing.JLabel();
        TodaysRevenueTotalNoLabel = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        PendingOrdersPanel = new javax.swing.JPanel();
        PendingOrdersLabel = new javax.swing.JLabel();
        PendingOrdersTotalNoLabel = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        ActiveeEmployeesPanel = new javax.swing.JPanel();
        ActiveEmployeesLabel = new javax.swing.JLabel();
        ActiveEmployeesTotalNoLabel = new javax.swing.JLabel();
        ProgressPanel = new javax.swing.JPanel();
        ProgressLabel = new javax.swing.JLabel();
        ProgressScrollPane = new javax.swing.JScrollPane();
        progressTable = new javax.swing.JTable();

        setLayout(new java.awt.GridLayout(2, 1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridLayout(2, 3));

        ActiveCustomersPanel.setBackground(new java.awt.Color(255, 255, 255));
        ActiveCustomersPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ActiveCustomersLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ActiveCustomersLabel.setForeground(new java.awt.Color(255, 204, 102));
        ActiveCustomersLabel.setText("Active Customers");

        ActiveCustomersTotalNoLabel.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        ActiveCustomersTotalNoLabel.setForeground(new java.awt.Color(255, 204, 102));
        ActiveCustomersTotalNoLabel.setText("100");

        javax.swing.GroupLayout ActiveCustomersPanelLayout = new javax.swing.GroupLayout(ActiveCustomersPanel);
        ActiveCustomersPanel.setLayout(ActiveCustomersPanelLayout);
        ActiveCustomersPanelLayout.setHorizontalGroup(
            ActiveCustomersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ActiveCustomersPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(ActiveCustomersLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ActiveCustomersPanelLayout.createSequentialGroup()
                .addContainerGap(124, Short.MAX_VALUE)
                .addComponent(ActiveCustomersTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );
        ActiveCustomersPanelLayout.setVerticalGroup(
            ActiveCustomersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ActiveCustomersPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(ActiveCustomersLabel)
                .addGap(29, 29, 29)
                .addComponent(ActiveCustomersTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(77, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ActiveCustomersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ActiveCustomersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel6);

        CompleteOrdersPanel.setBackground(new java.awt.Color(255, 255, 255));
        CompleteOrdersPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        CompleteOrdersLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        CompleteOrdersLabel.setForeground(new java.awt.Color(255, 204, 102));
        CompleteOrdersLabel.setText("Complete Orders");

        TotalOrdersTotalNoLabel.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        TotalOrdersTotalNoLabel.setForeground(new java.awt.Color(255, 204, 102));
        TotalOrdersTotalNoLabel.setText("100");

        javax.swing.GroupLayout CompleteOrdersPanelLayout = new javax.swing.GroupLayout(CompleteOrdersPanel);
        CompleteOrdersPanel.setLayout(CompleteOrdersPanelLayout);
        CompleteOrdersPanelLayout.setHorizontalGroup(
            CompleteOrdersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CompleteOrdersPanelLayout.createSequentialGroup()
                .addGroup(CompleteOrdersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CompleteOrdersPanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(CompleteOrdersLabel))
                    .addGroup(CompleteOrdersPanelLayout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(TotalOrdersTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        CompleteOrdersPanelLayout.setVerticalGroup(
            CompleteOrdersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CompleteOrdersPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(CompleteOrdersLabel)
                .addGap(33, 33, 33)
                .addComponent(TotalOrdersTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CompleteOrdersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CompleteOrdersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel4);

        FailedOrdersPanel.setBackground(new java.awt.Color(255, 255, 255));
        FailedOrdersPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        FailedOrdersLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        FailedOrdersLabel.setForeground(new java.awt.Color(255, 204, 102));
        FailedOrdersLabel.setText("Failed Orders");

        FailedOrdersTotalNoLabel.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        FailedOrdersTotalNoLabel.setForeground(new java.awt.Color(255, 204, 102));
        FailedOrdersTotalNoLabel.setText("100");

        javax.swing.GroupLayout FailedOrdersPanelLayout = new javax.swing.GroupLayout(FailedOrdersPanel);
        FailedOrdersPanel.setLayout(FailedOrdersPanelLayout);
        FailedOrdersPanelLayout.setHorizontalGroup(
            FailedOrdersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FailedOrdersPanelLayout.createSequentialGroup()
                .addGroup(FailedOrdersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FailedOrdersPanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(FailedOrdersLabel))
                    .addGroup(FailedOrdersPanelLayout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(FailedOrdersTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        FailedOrdersPanelLayout.setVerticalGroup(
            FailedOrdersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FailedOrdersPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(FailedOrdersLabel)
                .addGap(32, 32, 32)
                .addComponent(FailedOrdersTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(FailedOrdersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(FailedOrdersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3);

        TodaysRevenuePanel.setBackground(new java.awt.Color(255, 255, 255));
        TodaysRevenuePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        TodaysRevenueLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        TodaysRevenueLabel.setForeground(new java.awt.Color(255, 204, 102));
        TodaysRevenueLabel.setText("Today's Revenue");

        TodaysRevenueTotalNoLabel.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        TodaysRevenueTotalNoLabel.setForeground(new java.awt.Color(255, 204, 102));
        TodaysRevenueTotalNoLabel.setText("100");

        javax.swing.GroupLayout TodaysRevenuePanelLayout = new javax.swing.GroupLayout(TodaysRevenuePanel);
        TodaysRevenuePanel.setLayout(TodaysRevenuePanelLayout);
        TodaysRevenuePanelLayout.setHorizontalGroup(
            TodaysRevenuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TodaysRevenuePanelLayout.createSequentialGroup()
                .addGroup(TodaysRevenuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TodaysRevenuePanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(TodaysRevenueLabel))
                    .addGroup(TodaysRevenuePanelLayout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(TodaysRevenueTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(96, Short.MAX_VALUE))
        );
        TodaysRevenuePanelLayout.setVerticalGroup(
            TodaysRevenuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TodaysRevenuePanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(TodaysRevenueLabel)
                .addGap(33, 33, 33)
                .addComponent(TodaysRevenueTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TodaysRevenuePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TodaysRevenuePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2);

        PendingOrdersPanel.setBackground(new java.awt.Color(255, 255, 255));
        PendingOrdersPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        PendingOrdersLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PendingOrdersLabel.setForeground(new java.awt.Color(255, 204, 102));
        PendingOrdersLabel.setText("Pending Orders");

        PendingOrdersTotalNoLabel.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        PendingOrdersTotalNoLabel.setForeground(new java.awt.Color(255, 204, 102));
        PendingOrdersTotalNoLabel.setText("100");

        javax.swing.GroupLayout PendingOrdersPanelLayout = new javax.swing.GroupLayout(PendingOrdersPanel);
        PendingOrdersPanel.setLayout(PendingOrdersPanelLayout);
        PendingOrdersPanelLayout.setHorizontalGroup(
            PendingOrdersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PendingOrdersPanelLayout.createSequentialGroup()
                .addGroup(PendingOrdersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PendingOrdersPanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(PendingOrdersLabel))
                    .addGroup(PendingOrdersPanelLayout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(PendingOrdersTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        PendingOrdersPanelLayout.setVerticalGroup(
            PendingOrdersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PendingOrdersPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(PendingOrdersLabel)
                .addGap(32, 32, 32)
                .addComponent(PendingOrdersTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PendingOrdersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PendingOrdersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel5);

        ActiveeEmployeesPanel.setBackground(new java.awt.Color(255, 255, 255));
        ActiveeEmployeesPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ActiveEmployeesLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ActiveEmployeesLabel.setForeground(new java.awt.Color(255, 204, 102));
        ActiveEmployeesLabel.setText("Active Employees");

        ActiveEmployeesTotalNoLabel.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        ActiveEmployeesTotalNoLabel.setForeground(new java.awt.Color(255, 204, 102));
        ActiveEmployeesTotalNoLabel.setText("100");

        javax.swing.GroupLayout ActiveeEmployeesPanelLayout = new javax.swing.GroupLayout(ActiveeEmployeesPanel);
        ActiveeEmployeesPanel.setLayout(ActiveeEmployeesPanelLayout);
        ActiveeEmployeesPanelLayout.setHorizontalGroup(
            ActiveeEmployeesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ActiveeEmployeesPanelLayout.createSequentialGroup()
                .addGroup(ActiveeEmployeesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ActiveeEmployeesPanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(ActiveEmployeesLabel))
                    .addGroup(ActiveeEmployeesPanelLayout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(ActiveEmployeesTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(100, Short.MAX_VALUE))
        );
        ActiveeEmployeesPanelLayout.setVerticalGroup(
            ActiveeEmployeesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ActiveeEmployeesPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(ActiveEmployeesLabel)
                .addGap(31, 31, 31)
                .addComponent(ActiveEmployeesTotalNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ActiveeEmployeesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ActiveeEmployeesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel11);

        add(jPanel1);

        ProgressPanel.setBackground(new java.awt.Color(204, 204, 204));
        ProgressPanel.setLayout(new java.awt.BorderLayout());

        ProgressLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ProgressLabel.setText("Progress");
        ProgressPanel.add(ProgressLabel, java.awt.BorderLayout.PAGE_START);

        progressTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Status", "Name", "Start Date", "Progress"
            }
        ));
        ProgressScrollPane.setViewportView(progressTable);

        ProgressPanel.add(ProgressScrollPane, java.awt.BorderLayout.CENTER);

        add(ProgressPanel);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ActiveCustomersLabel;
    private javax.swing.JPanel ActiveCustomersPanel;
    private javax.swing.JLabel ActiveCustomersTotalNoLabel;
    private javax.swing.JLabel ActiveEmployeesLabel;
    private javax.swing.JLabel ActiveEmployeesTotalNoLabel;
    private javax.swing.JPanel ActiveeEmployeesPanel;
    private javax.swing.JLabel CompleteOrdersLabel;
    private javax.swing.JPanel CompleteOrdersPanel;
    private javax.swing.JLabel FailedOrdersLabel;
    private javax.swing.JPanel FailedOrdersPanel;
    private javax.swing.JLabel FailedOrdersTotalNoLabel;
    private javax.swing.JLabel PendingOrdersLabel;
    private javax.swing.JPanel PendingOrdersPanel;
    private javax.swing.JLabel PendingOrdersTotalNoLabel;
    private javax.swing.JLabel ProgressLabel;
    private javax.swing.JPanel ProgressPanel;
    private javax.swing.JScrollPane ProgressScrollPane;
    private javax.swing.JLabel TodaysRevenueLabel;
    private javax.swing.JPanel TodaysRevenuePanel;
    private javax.swing.JLabel TodaysRevenueTotalNoLabel;
    private javax.swing.JLabel TotalOrdersTotalNoLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTable progressTable;
    // End of variables declaration//GEN-END:variables
}
